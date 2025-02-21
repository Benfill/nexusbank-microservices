package com.nexusbank.customer.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.nexusbank.customer.dto.AccountDto;
import com.nexusbank.customer.dto.DtoRequest;
import com.nexusbank.customer.dto.DtoResponse;
import com.nexusbank.customer.dto.GlobalResponse;
import com.nexusbank.customer.entity.Customer;
import com.nexusbank.customer.exception.ResourceNotFoundException;
import com.nexusbank.customer.mapper.CustomerMapper;
import com.nexusbank.customer.repository.CustomerRepository;
import com.nexusbank.customer.service.ICustomerService;

@Service
public class CustomerService implements ICustomerService {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private CustomerMapper mapper;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Customer getById(Long id) {
	return repository.findById(id)
		.orElseThrow(() -> new ResourceNotFoundException("Cannot find customer with this id: " + id));
    }

    @Override
    public List<DtoResponse> getAll(Integer page, Integer size) {
	page = page > 0 ? page - 1 : 0;
	size = size > 3 ? size : 3;
	Pageable pageable = PageRequest.of(page, size);
	List<Customer> customers = repository.findAll(pageable).getContent();
	List<DtoResponse> dtos = mapper.entitiesToDto(customers);

	dtos.stream().map(c -> {

	    c.setAccounts(getAccounts(c.getId()));
	    return c;
	}).collect(Collectors.toList());
	return dtos;
    }

    @Override
    public DtoResponse create(DtoRequest dto) {
	Customer customer = mapper.DtoToentity(dto);
	Customer savedCustomer = repository.save(customer);
	return mapper.entityToDto(savedCustomer);
    }

    @Override
    public DtoResponse update(Long id, DtoRequest dto) {
	Customer customer = getById(id);

	customer.setName(dto.getName());
	customer.setEmail(dto.getEmail());

	Customer updatedCustomer = repository.save(customer);
	return mapper.entityToDto(updatedCustomer);
    }

    @Override
    public GlobalResponse delete(Long id) {

	Customer customer = getById(id);

	repository.delete(customer);

	return GlobalResponse.builder().message("Customer deleted successfully").build();
    }

    @Override
    public DtoResponse searchById(Long id) {
	DtoResponse dto = mapper.entityToDto(getById(id));
	dto.setAccounts(getAccounts(id));
	return dto;
    }

    @Override
    public List<AccountDto> getAccounts(Long id) {
	String url = "http://account-service/api/accounts/customer/" + id;

	try {
	    // Use getForEntity to get the full response (including status code)
	    ResponseEntity<List<AccountDto>> response = restTemplate.exchange(url, HttpMethod.GET, null,
		    new ParameterizedTypeReference<List<AccountDto>>() {
		    });

	    // Check if the response status is 200 OK
	    if (response.getStatusCode() == HttpStatus.OK) {
		return response.getBody(); // Return the account data
	    } else {
		// Handle other status codes if needed
		throw new ResourceNotFoundException("Account not found with this id: " + id);
	    }
	} catch (HttpClientErrorException e) {
	    // Handle 404 NOT_FOUND explicitly
	    if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
		throw new ResourceNotFoundException("Account not found with this id: " + id);
	    } else {
		// Handle other HTTP client errors (e.g., 400 Bad Request, 401 Unauthorized,
		// etc.)
		throw new RuntimeException("Error while fetching account data: " + e.getMessage());
	    }
	} catch (Exception e) {
	    // Handle other exceptions (e.g., network errors)
	    throw new RuntimeException("Error while fetching account data: " + e.getMessage());
	}
    }

}

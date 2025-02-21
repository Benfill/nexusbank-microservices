package com.nexusbank.account.service.impl;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.nexusbank.account.dto.CustomerDto;
import com.nexusbank.account.dto.DtoRequest;
import com.nexusbank.account.dto.DtoResponse;
import com.nexusbank.account.dto.GlobalResponse;
import com.nexusbank.account.entity.Account;
import com.nexusbank.account.exception.ResourceNotFoundException;
import com.nexusbank.account.exception.ResourceValidationException;
import com.nexusbank.account.mapper.AccountMapper;
import com.nexusbank.account.repository.AccountRepository;
import com.nexusbank.account.service.IAccountService;
import com.nexusbank.account.validation.DoubleAccountValidator;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private AccountMapper mapper;

    @Autowired
    private DoubleAccountValidator validator;

    private final Logger logger = Logger.getLogger(AccountService.class.getName());

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Account getById(Long id) {
	return repository.findById(id)
		.orElseThrow(() -> new ResourceNotFoundException("Cannot find Account with this id: " + id));
    }

    @Override
    public List<DtoResponse> getAll(Integer page, Integer size) {
	page = page > 0 ? page - 1 : 0;
	size = size > 3 ? size : 3;
	Pageable pageable = PageRequest.of(page, size);
	List<Account> accounts = repository.findAll(pageable).getContent();
	List<DtoResponse> dtos = accounts.stream().map(a -> {
	    DtoResponse dtoResponse = mapper.entityToDto(a);

	    logger.warning("this is customer id: " + a.getCustomerId());
	    dtoResponse.setCustomer(getCustomer(a.getCustomerId()));
	    return dtoResponse;
	}).collect(Collectors.toList());
	return dtos;
    }

    @Override
    public DtoResponse create(DtoRequest dto) {
	CustomerDto customer = getCustomer(dto.getCustomerId());

	Boolean checker = validator.hasDuplicateAccount(dto.getCustomerId(), dto.getType());

	if (checker == true) {
	    throw new ResourceValidationException(
		    "Cannot create multiple accounts of the same type for the same customer");
	}

	Account account = mapper.DtoToentity(dto);
	Account savedAccount = repository.save(account);
	DtoResponse savedDto = mapper.entityToDto(savedAccount);

	savedDto.setCustomer(customer);
	return savedDto;
    }

    @Override
    public DtoResponse update(Long id, DtoRequest dto) {
	Account account = getById(id);
	CustomerDto customer = getCustomer(dto.getCustomerId());

	if (account.getType() != dto.getType()) {
	    Boolean checker = validator.hasDuplicateAccount(dto.getCustomerId(), dto.getType());

	    if (checker == true) {
		throw new ResourceValidationException(
			"Cannot create multiple accounts of the same type for the same customer");
	    }
	}

	account.setBalance(dto.getBalance());
	account.setType(dto.getType());
	account.setCustomerId(dto.getCustomerId());

	Account updatedAccount = repository.save(account);
	DtoResponse updatedDto = mapper.entityToDto(updatedAccount);

	updatedDto.setCustomer(customer);
	return updatedDto;
    }

    @Override
    public GlobalResponse delete(Long id) {

	Account account = getById(id);

	repository.delete(account);

	return GlobalResponse.builder().message("Account deleted successfully").build();
    }

    @Override
    public DtoResponse display(Long id) {
	Account account = getById(id);
	CustomerDto customer = getCustomer(account.getCustomerId());
	DtoResponse dto = mapper.entityToDto(account);

	dto.setCustomer(customer);
	return dto;
    }

    @Override
    public CustomerDto getCustomer(Long id) {
	String url = "http://customer-service/api/customers/search/" + id;

	try {
	    // Use getForEntity to get the full response (including status code)
	    ResponseEntity<CustomerDto> response = restTemplate.getForEntity(url, CustomerDto.class);

	    // Check if the response status is 200 OK
	    if (response.getStatusCode() == HttpStatus.OK) {
		return response.getBody(); // Return the customer data
	    } else {
		// Handle other status codes if needed
		throw new ResourceNotFoundException("Customer not found with this id: " + id);
	    }
	} catch (HttpClientErrorException e) {
	    // Handle 404 NOT_FOUND explicitly
	    if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
		throw new ResourceNotFoundException("Customer not found with this id: " + id);
	    } else {
		// Handle other HTTP client errors (e.g., 400 Bad Request, 401 Unauthorized,
		// etc.)
		throw new RuntimeException("Error while fetching customer data: " + e.getMessage());
	    }
	} catch (Exception e) {
	    // Handle other exceptions (e.g., network errors)
	    throw new RuntimeException("Error while fetching customer data: " + e.getMessage());
	}
    }

}

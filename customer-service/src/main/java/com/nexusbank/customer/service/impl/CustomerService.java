package com.nexusbank.customer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

	return dto;
    }

}

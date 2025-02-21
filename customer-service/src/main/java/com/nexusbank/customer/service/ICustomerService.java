package com.nexusbank.customer.service;

import java.util.List;

import com.nexusbank.customer.dto.DtoRequest;
import com.nexusbank.customer.dto.DtoResponse;
import com.nexusbank.customer.dto.GlobalResponse;
import com.nexusbank.customer.entity.Customer;

public interface ICustomerService {

    Customer getById(Long id);

    List<DtoResponse> getAll(Integer page, Integer size);

    DtoResponse create(DtoRequest dto);

    DtoResponse update(Long id, DtoRequest dto);

    GlobalResponse delete(Long id);

    DtoResponse searchById(Long id);

}

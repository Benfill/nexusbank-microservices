package com.nexusbank.account.service;

import java.util.List;

import com.nexusbank.account.dto.CustomerDto;
import com.nexusbank.account.dto.DtoRequest;
import com.nexusbank.account.dto.DtoResponse;
import com.nexusbank.account.dto.GlobalResponse;
import com.nexusbank.account.entity.Account;

public interface IAccountService {

    Account getById(Long id);

    List<DtoResponse> getAll(Integer page, Integer size);

    DtoResponse create(DtoRequest dto);

    DtoResponse update(Long id, DtoRequest dto);

    GlobalResponse delete(Long id);

    DtoResponse display(Long id);

    CustomerDto getCustomer(Long id);
}

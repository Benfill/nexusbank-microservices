package com.nexusbank.customer.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.nexusbank.customer.dto.DtoRequest;
import com.nexusbank.customer.dto.DtoResponse;
import com.nexusbank.customer.entity.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    DtoResponse entityToDto(Customer entity);

    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    List<DtoResponse> entitiesToDto(List<Customer> entities);

    Customer DtoToentity(DtoRequest dto);
}

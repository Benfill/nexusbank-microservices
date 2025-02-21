package com.nexusbank.account.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.nexusbank.account.dto.DtoRequest;
import com.nexusbank.account.dto.DtoResponse;
import com.nexusbank.account.entity.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    DtoResponse entityToDto(Account entity);

    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    List<DtoResponse> entitiesToDto(List<Account> entities);

    Account DtoToentity(DtoRequest dto);
}

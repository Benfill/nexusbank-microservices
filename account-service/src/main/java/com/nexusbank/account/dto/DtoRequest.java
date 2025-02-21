package com.nexusbank.account.dto;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import com.nexusbank.account.entity.AccountType;

import lombok.Data;

@Data
public class DtoRequest {

    @Column
    @NotNull(message = "Account balance cant be null")
    private Double balance;

    @NotNull(message = "Customer id cant be null")
    private Long customerId;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Account type cant be null")
    private AccountType type;

}

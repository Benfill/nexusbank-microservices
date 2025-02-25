package com.nexusbank.account.dto;

import java.time.LocalDateTime;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.nexusbank.account.entity.AccountType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DtoResponse {
    private Long id;

    private Double balance;

    private CustomerDto customer;

    @Enumerated(EnumType.STRING)
    private AccountType type;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

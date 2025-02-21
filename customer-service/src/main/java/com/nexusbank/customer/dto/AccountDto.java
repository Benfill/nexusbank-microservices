package com.nexusbank.customer.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AccountDto {
    private Long id;

    private Double balance;

    private String type;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

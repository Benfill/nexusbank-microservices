package com.nexusbank.account.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CustomerDto {
    private Long id;

    private String name;

    private String email;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

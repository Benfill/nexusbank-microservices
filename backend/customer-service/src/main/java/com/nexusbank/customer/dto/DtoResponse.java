package com.nexusbank.customer.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DtoResponse {
    private Long id;

    private String name;

    private String email;

    private List<AccountDto> accounts;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

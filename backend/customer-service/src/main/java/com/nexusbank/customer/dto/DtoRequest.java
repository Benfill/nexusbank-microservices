package com.nexusbank.customer.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class DtoRequest {

    @NotNull(message = "Customer name cant be null")
    @NotEmpty(message = "Customer name cant be empty")
    private String name;

    @NotNull(message = "Customer email cant be null")
    @NotEmpty(message = "Customer email cant be empty")
    private String email;
}

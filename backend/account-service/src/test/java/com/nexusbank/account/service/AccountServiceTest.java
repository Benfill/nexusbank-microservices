package com.nexusbank.account.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.nexusbank.account.dto.CustomerDto;
import com.nexusbank.account.dto.DtoRequest;
import com.nexusbank.account.dto.DtoResponse;
import com.nexusbank.account.entity.Account;
import com.nexusbank.account.entity.AccountType;
import com.nexusbank.account.mapper.AccountMapper;
import com.nexusbank.account.repository.AccountRepository;
import com.nexusbank.account.service.impl.AccountService;
import com.nexusbank.account.validation.DoubleAccountValidator;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private RestTemplate restTemplate; // Mock RestTemplate

    @Mock
    private DoubleAccountValidator validator;

    @Mock
    private AccountRepository repository;

    @Mock
    private AccountMapper mapper;

    @InjectMocks
    private AccountService accountService; // Class under test

    private DtoRequest dtoRequest;
    private CustomerDto customerDto;
    private Account account;
    private DtoResponse dtoResponse;

    @BeforeEach
    public void setUp() {
	// Initialize test data
	dtoRequest = new DtoRequest();
	dtoRequest.setCustomerId(1L);
	dtoRequest.setType(AccountType.SAVING);

	customerDto = new CustomerDto();
	customerDto.setId(1L);
	customerDto.setName("John Doe");

	account = new Account();
	account.setId(1L);
	account.setType(AccountType.SAVING);

	dtoResponse = new DtoResponse();
	dtoResponse.setId(1L);
	dtoResponse.setType(AccountType.SAVING);
	dtoResponse.setCustomer(customerDto);
    }

    // Test Case 1: Successful Account Creation
    @Test
    public void testCreateAccount_Success() {
	// Arrange
	String customerServiceUrl = "http://customer-service/api/customers/search/1";
	when(restTemplate.getForEntity(customerServiceUrl, CustomerDto.class))
		.thenReturn(new ResponseEntity<>(customerDto, HttpStatus.OK)); // Mock RestTemplate response
	when(validator.hasDuplicateAccount(1L, AccountType.SAVING)).thenReturn(false);
	when(mapper.DtoToentity(dtoRequest)).thenReturn(account);
	when(repository.save(account)).thenReturn(account);
	when(mapper.entityToDto(account)).thenReturn(dtoResponse);

	// Act
	DtoResponse result = accountService.create(dtoRequest);

	// Assert
	assertNotNull(result);
	assertEquals(1L, result.getId());
	assertEquals(AccountType.SAVING, result.getType());
	assertEquals(customerDto, result.getCustomer());

	// Verify interactions
	verify(restTemplate, times(1)).getForEntity(customerServiceUrl, CustomerDto.class);
	verify(validator, times(1)).hasDuplicateAccount(1L, AccountType.SAVING);
	verify(mapper, times(1)).DtoToentity(dtoRequest);
	verify(repository, times(1)).save(account);
	verify(mapper, times(1)).entityToDto(account);
    }

    // Test Case 3: RestTemplate Throws Exception
    @Test
    public void testCreateAccount_RestTemplateException() {
	// Arrange
	String customerServiceUrl = "http://customer-service/api/customers/search/1";
	when(restTemplate.getForEntity(customerServiceUrl, CustomerDto.class))
		.thenThrow(new RuntimeException("Network error")); // Mock RestTemplate exception

	// Act & Assert
	assertThrows(RuntimeException.class, () -> {
	    accountService.create(dtoRequest);
	});

	// Verify interactions
	verify(restTemplate, times(1)).getForEntity(customerServiceUrl, CustomerDto.class);
	verify(validator, never()).hasDuplicateAccount(any(), any());
	verify(mapper, never()).DtoToentity(any());
	verify(repository, never()).save(any());
    }
}
package com.nexusbank.customer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.nexusbank.customer.dto.GlobalResponse;
import com.nexusbank.customer.entity.Customer;
import com.nexusbank.customer.exception.ResourceNotFoundException;
import com.nexusbank.customer.mapper.CustomerMapper;
import com.nexusbank.customer.repository.CustomerRepository;
import com.nexusbank.customer.service.impl.CustomerService;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository repository;

    @Mock
    private CustomerMapper mapper;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CustomerService customerService;

    private Customer testCustomer;
    private final Long customerId = 1L;

    @BeforeEach
    void setUp() {
	testCustomer = new Customer();
	testCustomer.setId(customerId);
	testCustomer.setName("Test Customer");
    }

    @Test
    void getById_WithExistingId_ShouldReturnCustomer() {
	// Arrange
	when(repository.findById(customerId)).thenReturn(Optional.of(testCustomer));

	// Act
	Customer result = customerService.getById(customerId);

	// Assert
	assertNotNull(result);
	assertEquals(customerId, result.getId());
	verify(repository, times(1)).findById(customerId);
    }

    @Test
    void getById_WithNonExistingId_ShouldThrowResourceNotFoundException() {
	// Arrange
	when(repository.findById(customerId)).thenReturn(Optional.empty());

	// Act & Assert
	ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
		() -> customerService.getById(customerId));

	assertTrue(exception.getMessage().contains("Cannot find customer with this id: " + customerId));
	verify(repository, times(1)).findById(customerId);
    }

    @Test
    void delete_WithExistingId_ShouldDeleteCustomerAndAccounts() {
	// Arrange
	when(repository.findById(customerId)).thenReturn(Optional.of(testCustomer));

	ResponseEntity<GlobalResponse> mockResponse = new ResponseEntity<>(
		GlobalResponse.builder().message("Accounts deleted successfully").build(), HttpStatus.OK);

	when(restTemplate.exchange(eq("http://account-service/api/accounts/customer/" + customerId),
		eq(HttpMethod.DELETE), isNull(), any(ParameterizedTypeReference.class))).thenReturn(mockResponse);

	// Act
	GlobalResponse result = customerService.delete(customerId);

	// Assert
	assertNotNull(result);
	assertEquals("Customer deleted successfully", result.getMessage());

	verify(repository, times(1)).findById(customerId);
	verify(repository, times(1)).delete(testCustomer);
	verify(restTemplate, times(1)).exchange(eq("http://account-service/api/accounts/customer/" + customerId),
		eq(HttpMethod.DELETE), isNull(), any(ParameterizedTypeReference.class));
    }

    @Test
    void delete_WithNonExistingId_ShouldThrowResourceNotFoundException() {
	// Arrange
	when(repository.findById(customerId)).thenReturn(Optional.empty());

	// Act & Assert
	ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
		() -> customerService.delete(customerId));

	assertTrue(exception.getMessage().contains("Cannot find customer with this id: " + customerId));
	verify(repository, times(1)).findById(customerId);
	verify(repository, never()).delete(any(Customer.class));
	verify(restTemplate, never()).exchange(anyString(), any(HttpMethod.class), any(),
		any(ParameterizedTypeReference.class));
    }

    @Test
    void deleteAccounts_WithSuccessfulResponse_ShouldReturnGlobalResponse() {
	// Arrange
	GlobalResponse expectedResponse = GlobalResponse.builder().message("Accounts deleted successfully").build();
	ResponseEntity<GlobalResponse> mockResponse = new ResponseEntity<>(expectedResponse, HttpStatus.OK);

	when(restTemplate.exchange(eq("http://account-service/api/accounts/customer/" + customerId),
		eq(HttpMethod.DELETE), isNull(), any(ParameterizedTypeReference.class))).thenReturn(mockResponse);

	// Act
	GlobalResponse result = customerService.deleteAccounts(customerId);

	// Assert
	assertNotNull(result);
	assertEquals("Accounts deleted successfully", result.getMessage());
	verify(restTemplate, times(1)).exchange(eq("http://account-service/api/accounts/customer/" + customerId),
		eq(HttpMethod.DELETE), isNull(), any(ParameterizedTypeReference.class));
    }

    @Test
    void deleteAccounts_WithNotFoundError_ShouldThrowResourceNotFoundException() {
	// Arrange
	HttpClientErrorException notFoundException = new HttpClientErrorException(HttpStatus.NOT_FOUND);

	when(restTemplate.exchange(eq("http://account-service/api/accounts/customer/" + customerId),
		eq(HttpMethod.DELETE), isNull(), any(ParameterizedTypeReference.class))).thenThrow(notFoundException);

	// Act & Assert
	ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
		() -> customerService.deleteAccounts(customerId));

	assertTrue(exception.getMessage().contains("Account not found with this id: " + customerId));
    }

    @Test
    void deleteAccounts_WithOtherClientError_ShouldThrowRuntimeException() {
	// Arrange
	HttpClientErrorException badRequestException = new HttpClientErrorException(HttpStatus.BAD_REQUEST,
		"Bad request");

	when(restTemplate.exchange(eq("http://account-service/api/accounts/customer/" + customerId),
		eq(HttpMethod.DELETE), isNull(), any(ParameterizedTypeReference.class))).thenThrow(badRequestException);

	// Act & Assert
	RuntimeException exception = assertThrows(RuntimeException.class,
		() -> customerService.deleteAccounts(customerId));

	assertTrue(exception.getMessage().contains("Error while deleting account data"));
    }

    @Test
    void deleteAccounts_WithGenericException_ShouldThrowRuntimeException() {
	// Arrange
	RuntimeException runtimeException = new RuntimeException("Connection error");

	when(restTemplate.exchange(eq("http://account-service/api/accounts/customer/" + customerId),
		eq(HttpMethod.DELETE), isNull(), any(ParameterizedTypeReference.class))).thenThrow(runtimeException);

	// Act & Assert
	RuntimeException exception = assertThrows(RuntimeException.class,
		() -> customerService.deleteAccounts(customerId));

	assertTrue(exception.getMessage().contains("Error while deleting account data"));
	assertTrue(exception.getMessage().contains("Connection error"));
    }
}
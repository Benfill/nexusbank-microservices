package com.nexusbank.customer.exception;

public class ResourceValidationException extends RuntimeException {

    private String message;

    public ResourceValidationException() {
	super();
    }

    public ResourceValidationException(String message) {
	super(message);
	this.message = message;
    }
}

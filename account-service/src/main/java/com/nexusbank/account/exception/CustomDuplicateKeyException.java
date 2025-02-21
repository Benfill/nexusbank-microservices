package com.nexusbank.account.exception;

public class CustomDuplicateKeyException extends RuntimeException {
    private String message;

    public CustomDuplicateKeyException() {
	super();
    }

    public CustomDuplicateKeyException(String msg) {
	super(msg);
	this.message = msg;
    }
}

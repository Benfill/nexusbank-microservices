package com.nexusbank.account.exception;

public class ModelNotFoundException extends RuntimeException {

    private String message;

    public ModelNotFoundException() {
    }

    public ModelNotFoundException(String msg) {
	super(msg);
	this.message = msg;
    }
}

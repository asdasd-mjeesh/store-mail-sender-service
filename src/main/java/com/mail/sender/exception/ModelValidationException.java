package com.mail.sender.exception;

public class ModelValidationException extends RuntimeException {

    public ModelValidationException(String message) {
        super(message);
    }
}

package com.theusick.core.service.exception;

public class NoAccessException extends Exception {

    private final static String DEFAULT_MESSAGE = "No access to this resource with id: ";

    public NoAccessException(String message) {
        super(message);
    }

    public NoAccessException(Long id) {
        this(DEFAULT_MESSAGE + id);
    }

}

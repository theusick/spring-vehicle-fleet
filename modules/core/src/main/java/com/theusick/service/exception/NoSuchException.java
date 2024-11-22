package com.theusick.service.exception;

public class NoSuchException extends Exception {

    private final static String DEFAULT_MESSAGE = "No such objectId: ";

    public NoSuchException(String message) {
        super(message);
    }

    public NoSuchException(Long objectId) {
        this(DEFAULT_MESSAGE + objectId);
    }

}

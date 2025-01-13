package com.theusick.service.exception;

public class NoSuchUserException extends NoSuchException {

    public NoSuchUserException(String username) {
        super("User not found: " + username);
    }

    public NoSuchUserException(Long id) {
        super("User not found by id: " + id);
    }

}

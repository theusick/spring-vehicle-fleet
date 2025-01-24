package com.theusick.fleet.service.exception;

import com.theusick.core.service.exception.NoSuchException;

public class NoSuchUserException extends NoSuchException {

    public NoSuchUserException(String username) {
        super("User not found: " + username);
    }

    public NoSuchUserException(Long id) {
        super("User not found by id: " + id);
    }

}

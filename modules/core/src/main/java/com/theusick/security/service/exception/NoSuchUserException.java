package com.theusick.security.service.exception;

import com.theusick.service.exception.NoSuchException;

public class NoSuchUserException extends NoSuchException {

    public NoSuchUserException(String username) {
        super("User not found: " + username);
    }

}

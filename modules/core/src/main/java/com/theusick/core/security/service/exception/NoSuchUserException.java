package com.theusick.core.security.service.exception;

import com.theusick.core.service.exception.NoSuchException;

public class NoSuchUserException extends NoSuchException {

    public NoSuchUserException(String username) {
        super("User not found: " + username);
    }

}

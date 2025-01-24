package com.theusick.core.api.exception;

import org.springframework.http.HttpStatus;

public class ConflictApiException extends ApiException {

    public ConflictApiException(String message) {
        super(HttpStatus.CONFLICT, message);
    }

}

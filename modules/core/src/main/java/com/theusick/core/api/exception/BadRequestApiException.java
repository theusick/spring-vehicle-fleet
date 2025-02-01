package com.theusick.core.api.exception;

import org.springframework.http.HttpStatus;

public class BadRequestApiException extends ApiException {

    public BadRequestApiException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

}

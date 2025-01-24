package com.theusick.core.api.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedApiException extends ApiException {

    public UnauthorizedApiException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }

}

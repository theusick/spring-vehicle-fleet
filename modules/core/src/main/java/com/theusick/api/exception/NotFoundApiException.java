package com.theusick.api.exception;

import org.springframework.http.HttpStatus;

public class NotFoundApiException extends ApiException {

    public NotFoundApiException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

}

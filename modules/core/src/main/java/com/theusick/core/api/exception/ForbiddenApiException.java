package com.theusick.core.api.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenApiException extends ApiException {

    public ForbiddenApiException(String message) {
        super(HttpStatus.FORBIDDEN, "Forbidden");
    }

}

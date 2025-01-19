package com.theusick.api.exception;

import org.springframework.http.HttpStatus;

public class GeneralApiException extends ApiException {

    public GeneralApiException(Exception exception) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
    }

    public GeneralApiException(HttpStatus status, String message) {
        super(status, message);
    }

}

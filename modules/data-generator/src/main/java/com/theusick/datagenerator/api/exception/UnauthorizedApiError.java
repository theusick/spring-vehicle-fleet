package com.theusick.datagenerator.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class UnauthorizedApiError extends AbstractGraphHopperApiError {

    private static final HttpStatus STATUS = HttpStatus.UNAUTHORIZED;

    public UnauthorizedApiError() {
        super(STATUS, "API token is invalid");
    }

}

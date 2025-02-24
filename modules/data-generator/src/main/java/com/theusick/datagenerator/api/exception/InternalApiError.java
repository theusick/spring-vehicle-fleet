package com.theusick.datagenerator.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class InternalApiError extends AbstractGraphHopperApiError {

    private static final HttpStatus STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    protected InternalApiError() {
        super(STATUS, "GraphHopper server error");
    }

}

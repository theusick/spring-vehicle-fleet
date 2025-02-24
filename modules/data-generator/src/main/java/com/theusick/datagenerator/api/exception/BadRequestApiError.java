package com.theusick.datagenerator.api.exception;

import com.theusick.datagenerator.api.exception.model.GraphHopperErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class BadRequestApiError extends AbstractGraphHopperApiError {

    private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    public BadRequestApiError() {
        super(STATUS);
    }

    @Override
    public String getErrorDetails(GraphHopperErrorResponse errorResponse, HttpHeaders headers) {
        return defaultMessage + " " + errorResponse.getMessage();
    }

}

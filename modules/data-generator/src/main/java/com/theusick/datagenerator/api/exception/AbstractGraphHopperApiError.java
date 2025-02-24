package com.theusick.datagenerator.api.exception;

import com.theusick.datagenerator.api.exception.model.GraphHopperErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

public abstract class AbstractGraphHopperApiError implements GraphHopperApiError {

    protected final HttpStatus status;
    protected final String defaultMessage;

    protected AbstractGraphHopperApiError(HttpStatus status) {
        this.status = status;
        this.defaultMessage = createErrorDefaultMessage();
    }

    protected AbstractGraphHopperApiError(HttpStatus status, String details) {
        this.status = status;
        this.defaultMessage = createErrorDefaultMessage(details);
    }

    private String createErrorDefaultMessage() {
        return "[" + status.value() + "]";
    }

    private String createErrorDefaultMessage(String details) {
        return createErrorDefaultMessage() + " " + details;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getErrorDetails(GraphHopperErrorResponse errorResponse, HttpHeaders headers) {
        return defaultMessage;
    }

}

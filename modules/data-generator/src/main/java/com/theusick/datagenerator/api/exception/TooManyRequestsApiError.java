package com.theusick.datagenerator.api.exception;

import com.theusick.datagenerator.api.exception.model.GraphHopperErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class TooManyRequestsApiError extends AbstractGraphHopperApiError {

    private static final HttpStatus STATUS = HttpStatus.TOO_MANY_REQUESTS;

    protected TooManyRequestsApiError() {
        super(STATUS);
    }

    @Override
    public String getErrorDetails(GraphHopperErrorResponse errorResponse, HttpHeaders headers) {
        String resetTime = headers.getFirst("X-RateLimit-Reset");
        return defaultMessage + " Exceeded request limit. Reset after: '" + resetTime
            + "' seconds";
    }

}

package com.theusick.datagenerator.api.exception;

import com.theusick.datagenerator.api.exception.model.GraphHopperErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

public interface GraphHopperApiError {

    HttpStatus getStatus();

    String getErrorDetails(GraphHopperErrorResponse errorResponse, HttpHeaders headers);

}

package com.theusick.datagenerator.api.exception.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.theusick.datagenerator.api.exception.GraphHopperApiError;
import com.theusick.datagenerator.api.exception.factory.ApiErrorLogFactory;
import com.theusick.datagenerator.api.exception.model.GraphHopperErrorResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Slf4j
@Component
@AllArgsConstructor
public class GraphHopperApiErrorHandler implements ResponseErrorHandler {

    private final ObjectMapper jacksonObjectMapper;

    private final ApiErrorLogFactory apiErrorFactory;

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().isError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatus errorStatus = HttpStatus.valueOf(response.getStatusCode().value());

        GraphHopperApiError apiErrorHandler = apiErrorFactory.getLogErrorHandler(errorStatus);

        GraphHopperErrorResponse errorResponse = jacksonObjectMapper
            .readValue(response.getBody(), GraphHopperErrorResponse.class);

        log.error(apiErrorHandler.getErrorDetails(errorResponse, response.getHeaders()));
    }

}

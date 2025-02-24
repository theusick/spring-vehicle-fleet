package com.theusick.datagenerator.api.exception.factory;

import com.theusick.datagenerator.api.exception.GraphHopperApiError;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ApiErrorLogFactory {

    private final Map<HttpStatus, GraphHopperApiError> errorHandlers;

    public ApiErrorLogFactory(List<GraphHopperApiError> errorHandlers) {
        this.errorHandlers = errorHandlers.stream()
            .collect(Collectors.toMap(GraphHopperApiError::getStatus, Function.identity()));
    }

    public GraphHopperApiError getLogErrorHandler(HttpStatus status) {
        return errorHandlers.getOrDefault(status,
            errorHandlers.get(HttpStatus.INTERNAL_SERVER_ERROR));
    }

}

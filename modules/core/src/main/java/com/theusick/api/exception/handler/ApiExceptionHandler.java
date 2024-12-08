package com.theusick.api.exception.handler;

import com.theusick.api.exception.ApiException;
import com.theusick.api.exception.GeneralApiException;
import com.theusick.api.exception.mapper.ApiExceptionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {

    private final ApiExceptionMapper apiExceptionMapper;

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ProblemDetail> handleApiException(ApiException exception,
                                                            WebRequest request) {
        log.warn(exception.toString());
        ProblemDetail problemDetail = apiExceptionMapper.toProblemDetail(exception, request);
        return ResponseEntity.status(exception.getStatus()).body(problemDetail);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ProblemDetail> handleGeneralException(Exception exception,
                                                                WebRequest request) {
        log.error("Unhandled exception occurred: ", exception);
        ApiException apiException = new GeneralApiException(exception);

        ProblemDetail problemDetail = apiExceptionMapper.toProblemDetail(apiException, request);
        return ResponseEntity.status(apiException.getStatus()).body(problemDetail);
    }

}

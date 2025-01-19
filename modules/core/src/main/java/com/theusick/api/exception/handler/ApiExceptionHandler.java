package com.theusick.api.exception.handler;

import com.theusick.api.exception.ApiException;
import com.theusick.api.exception.GeneralApiException;
import com.theusick.api.exception.mapper.ApiExceptionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

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

    @ExceptionHandler({
        HttpRequestMethodNotSupportedException.class,
        NoResourceFoundException.class,
        AuthorizationDeniedException.class
    })
    @ResponseStatus()
    public ResponseEntity<ProblemDetail> handleNoResourceException(Exception exception,
                                                                   WebRequest request) {
        ApiException apiException = new GeneralApiException(HttpStatus.UNAUTHORIZED, "Unauthorized");

        ProblemDetail problemDetail = apiExceptionMapper.toProblemDetail(apiException, request);
        return ResponseEntity.status(apiException.getStatus()).body(problemDetail);
    }

}

package com.theusick.core.api.exception.handler;

import com.theusick.core.api.exception.ApiException;
import com.theusick.core.api.exception.GeneralApiException;
import com.theusick.core.api.exception.mapper.ApiExceptionMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.security.SignatureException;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice(annotations = RestController.class)
public class ApiExceptionHandler {

    private final ApiExceptionMapper apiExceptionMapper;

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ProblemDetail> handleApiException(ApiException exception,
                                                            WebRequest request) {
        return buildResponse(exception, exception.getStatus(), request);
    }

    @ExceptionHandler({
        HttpRequestMethodNotSupportedException.class,
        NoResourceFoundException.class,
        AuthorizationDeniedException.class
    })
    public ResponseEntity<ProblemDetail> handleNoResourceException(Exception exception,
                                                                   WebRequest request) {
        final HttpStatus status = HttpStatus.UNAUTHORIZED;
        return buildResponse(
            new GeneralApiException(status, status.getReasonPhrase()),
            status,
            request
        );
    }

    @ExceptionHandler({
        SignatureException.class,
        ExpiredJwtException.class,
        JwtException.class
    })
    public ResponseEntity<ProblemDetail> handleJwtSignatureException(Exception exception,
                                                                     WebRequest request) {
        final HttpStatus status = HttpStatus.UNAUTHORIZED;
        return buildResponse(
            new GeneralApiException(status, exception.getMessage()),
            status,
            request
        );
    }

    @ExceptionHandler(AccountStatusException.class)
    public ResponseEntity<ProblemDetail> handleAccountStatusException(AccountStatusException exception,
                                                                      WebRequest request) {
        final HttpStatus status = HttpStatus.UNAUTHORIZED;
        return buildResponse(
            new GeneralApiException(status, "Bad credentials"),
            status,
            request
        );
    }

    private ResponseEntity<ProblemDetail> buildResponse(ApiException exception,
                                                        HttpStatus status,
                                                        WebRequest request) {
        log.warn(exception.toString());
        ProblemDetail problemDetail = apiExceptionMapper.toProblemDetail(exception, request);
        return ResponseEntity.status(status).body(problemDetail);
    }

}

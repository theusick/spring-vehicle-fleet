package com.theusick.core.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theusick.core.api.exception.GeneralApiException;
import com.theusick.core.api.exception.mapper.ApiExceptionMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;
    private final ApiExceptionMapper apiExceptionMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
        throws IOException {
        final HttpStatus status = HttpStatus.UNAUTHORIZED;

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(status.value());

        ProblemDetail problemDetail = apiExceptionMapper.toProblemDetail(
            new GeneralApiException(status, status.getReasonPhrase()), request);

        response.getWriter().write(objectMapper.writeValueAsString(problemDetail));
        response.getWriter().flush();
    }

}

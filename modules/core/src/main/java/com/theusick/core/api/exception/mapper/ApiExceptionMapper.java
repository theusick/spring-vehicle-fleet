package com.theusick.core.api.exception.mapper;

import com.theusick.core.api.exception.ApiException;
import jakarta.servlet.http.HttpServletRequest;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ObjectFactory;
import org.springframework.http.ProblemDetail;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {
    ProblemDetail.class,
    URI.class,
    LocalDateTime.class
})
public interface ApiExceptionMapper {

    @Mapping(target = "type", expression = "java(URI.create(getRequestURI(request)))")
    @Mapping(target = "title", expression = "java(exception.getStatus().getReasonPhrase())")
    @Mapping(target = "status", expression = "java(exception.getStatus().value())")
    @Mapping(target = "detail", source = "exception.message")
    @Mapping(target = "instance", expression = "java(URI.create(getServerBasePath(request)))")
    @Mapping(target = "properties", ignore = true)
    ProblemDetail toProblemDetail(ApiException exception, HttpServletRequest request);

    default ProblemDetail toProblemDetail(ApiException exception, WebRequest webRequest) {
        if (webRequest instanceof ServletWebRequest servletWebRequest) {
            return toProblemDetail(exception, servletWebRequest.getRequest());
        }
        throw new IllegalArgumentException("Unprocessable type of WebRequest");
    }

    @ObjectFactory
    default ProblemDetail createProblemDetail(ApiException exception) {
        return ProblemDetail.forStatus(exception.getStatus());
    }

    @Named("getRequestURI")
    default String getRequestURI(HttpServletRequest request) {
        return request.getRequestURI();
    }

    @Named("getServerBasePath")
    default String getServerBasePath(HttpServletRequest request) {
        return request.getScheme() +
            "://" +
            request.getServerName() +
            ":" +
            request.getServerPort() +
            request.getContextPath();
    }

    @AfterMapping
    default void addProblemDetailCustomProperties(@MappingTarget ProblemDetail problemDetail) {
        problemDetail.setProperty("timestamp", LocalDateTime.now());
    }

}

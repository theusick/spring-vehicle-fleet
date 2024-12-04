package com.theusick.api.exception.mapper;

import com.theusick.api.exception.ApiException;
import com.theusick.api.exception.model.ApiErrorResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.context.request.WebRequest;

@Mapper(componentModel = "spring")
public interface ApiExceptionMapper {

    @Mapping(target = "error", expression = "java(exception.getClass().getSimpleName())")
    @Mapping(target = "message", source = "exception.message")
    @Mapping(target = "description", expression = "java(request.getDescription(false))")
    @Mapping(target = "status", expression = "java(exception.getStatus().value())")
    @Mapping(target = "timestamp", expression = "java(java.time.LocalDateTime.now())")
    ApiErrorResponseModel apiResponseModelFromException(ApiException exception, WebRequest request);

}

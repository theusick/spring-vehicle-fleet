package com.theusick.core.security.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@Schema(name = "LoginResponse")
public class LoginResponse {

    String token;
    long expiresIn;

}

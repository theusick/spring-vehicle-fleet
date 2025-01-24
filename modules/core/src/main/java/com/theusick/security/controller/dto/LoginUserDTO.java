package com.theusick.security.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@Schema(name = "LoginUser")
public class LoginUserDTO {

    @NotBlank
    String username;
    @NotBlank
    String password;

}

package com.theusick.core.security.controller;

import com.theusick.core.api.exception.UnauthorizedApiException;
import com.theusick.core.security.controller.dto.LoginResponse;
import com.theusick.core.security.controller.dto.LoginUserDTO;
import com.theusick.core.security.repository.entity.User;
import com.theusick.core.security.service.AuthenticationService;
import com.theusick.core.security.service.JwtService;
import com.theusick.core.service.exception.NoSuchException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    @PostMapping(
        value = "/login",
        produces = {MediaType.APPLICATION_JSON_VALUE},
        consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public LoginResponse authenticate(@RequestBody @Valid LoginUserDTO loginUserDTO,
                                      HttpServletResponse response) {
        try {
            User authenticatedUser = authenticationService.authenticate(loginUserDTO);
            String jwtToken = jwtService.generateToken(authenticatedUser);

            response.addCookie(configuredJwtCookie(jwtToken));

            return LoginResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();
        } catch (NoSuchException | BadCredentialsException exception) {
            throw new UnauthorizedApiException(exception.getMessage());
        }
    }

    private Cookie configuredJwtCookie(@NotBlank String jwtToken) {
        Cookie jwtCookie = new Cookie(JwtService.JWT_COOKIE_NAME, jwtToken);

        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setAttribute("SameSite", "strict");
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge((int) TimeUnit.MILLISECONDS.toSeconds(jwtService.getExpirationTime()));

        return jwtCookie;
    }

}

package com.theusick.security.controller;

import com.theusick.api.exception.UnauthorizedApiException;
import com.theusick.security.controller.dto.LoginResponse;
import com.theusick.security.controller.dto.LoginUserDTO;
import com.theusick.security.repository.entity.User;
import com.theusick.security.service.AuthenticationService;
import com.theusick.security.service.JwtService;
import com.theusick.service.exception.NoSuchException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    @PostMapping(
        value = "/login",
        produces = {"application/json"},
        consumes = {"application/json"}
    )
    public LoginResponse authenticate(@RequestBody @Valid LoginUserDTO loginUserDTO) {
        try {
            User authenticatedUser = authenticationService.authenticate(loginUserDTO);
            String jwtToken = jwtService.generateToken(authenticatedUser);

            return LoginResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();
        } catch (NoSuchException | BadCredentialsException exception) {
            throw new UnauthorizedApiException(exception.getMessage());
        }
    }

}

package com.theusick.security.service;

import com.theusick.security.controller.dto.LoginUserDTO;
import com.theusick.security.repository.UserRepository;
import com.theusick.security.repository.entity.User;
import com.theusick.security.service.exception.NoSuchUserException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    public User authenticate(LoginUserDTO userLogin)
        throws AuthenticationException, NoSuchUserException {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(userLogin.getUsername(), userLogin.getPassword())
        );

        return userRepository.findByUsername(userLogin.getUsername())
            .orElseThrow(() -> new NoSuchUserException(userLogin.getUsername()));
    }

}

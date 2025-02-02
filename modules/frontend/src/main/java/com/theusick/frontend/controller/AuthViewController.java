package com.theusick.frontend.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Hidden
@Controller
@AllArgsConstructor
public class AuthViewController {

    @GetMapping("/login")
    public String loginPage() {
        return "pages/auth/login";
    }

}

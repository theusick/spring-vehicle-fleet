package com.theusick.frontend.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Hidden
@Controller
@RequestMapping("/overview")
@AllArgsConstructor
public class OverviewViewController {

    @GetMapping
    public String overviewPage() {
        return "pages/overview";
    }

}

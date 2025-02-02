package com.theusick.frontend.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Hidden
@Controller
@AllArgsConstructor
public class TablesViewController {

    @GetMapping("/enterprises")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public String enterprisesTable() {
        return "views/tables/enterprises";
    }

}

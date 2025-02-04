package com.theusick.frontend.controller;

import com.theusick.core.service.exception.NoSuchException;
import com.theusick.fleet.service.EnterpriseService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Hidden
@Controller
@AllArgsConstructor
public class TablesViewController {

    private final EnterpriseService enterpriseService;

    @GetMapping("/enterprises")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public String getEnterprisesTable() {
        return "views/tables/enterprises";
    }

    @GetMapping("/enterprises/{enterpriseId}/vehicles")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public String getEnterpriseVehiclesTable(@PathVariable Long enterpriseId,
                                             Model model) throws NoSuchException {
        model.addAttribute("enterpriseName",
            enterpriseService.getEnterprise(enterpriseId).getName());
        return "views/tables/enterprise-vehicles";
    }

}

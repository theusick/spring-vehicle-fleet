package com.theusick.controller;

import com.theusick.service.VehicleService;
import com.theusick.service.model.VehicleModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @GetMapping("vehicles")
    public String getVehicles(Model model) {
        return "vehicles";
    }

    @GetMapping("/components/vehicle_table")
    public String getVehicleTable(Model model) {
        List<VehicleModel> vehicles = vehicleService.getVehicles();
        model.addAttribute("vehicles", vehicles);
        return "components/vehicle_table";
    }

}

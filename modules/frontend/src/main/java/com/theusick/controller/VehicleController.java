package com.theusick.controller;

import com.theusick.service.VehicleBrandService;
import com.theusick.service.VehicleService;
import com.theusick.service.model.VehicleBrandModel;
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
    private final VehicleBrandService vehicleBrandService;

    @GetMapping("vehicles")
    public String getVehicles(Model model) {
        return "vehicles";
    }

    @GetMapping("brands")
    public String getVehicleBrands(Model model) {
        return "brands";
    }

    @GetMapping("/components/tables/vehicles_table")
    public String getVehicleTable(Model model) {
        List<VehicleModel> vehicles = vehicleService.getVehicles();
        model.addAttribute("vehicles", vehicles);
        return "components/tables/vehicles_table";
    }

    @GetMapping("/components/tables/vehicle_brands_table")
    public String getVehicleBrandsTable(Model model) {
        List<VehicleBrandModel> brands = vehicleBrandService.getVehicleBrands();
        model.addAttribute("brands", brands);
        return "components/tables/vehicle_brands_table";
    }

}

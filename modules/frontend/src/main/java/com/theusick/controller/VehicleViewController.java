package com.theusick.controller;

import com.theusick.api.exception.NotFoundApiException;
import com.theusick.service.VehicleBrandService;
import com.theusick.service.VehicleService;
import com.theusick.service.exception.NoSuchException;
import com.theusick.service.model.VehicleModel;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Hidden
@Controller
@RequestMapping("/vehicles")
@AllArgsConstructor
public class VehicleViewController {

    private final VehicleService vehicleService;
    private final VehicleBrandService vehicleBrandService;

    @GetMapping
    public String getVehicles(Model model) {
        model.addAttribute("vehicles", vehicleService.getVehicles());
        model.addAttribute("brands", vehicleBrandService.getVehicleBrands());
        return "views/tables/vehicles";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public VehicleModel getVehicle(@PathVariable Long id) {
        try {
            return vehicleService.getVehicle(id);
        } catch (NoSuchException exception) {
            throw new NotFoundApiException(exception.getMessage());
        }
    }

    @PostMapping
    public String createVehicle(@ModelAttribute VehicleModel vehicle) {
        try {
            vehicleService.createVehicle(vehicle.getBrandId(), vehicle);
            return "redirect:/vehicles";
        } catch (NoSuchException exception) {
            throw new NotFoundApiException(exception.getMessage());
        }
    }

    @PutMapping
    public String updateVehicle(VehicleModel vehicle) {
        try {
            vehicleService.updateVehicle(vehicle);
            return "redirect:/vehicles";
        } catch (NoSuchException exception) {
            throw new NotFoundApiException(exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public String deleteVehicle(@PathVariable Long id) {
        try {
            vehicleService.deleteVehicle(id);
            return "redirect:/vehicles";
        } catch (NoSuchException exception) {
            throw new NotFoundApiException(exception.getMessage());
        }
    }

}

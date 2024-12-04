package com.theusick.controller;

import com.theusick.api.exception.NotFoundApiException;
import com.theusick.service.VehicleBrandService;
import com.theusick.service.exception.NoSuchException;
import com.theusick.service.model.VehicleBrandModel;
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

import java.util.List;

@Hidden
@Controller
@RequestMapping("/brands")
@AllArgsConstructor
public class VehicleBrandViewController {

    private final VehicleBrandService vehicleBrandService;

    @GetMapping
    public String getVehicleBrands(Model model) {
        List<VehicleBrandModel> brands = vehicleBrandService.getVehicleBrands();
        model.addAttribute("brands", brands);
        return "views/tables/brands";
    }

    @GetMapping("/{vehicleBrandId}")
    @ResponseBody
    public VehicleBrandModel getVehicleBrand(@PathVariable Long vehicleBrandId) {
        try {
            return vehicleBrandService.getVehicleBrand(vehicleBrandId);
        } catch (NoSuchException exception) {
            throw new NotFoundApiException(exception.getMessage());
        }
    }

    @PostMapping
    public String createVehicleBrand(@ModelAttribute VehicleBrandModel vehicleBrand) {
        vehicleBrandService.createVehicleBrand(vehicleBrand);
        return "redirect:/brands";
    }

    @PutMapping
    public String updateVehicleBrand(VehicleBrandModel vehicleBrand) {
        try {
            vehicleBrandService.updateVehicleBrand(vehicleBrand);
            return "redirect:/brands";
        } catch (NoSuchException exception) {
            throw new NotFoundApiException(exception.getMessage());
        }
    }

    @DeleteMapping("/{vehicleBrandId}")
    public String deleteVehicleBrand(@PathVariable Long vehicleBrandId) {
        try {
            vehicleBrandService.deleteVehicleBrand(vehicleBrandId);
            return "redirect:/brands";
        } catch (NoSuchException exception) {
            throw new NotFoundApiException(exception.getMessage());
        }
    }

}

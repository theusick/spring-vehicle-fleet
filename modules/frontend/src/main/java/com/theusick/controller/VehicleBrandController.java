package com.theusick.controller;

import com.theusick.api.exception.NotFoundApiException;
import com.theusick.service.VehicleBrandService;
import com.theusick.service.exception.NoSuchException;
import com.theusick.service.model.VehicleBrandModel;
import com.theusick.service.model.VehicleModel;
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

@Controller
@RequestMapping("/brands")
@AllArgsConstructor
public class VehicleBrandController {

    private final VehicleBrandService vehicleBrandService;

    @GetMapping
    public String getVehicleBrands(Model model) {
        List<VehicleBrandModel> brands = vehicleBrandService.getVehicleBrands();
        model.addAttribute("brands", brands);
        return "views/tables/brands";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public VehicleBrandModel getVehicleBrand(@PathVariable Long id) {
        try {
            return vehicleBrandService.getVehicleBrand(id);
        } catch (NoSuchException exception) {
            throw new NotFoundApiException(exception.getMessage());
        }
    }

    @PostMapping
    public String createVehicleBrand(@ModelAttribute VehicleBrandModel vehicleBrand) {
        try {
            vehicleBrandService.createVehicleBrand(vehicleBrand);
            return "redirect:/brands";
        } catch (NoSuchException exception) {
            throw new NotFoundApiException(exception.getMessage());
        }
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

    @DeleteMapping("/{id}")
    public String deleteVehicleBrand(@PathVariable Long id) {
        try {
            vehicleBrandService.deleteVehicleBrand(id);
            return "redirect:/brands";
        } catch (NoSuchException exception) {
            throw new NotFoundApiException(exception.getMessage());
        }
    }

}

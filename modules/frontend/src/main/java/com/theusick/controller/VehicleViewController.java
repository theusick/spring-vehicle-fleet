package com.theusick.controller;

import com.theusick.api.exception.NotFoundApiException;
import com.theusick.controller.dto.vehicle.EnterpriseVehicleDTO;
import com.theusick.service.EnterpriseService;
import com.theusick.service.VehicleBrandService;
import com.theusick.service.VehicleService;
import com.theusick.service.exception.NoSuchException;
import com.theusick.service.mapper.VehicleMapper;
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
    private final VehicleMapper vehicleMapper;

    private final EnterpriseService enterpriseService;

    private final VehicleBrandService vehicleBrandService;

    @GetMapping
    public String getVehicles(Model model) {
        model.addAttribute("vehicles",
            vehicleService.getVehicles()
                .stream()
                .map(vehicleMapper::enterpriseVehicleDTOFromModel)
                .toList());
        model.addAttribute("enterprises", enterpriseService.getEnterprises());
        model.addAttribute("brands", vehicleBrandService.getVehicleBrands());
        return "views/tables/vehicles";
    }

    @GetMapping("/{vehicleId}")
    @ResponseBody
    public EnterpriseVehicleDTO getVehicle(@PathVariable Long vehicleId) {
        try {
            return vehicleMapper.enterpriseVehicleDTOFromModel(vehicleService.getVehicle(vehicleId));
        } catch (NoSuchException exception) {
            throw new NotFoundApiException(exception.getMessage());
        }
    }

    @PostMapping
    public String createVehicle(@ModelAttribute EnterpriseVehicleDTO vehicleDTO) {
        try {
            vehicleService.createVehicle(vehicleDTO.getEnterpriseId(),
                vehicleDTO.getVehicle().getBrandId(),
                vehicleMapper.vehicleModelFromEnterpriseDTO(vehicleDTO));
            return "redirect:/vehicles";
        } catch (NoSuchException exception) {
            throw new NotFoundApiException(exception.getMessage());
        }
    }

    @PutMapping
    public String updateVehicle(EnterpriseVehicleDTO vehicleDTO) {
        try {
            vehicleService.updateVehicle(vehicleMapper.vehicleModelFromEnterpriseDTO(vehicleDTO));
            return "redirect:/vehicles";
        } catch (NoSuchException exception) {
            throw new NotFoundApiException(exception.getMessage());
        }
    }

    @DeleteMapping("/{vehicleId}")
    public String deleteVehicle(@PathVariable Long vehicleId) {
        try {
            vehicleService.deleteVehicle(vehicleId);
            return "redirect:/vehicles";
        } catch (NoSuchException exception) {
            throw new NotFoundApiException(exception.getMessage());
        }
    }

}

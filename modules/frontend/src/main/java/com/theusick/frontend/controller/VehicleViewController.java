package com.theusick.frontend.controller;

import com.theusick.core.api.exception.NotFoundApiException;
import com.theusick.frontend.controller.dto.VehicleViewDTO;
import com.theusick.frontend.controller.mapper.EnterpriseViewMapper;
import com.theusick.frontend.controller.mapper.VehicleViewMapper;
import com.theusick.fleet.service.EnterpriseService;
import com.theusick.fleet.service.VehicleBrandService;
import com.theusick.fleet.service.VehicleService;
import com.theusick.core.service.exception.NoSuchException;
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
    private final VehicleViewMapper vehicleViewMapper;

    private final EnterpriseService enterpriseService;
    private final EnterpriseViewMapper enterpriseViewMapper;

    private final VehicleBrandService vehicleBrandService;

    @GetMapping
    public String getVehicles(Model model) {
        model.addAttribute("vehicles",
            vehicleService.getVehicles()
                .stream()
                .map(vehicleViewMapper::vehicleViewDTOFromModel)
                .toList());
        model.addAttribute("enterprises",
            enterpriseService.getEnterprises()
                .stream()
                .map(enterpriseViewMapper::enterpriseViewDTOFromModel)
                .toList());
        model.addAttribute("brands", vehicleBrandService.getVehicleBrands());
        return "views/tables/vehicles";
    }

    @GetMapping("/{vehicleId}")
    @ResponseBody
    public VehicleViewDTO getVehicle(@PathVariable Long vehicleId) {
        try {
            return vehicleViewMapper.vehicleViewDTOFromModel(vehicleService.getVehicle(vehicleId));
        } catch (NoSuchException exception) {
            throw new NotFoundApiException(exception.getMessage());
        }
    }

    @PostMapping
    public String createVehicle(@ModelAttribute VehicleViewDTO vehicleDTO) {
        try {
            vehicleService.createVehicle(vehicleDTO.getEnterpriseId(),
                vehicleDTO.getBrandId(),
                vehicleViewMapper.vehicleModelFromDTO(vehicleDTO));
            return "redirect:/vehicles";
        } catch (NoSuchException exception) {
            throw new NotFoundApiException(exception.getMessage());
        }
    }

    @PutMapping
    public String updateVehicle(VehicleViewDTO vehicleDTO) {
        try {
            vehicleService.updateVehicle(vehicleViewMapper.vehicleModelFromDTO(vehicleDTO));
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

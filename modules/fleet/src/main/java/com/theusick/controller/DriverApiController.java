package com.theusick.controller;

import com.theusick.service.DriverService;
import com.theusick.service.model.DriverModel;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Driver")
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
public class DriverApiController {

    private final DriverService driverService;

    @GetMapping
    public List<DriverModel> getDrivers() {
        return driverService.getDrivers();
    }

}

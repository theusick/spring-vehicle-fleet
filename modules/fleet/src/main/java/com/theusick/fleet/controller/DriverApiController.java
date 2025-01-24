package com.theusick.fleet.controller;

import com.theusick.fleet.controller.dto.driver.DriverBaseDTO;
import com.theusick.fleet.service.DriverService;
import com.theusick.fleet.service.mapper.DriverMapper;
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
    private final DriverMapper driverMapper;

    @GetMapping
    public List<DriverBaseDTO> getDrivers() {
        return driverService.getDrivers()
            .stream()
            .map(driverMapper::driverBaseDTOFromModel)
            .toList();
    }

}

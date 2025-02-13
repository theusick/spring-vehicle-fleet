package com.theusick.fleet.service.impl;

import com.theusick.fleet.repository.VehicleTelemetryRepository;
import com.theusick.fleet.service.VehicleTelemetryService;
import com.theusick.fleet.service.mapper.VehicleTelemetryMapper;
import com.theusick.fleet.service.model.VehicleTelemetryModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class VehicleTelemetryServiceImpl implements VehicleTelemetryService {

    private final VehicleTelemetryRepository vehicleTelemetryRepository;
    private final VehicleTelemetryMapper vehicleTelemetryMapper;

    @Override
    public List<VehicleTelemetryModel> getVehicleTelemetry(Long vehicleId,
                                                           OffsetDateTime start,
                                                           OffsetDateTime end) {
        return vehicleTelemetryRepository
            .findByVehicleIdAndTimestampBetween(vehicleId, start.toInstant(), end.toInstant()).stream()
            .map(vehicleTelemetryMapper::telemetryModelFromEntity)
            .toList();
    }

    @Override
    public List<VehicleTelemetryModel> getVehicleTelemetry(Long vehicleId) {
        return vehicleTelemetryRepository.findByVehicleId(vehicleId).stream()
            .map(vehicleTelemetryMapper::telemetryModelFromEntity)
            .toList();
    }

}

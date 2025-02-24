package com.theusick.fleet.service.impl;

import com.theusick.fleet.repository.VehicleRepository;
import com.theusick.fleet.repository.VehicleTelemetryRepository;
import com.theusick.fleet.repository.entity.VehicleEntity;
import com.theusick.fleet.repository.entity.VehicleTelemetryEntity;
import com.theusick.fleet.service.VehicleTelemetryService;
import com.theusick.fleet.service.exception.NoSuchVehicleException;
import com.theusick.fleet.service.mapper.VehicleTelemetryMapper;
import com.theusick.fleet.service.mapper.VehicleTelemetryRouteMapper;
import com.theusick.fleet.service.model.VehicleTelemetryModel;
import com.theusick.fleet.service.model.VehicleTelemetryRouteModel;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class VehicleTelemetryServiceImpl implements VehicleTelemetryService {

    private final VehicleTelemetryRepository vehicleTelemetryRepository;
    private final VehicleTelemetryMapper vehicleTelemetryMapper;

    private final VehicleTelemetryRouteMapper vehicleTelemetryRouteMapper;

    private final VehicleRepository vehicleRepository;

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

    @Override
    public VehicleTelemetryRouteModel getVehicleTelemetryRoute(Long vehicleId,
                                                               OffsetDateTime start,
                                                               OffsetDateTime end) {
        VehicleEntity vehicleEnterprise = vehicleRepository.findById(vehicleId).orElseThrow();
        return vehicleTelemetryRouteMapper.routeModelFromEntities(
            vehicleTelemetryRepository
                .findByVehicleIdAndTimestampBetween(vehicleId, start.toInstant(), end.toInstant()),
            vehicleEnterprise.getEnterprise());
    }

    @Override
    @Transactional
    public VehicleTelemetryModel createTelemetryPoint(Long vehicleId,
                                                      VehicleTelemetryModel telemetryModel)
        throws NoSuchVehicleException {
        VehicleEntity vehicleEntity = vehicleRepository.findById(vehicleId)
            .orElseThrow(() -> new NoSuchVehicleException(vehicleId));

        VehicleTelemetryEntity telemetryEntity = VehicleTelemetryEntity.builder()
            .vehicle(vehicleEntity)
            .build();

        vehicleTelemetryMapper.updateVehicleTelemetryEntityFromModel(telemetryEntity, telemetryModel);
        vehicleTelemetryRepository.save(telemetryEntity);
        return vehicleTelemetryMapper.telemetryModelFromEntity(telemetryEntity);
    }

}

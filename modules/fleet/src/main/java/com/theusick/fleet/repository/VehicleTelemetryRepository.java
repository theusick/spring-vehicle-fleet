package com.theusick.fleet.repository;

import com.theusick.fleet.repository.entity.VehicleTelemetryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface VehicleTelemetryRepository extends JpaRepository<VehicleTelemetryEntity, Long> {

    List<VehicleTelemetryEntity> findByVehicleIdAndTimestampBetween(Long vehicleId,
                                                                    Instant start,
                                                                    Instant end);

    List<VehicleTelemetryEntity> findByVehicleId(Long vehicleId);

}

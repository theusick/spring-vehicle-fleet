package com.theusick.fleet.service;

import com.theusick.fleet.service.model.VehicleTelemetryModel;

import java.time.OffsetDateTime;
import java.util.List;

public interface VehicleTelemetryService {

    List<VehicleTelemetryModel> getVehicleTelemetry(Long vehicleId,
                                                    OffsetDateTime start,
                                                    OffsetDateTime end);

    List<VehicleTelemetryModel> getVehicleTelemetry(Long vehicleId);

}

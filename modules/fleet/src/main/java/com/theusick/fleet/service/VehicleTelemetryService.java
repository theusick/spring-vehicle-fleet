package com.theusick.fleet.service;

import com.theusick.fleet.service.exception.NoSuchVehicleException;
import com.theusick.fleet.service.model.VehicleTelemetryModel;
import com.theusick.fleet.service.model.VehicleTelemetryRouteModel;

import java.time.OffsetDateTime;
import java.util.List;

public interface VehicleTelemetryService {

    List<VehicleTelemetryModel> getVehicleTelemetry(Long vehicleId,
                                                    OffsetDateTime start,
                                                    OffsetDateTime end);

    List<VehicleTelemetryModel> getVehicleTelemetry(Long vehicleId);

    VehicleTelemetryRouteModel getVehicleTelemetryRoute(Long vehicleId,
                                                        OffsetDateTime start,
                                                        OffsetDateTime end);

    VehicleTelemetryModel createTelemetryPoint(Long vehicleId,
                                               VehicleTelemetryModel telemetryModel)
        throws NoSuchVehicleException;

}

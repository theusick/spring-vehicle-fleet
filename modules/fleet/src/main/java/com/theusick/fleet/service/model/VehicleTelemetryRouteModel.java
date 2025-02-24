package com.theusick.fleet.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.LineString;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleTelemetryRouteModel {

    private Long vehicleId;

    private LineString route;

    private OffsetDateTime timestampStart;
    private OffsetDateTime timestampEnd;

}

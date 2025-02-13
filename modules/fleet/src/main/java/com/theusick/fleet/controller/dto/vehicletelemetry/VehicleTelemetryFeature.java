package com.theusick.fleet.controller.dto.vehicletelemetry;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.locationtech.jts.geom.Point;

import java.util.Map;

@Value
@Builder
@Jacksonized
@Schema
public class VehicleTelemetryFeature implements Feature {

    Point geometry;
    Long id;
    Map<String, Object> properties;

}

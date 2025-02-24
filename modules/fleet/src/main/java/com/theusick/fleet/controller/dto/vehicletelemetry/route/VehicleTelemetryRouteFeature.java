package com.theusick.fleet.controller.dto.vehicletelemetry.route;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.theusick.fleet.controller.dto.vehicletelemetry.Feature;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.locationtech.jts.geom.LineString;

import java.util.Map;

@Value
@Builder
@Jacksonized
@Schema
public class VehicleTelemetryRouteFeature implements Feature {

    LineString geometry;
    @JsonIgnore
    Long id;
    Map<String, Object> properties;

}

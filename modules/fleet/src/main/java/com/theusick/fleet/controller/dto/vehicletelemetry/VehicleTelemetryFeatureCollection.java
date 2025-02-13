package com.theusick.fleet.controller.dto.vehicletelemetry;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
@Schema
public class VehicleTelemetryFeatureCollection implements FeatureCollection<VehicleTelemetryFeature> {

    List<VehicleTelemetryFeature> features;

}


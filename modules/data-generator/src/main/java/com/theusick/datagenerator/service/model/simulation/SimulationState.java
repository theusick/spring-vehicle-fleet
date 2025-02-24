package com.theusick.datagenerator.service.model.simulation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class SimulationState {

    private final Coordinate[] routeCoordinates;
    private final long simulationIntervalMs;

    @Builder.Default
    long accumulatedTime = 0L;
    @Builder.Default
    double accumulatedDistance = 0.0;
    long remainingInterval;
    @Builder.Default
    long elapsedInSegment = 0L;
    @Builder.Default
    int segmentIndex = 0;

}

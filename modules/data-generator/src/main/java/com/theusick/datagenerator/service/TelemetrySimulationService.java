package com.theusick.datagenerator.service;

import org.locationtech.jts.geom.Point;

public interface TelemetrySimulationService {

    void startSimulation(Long vehicleId,
                         Point circleCenter,
                         Double radiusKm,
                         Double maxDistanceKm);

}

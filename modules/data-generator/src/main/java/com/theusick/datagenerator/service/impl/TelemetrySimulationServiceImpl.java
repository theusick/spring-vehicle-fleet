package com.theusick.datagenerator.service.impl;

import com.theusick.datagenerator.service.TelemetrySimulationService;
import com.theusick.datagenerator.service.model.simulation.GraphHopperSimulation;
import com.theusick.fleet.service.VehicleTelemetryService;
import com.theusick.fleet.service.model.VehicleTelemetryModel;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class TelemetrySimulationServiceImpl implements TelemetrySimulationService {

    private static final long SIMULATION_INTERVAL_MS = 10_000L;
    private final ThreadPoolTaskExecutor simulationTaskExecutor;
    private final GraphHopperSimulation graphHopperSimulation;
    private final VehicleTelemetryService vehicleTelemetryService;

    @Override
    public void startSimulation(Long vehicleId,
                                Point circleCenter,
                                @DecimalMin("0.5") Double radiusKm,
                                Double maxDistanceKm) {
        List<Point> findRouteForPoints = generateOppositePoints(circleCenter, radiusKm);

        log.debug(
            "Generated starting points for route: point1={}, point2={}",
            findRouteForPoints.getFirst(),
            findRouteForPoints.getLast());

        List<Point> simulationPoints = graphHopperSimulation.getSimulationPoints(
            findRouteForPoints,
            maxDistanceKm,
            SIMULATION_INTERVAL_MS);

        simulationTaskExecutor.execute(() -> {
            for (Point simulationPoint : simulationPoints) {
                try {
                    VehicleTelemetryModel telemetryModel = VehicleTelemetryModel.builder()
                        .coordinates(simulationPoint)
                        .timestamp(OffsetDateTime.ofInstant(Instant.now(), ZoneOffset.UTC))
                        .build();
                    vehicleTelemetryService.createTelemetryPoint(vehicleId, telemetryModel);
                    log.info(
                        "Saved telemetry for vehicle {} at point {}",
                        vehicleId,
                        simulationPoint);
                    Thread.sleep(SIMULATION_INTERVAL_MS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log.error("Simulation interrupted", e);
                    break;
                } catch (Exception ex) {
                    log.error("Error saving telemetry", ex);
                }
            }
            log.info(
                "Simulation ended for vehicle {} with {} points",
                vehicleId,
                simulationPoints.size());
        });
    }

    private List<Point> generateOppositePoints(Point center, Double radiusKm) {
        final double randomAngle = Math.random() * 2 * Math.PI;
        final double approximateDegreeDistance = 111.0;

        double deltaLat = (radiusKm / approximateDegreeDistance) * Math.cos(randomAngle);
        double deltaLon = (radiusKm / (approximateDegreeDistance *
            Math.cos(Math.toRadians(center.getY())))) * Math.sin(randomAngle);

        double point1Lat = center.getX() + deltaLat;
        double point1Lon = center.getY() + deltaLon;
        double point2Lat = center.getX() - deltaLat;
        double point2Lon = center.getY() - deltaLon;

        GeometryFactory geometryFactory = new GeometryFactory();
        return List.of(
            geometryFactory.createPoint(new Coordinate(point1Lat, point1Lon)),
            geometryFactory.createPoint(new Coordinate(point2Lat, point2Lon)));
    }

}

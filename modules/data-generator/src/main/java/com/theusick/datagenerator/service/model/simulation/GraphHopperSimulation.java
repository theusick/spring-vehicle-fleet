package com.theusick.datagenerator.service.model.simulation;

import com.theusick.datagenerator.service.model.api.GraphHopperRouteRequest;
import com.theusick.datagenerator.service.model.api.GraphHopperRouteResponse;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class GraphHopperSimulation {

    private final GeometryFactory geometryFactory = new GeometryFactory();

    private final RestTemplate graphHopperClient;

    public List<Point> getSimulationPoints(List<Point> findRouteForPoints,
                                           double maxDistanceKm,
                                           @Positive long simulationIntervalMs) {
        return getSimulationPoints(
            getRouteResponse(findRouteForPoints).getPaths().getFirst(),
            maxDistanceKm,
            simulationIntervalMs);
    }

    private GraphHopperRouteResponse getRouteResponse(@Size(min = 1, max = 20) List<Point> points) {
        HttpEntity<GraphHopperRouteRequest> request =
            new HttpEntity<>(GraphHopperRouteRequest.builder()
                .points(points)
                .build());

        return graphHopperClient.postForObject(
            "/route?key={apiKey}",
            request,
            GraphHopperRouteResponse.class
        );
    }

    private List<Point> getSimulationPoints(GraphHopperRouteResponse.Paths path,
                                            double maxDistanceKm,
                                            long simulationIntervalMs) {
        Coordinate[] routeCoordinates = path.getPoints().getCoordinates();
        if (routeCoordinates.length == 0) {
            return new ArrayList<>();
        }

        final double maxDistanceMeters = maxDistanceKm * 1000;
        List<Point> simulationPoints = new ArrayList<>();

        simulationPoints.add(geometryFactory.createPoint(routeCoordinates[0]));

        List<GraphHopperRouteResponse.DetailSegment> timeSegments = path.getDetails().getTime();
        List<GraphHopperRouteResponse.DetailSegment> distanceSegments =
            path.getDetails().getDistance();

        if (timeSegments.size() != distanceSegments.size()) {
            throw new RuntimeException("GraphHopper details time and distance have different " +
                "dimensions");
        }

        SimulationState state = new SimulationState.SimulationStateBuilder()
            .remainingInterval(simulationIntervalMs)
            .routeCoordinates(routeCoordinates)
            .simulationIntervalMs(simulationIntervalMs)
            .build();

        for (; state.segmentIndex < timeSegments.size() &&
            state.accumulatedDistance < maxDistanceMeters; ) {
            GraphHopperRouteResponse.DetailSegment currentTimeSegment =
                timeSegments.get(state.segmentIndex);
            GraphHopperRouteResponse.DetailSegment currentDistanceSegment =
                distanceSegments.get(state.segmentIndex);

            long segmentTime = (long) currentTimeSegment.getValue();
            long availableSegmentTime = segmentTime - state.elapsedInSegment;

            boolean isEnoughSeconds = (availableSegmentTime / 1000) ==
                (state.remainingInterval / 1000);

            if (isEnoughSeconds) {
                processExactSegment(
                    state,
                    currentTimeSegment,
                    currentDistanceSegment,
                    simulationPoints);
            } else if (availableSegmentTime < state.remainingInterval) {
                processSegmentInsufficientTime(
                    state,
                    currentDistanceSegment,
                    availableSegmentTime);
            } else {
                processSegmentInterpolation(
                    state,
                    currentTimeSegment,
                    currentDistanceSegment,
                    segmentTime,
                    simulationPoints);
            }
        }

        log.debug(
            "Counted simulation points, simulation time = `{}`, simulation distance = `{}`",
            state.accumulatedTime,
            state.accumulatedDistance);

        return simulationPoints;
    }

    private void processExactSegment(SimulationState state,
                                     GraphHopperRouteResponse.DetailSegment timeSegment,
                                     GraphHopperRouteResponse.DetailSegment distanceSegment,
                                     List<Point> simulationPoints) {
        long segmentTime = (long) timeSegment.getValue();
        long availableSegmentTime = segmentTime - state.elapsedInSegment;

        addPointAtSegmentEnd(timeSegment, state.getRouteCoordinates(), simulationPoints);

        state.accumulatedTime += availableSegmentTime;
        state.accumulatedDistance += distanceSegment.getValue();
        state.remainingInterval = state.getSimulationIntervalMs();
        state.elapsedInSegment = 0L;
        state.segmentIndex++;
    }

    private void processSegmentInsufficientTime(SimulationState state,
                                                GraphHopperRouteResponse.DetailSegment distanceSegment,
                                                long availableSegmentTime) {
        state.accumulatedTime += availableSegmentTime;
        state.accumulatedDistance += distanceSegment.getValue();
        state.remainingInterval -= availableSegmentTime;
        state.elapsedInSegment = 0L;
        state.segmentIndex++;
    }

    private void processSegmentInterpolation(SimulationState state,
                                             GraphHopperRouteResponse.DetailSegment timeSegment,
                                             GraphHopperRouteResponse.DetailSegment distanceSegment,
                                             long segmentTime,
                                             List<Point> simulationPoints) {
        double fraction = (double) (state.elapsedInSegment + state.remainingInterval) / segmentTime;
        addInterpolatedPoint(timeSegment, fraction, state.getRouteCoordinates(), simulationPoints);

        state.accumulatedTime += state.remainingInterval;
        state.accumulatedDistance += fraction * distanceSegment.getValue();
        state.elapsedInSegment += state.remainingInterval;
        state.remainingInterval = state.getSimulationIntervalMs();
    }

    private void addPointAtSegmentEnd(GraphHopperRouteResponse.DetailSegment timeSegment,
                                      Coordinate[] routeCoordinates,
                                      List<Point> simulationPoints) {
        int endCoordinateIndex = timeSegment.getTo();
        simulationPoints.add(geometryFactory.createPoint(routeCoordinates[endCoordinateIndex]));
    }

    private void addInterpolatedPoint(GraphHopperRouteResponse.DetailSegment timeSegment,
                                      double fraction,
                                      Coordinate[] routeCoordinates,
                                      List<Point> simulationPoints) {
        int fromIndex = timeSegment.getFrom();
        int toIndex = timeSegment.getTo();
        Coordinate startCoordinate = routeCoordinates[fromIndex];
        Coordinate endCoordinate = routeCoordinates[toIndex];
        double interpolatedX = startCoordinate.x + fraction * (endCoordinate.x - startCoordinate.x);
        double interpolatedY = startCoordinate.y + fraction * (endCoordinate.y - startCoordinate.y);
        simulationPoints.add(geometryFactory.createPoint(new Coordinate(interpolatedX, interpolatedY)));
    }

}

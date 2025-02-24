package com.theusick.datagenerator.command;

import com.theusick.datagenerator.service.TelemetrySimulationService;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.shell.command.CommandHandlingResult;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.ExceptionResolver;
import org.springframework.shell.command.annotation.ExitCode;
import org.springframework.shell.command.annotation.Option;

@Command(group = "Vehicle Route Simulation")
@RequiredArgsConstructor
public class TelemetrySimulationCommand {

    private final TelemetrySimulationService telemetrySimulationService;

    @Command(
        command = "simulate",
        alias = {"sim"},
        description = "Simulate telemetry geometry points (route) and save in DB with given delay" +
            " in seconds"
    )
    public void simulateTelemetry(
        @Option(shortNames = 'v', required = true) @PositiveOrZero long vehicleId,
        @Option(shortNames = 'r', required = true) @DecimalMin("0.5") double radiusKm,
        @Option(shortNames = 'd', required = true) @Positive double maxDistanceKm,
        @Option(required = true) double centerLat,
        @Option(required = true) double centerLon
    ) {
        GeometryFactory geometryFactory = new GeometryFactory();

        telemetrySimulationService.startSimulation(
            vehicleId,
            geometryFactory.createPoint(new Coordinate(centerLat, centerLon)),
            radiusKm,
            maxDistanceKm
        );
    }

    @ExceptionResolver({
        RuntimeException.class
    })
    @ExitCode(code = 1)
    public CommandHandlingResult simulatorExceptionHandler(Exception exception) {
        return CommandHandlingResult.of(exception.getMessage());
    }

}

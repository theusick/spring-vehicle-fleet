package com.theusick.datagenerator.command;

import com.theusick.core.service.exception.NoSuchException;
import com.theusick.datagenerator.service.GeneratorService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.command.CommandHandlingResult;
import org.springframework.shell.command.annotation.ExceptionResolver;
import org.springframework.shell.command.annotation.ExitCode;
import org.springframework.shell.command.annotation.Option;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class EnterpriseGeneratorCommand {

    private final GeneratorService generatorService;

    @ShellMethod("Generate number of VehicleEntity and DriverEntity for existing enterprise")
    public String generate(
        @Option(shortNames = 'e', required = true) @PositiveOrZero Long enterpriseId,
        @Option(shortNames = 'v', required = true) @Min(1) @Max(10000) int vehiclesCount,
        @Option(shortNames = 'd', required = true) @Min(1) @Max(10000) int driversCount
    ) throws NoSuchException {
        List<Long> vehicleIds = generatorService.generateAndSaveVehicles(
            enterpriseId,
            vehiclesCount);

        List<Long> driverIds = generatorService.generateAndSaveDrivers(
            enterpriseId,
            driversCount,
            vehicleIds);

        return String.format("Successfully generated for enterprise with ID %d:%n" +
                "- Vehicles: %d%n" +
                "- Drivers: %d",
            enterpriseId, vehicleIds.size(), driverIds.size());
    }

    @ExceptionResolver({
        NoSuchException.class
    })
    @ExitCode(code = 1)
    public CommandHandlingResult generatorExceptionHandler(Exception exception) {
        return CommandHandlingResult.of(exception.getMessage());
    }

}

package com.theusick.datagenerator.command.config;

import com.theusick.datagenerator.command.EnterpriseGeneratorCommand;
import com.theusick.datagenerator.command.TelemetrySimulationCommand;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.command.annotation.EnableCommand;

@Configuration
@EnableCommand({
    EnterpriseGeneratorCommand.class,
    TelemetrySimulationCommand.class
})
@ConditionalOnProperty(name = "datagenerator.shell.enabled", havingValue = "true")
public class GeneratorShellCommandConfig {
}

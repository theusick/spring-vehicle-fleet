package com.theusick.datagenerator.config;

import com.theusick.datagenerator.command.EnterpriseGeneratorCommand;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.command.annotation.EnableCommand;

@Configuration
@EnableCommand(EnterpriseGeneratorCommand.class)
@ConditionalOnProperty(name = "datagenerator.shell.enabled", havingValue = "true")
public class GeneratorShellCommandConfig {
}

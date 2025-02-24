package com.theusick.datagenerator.executor.config;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Component
@Validated
@ConfigurationProperties(prefix = "task.executor")
public class TaskExecutorProperties {

    @Min(1)
    private int corePoolSize = 5;
    @Min(1)
    private int maxPoolSize = 15;
    @Min(1)
    private int queueCapacity = 50;
    private String threadNamePrefix = "RouteSimulationService-";

}

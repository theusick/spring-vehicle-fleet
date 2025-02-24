package com.theusick.datagenerator.api.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Setter
@Getter
@Validated
@NoArgsConstructor
@ConfigurationProperties(prefix = "graphhopper")
public class GraphHopperApiProperties {

    @NotBlank
    @Value("${graphhopper.api.url}")
    private String apiUrl;
    @NotBlank
    @Value("${graphhopper.api.key}")
    private String apiKey;

    @Positive
    private long readTimeout = 30;

}

package com.theusick.datagenerator.api.config;

import com.theusick.datagenerator.api.exception.handler.GraphHopperApiErrorHandler;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.Duration;
import java.util.Map;

@Configuration
@AllArgsConstructor
@EnableConfigurationProperties({GraphHopperApiProperties.class})
public class GraphHopperApiConfig {

    private final GraphHopperApiProperties properties;

    private final GraphHopperApiErrorHandler errorHandler;

    @Bean
    @ConditionalOnProperty({"graphhopper.api.url"})
    public RestTemplate graphHopperClient(RestTemplateBuilder builder) {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(properties.getApiUrl());
        factory.setDefaultUriVariables(Map.of("apiKey", properties.getApiKey()));

        return builder
            .setReadTimeout(Duration.ofSeconds(properties.getReadTimeout()))
            .uriTemplateHandler(factory)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .errorHandler(errorHandler)
            // Default ClientHttpRequestFactory does not return error body
            // https://stackoverflow.com/questions/62309635/resttemplate-get-with-body
            .requestFactory(() -> new JdkClientHttpRequestFactory())
            .build();
    }

}

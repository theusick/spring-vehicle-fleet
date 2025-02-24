package com.theusick.datagenerator.api.exception.model;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
@Hidden
public class GraphHopperErrorResponse {

    String message;
    List<HintItems> hints;

    @Value
    @Builder
    @Jacksonized
    public static class HintItems {

        String message;
        String details;

    }

}

package com.theusick.fleet.controller.dto.enterprise;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import lombok.extern.jackson.Jacksonized;

import java.time.ZoneId;
import java.util.List;

@With
@Value
@Builder
@Jacksonized
@Schema(name = "Enterprise")
public class EnterpriseBaseDTO {

    @NotNull
    Long id;
    @NotBlank
    String name;
    @NotBlank
    String city;
    @NotNull
    ZoneId timezone;

    @NotNull
    @JsonProperty("vehicles")
    List<Long> vehicleIds;
    @NotNull
    @JsonProperty("drivers")
    List<Long> driverIds;

}

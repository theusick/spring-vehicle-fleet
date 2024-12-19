package com.theusick.controller.dto.enterprise;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@Jacksonized
@Schema(name = "EnterpriseResponse")
public class EnterpriseBaseDTO {

    private Long id;
    private String name;
    private String city;

    private List<Long> vehicles;
    private List<Long> drivers;

}

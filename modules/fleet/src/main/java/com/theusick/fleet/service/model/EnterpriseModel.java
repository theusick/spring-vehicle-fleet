package com.theusick.fleet.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.ZoneId;
import java.util.List;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnterpriseModel {

    private Long id;

    private String name;
    private String city;

    private ZoneId timezone;

    List<Long> vehicleIds;
    List<Long> driverIds;

}

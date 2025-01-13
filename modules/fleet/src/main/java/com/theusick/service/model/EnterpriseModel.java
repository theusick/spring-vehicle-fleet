package com.theusick.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

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

    List<VehicleModel> vehicles;
    List<DriverModel> drivers;

}

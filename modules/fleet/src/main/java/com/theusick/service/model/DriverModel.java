package com.theusick.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverModel {

    private Long id;

    private String name;
    private int age;
    private double salary;

    private Long enterpriseId;
    private Long vehicleId;

    private boolean active;

}

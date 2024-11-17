package com.theusick.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "vehicle_brands")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleBrandEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String type;
    private int seats;
    private double fuelTank;
    private double payloadCapacity;

}

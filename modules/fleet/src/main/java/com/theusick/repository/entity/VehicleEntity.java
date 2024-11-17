package com.theusick.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "vehicles")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleEntity {

    @Id
    @GeneratedValue
    private Long id;

    private int year;
    private int mileage;
    private String color;
    private double price;
    private String plateNumber;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private VehicleBrandEntity brand;

}

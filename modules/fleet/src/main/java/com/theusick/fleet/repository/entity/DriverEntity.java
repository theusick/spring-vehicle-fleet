package com.theusick.fleet.repository.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "drivers")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DriverEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String name;
    private int age;
    private double salary;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enterprise_id", nullable = false)
    private EnterpriseEntity enterprise;

    @OneToMany(
        mappedBy = "primaryKey.driver",
        cascade = {CascadeType.PERSIST, CascadeType.MERGE},
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private Set<VehicleDriverEntity> vehicleDrivers;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "active_vehicle_id", unique = true)
    private VehicleEntity activeVehicle;

}

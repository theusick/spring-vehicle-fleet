package com.theusick.repository.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Set;

@Entity
@Table(name = "vehicles")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int year;
    private int mileage;
    private String color;
    private double price;
    private String licensePlate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "enterprise_id", nullable = false)
    private EnterpriseEntity enterprise;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private VehicleBrandEntity brand;

    @OneToMany(mappedBy = "primaryKey.vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<VehicleDriverEntity> vehicleDrivers;

    @OneToOne(mappedBy = "activeVehicle")
    private DriverEntity currentDriver;

    @PreRemove
    public void onRemoveSetActiveVehicleNull() {
        if (currentDriver != null) {
            currentDriver.setActiveVehicle(null);
        }
    }

}

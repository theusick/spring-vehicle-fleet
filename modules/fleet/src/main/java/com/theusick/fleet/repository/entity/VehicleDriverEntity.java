package com.theusick.fleet.repository.entity;

import jakarta.persistence.AssociationOverride;
import jakarta.persistence.AssociationOverrides;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "vehicle_drivers")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@AssociationOverrides({
    @AssociationOverride(
        name = "primaryKey.vehicle",
        joinColumns = @JoinColumn(name = "vehicle_id", nullable = false)),
    @AssociationOverride(
        name = "primaryKey.driver",
        joinColumns = @JoinColumn(name = "driver_id", nullable = false)
    )
})
public class VehicleDriverEntity {

    @EmbeddedId
    @Builder.Default
    private VehicleDriverId primaryKey = new VehicleDriverId();

    @Getter
    @Setter
    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VehicleDriverId implements Serializable {

        @ManyToOne(fetch = FetchType.LAZY)
        private VehicleEntity vehicle;
        @ManyToOne(fetch = FetchType.LAZY)
        private DriverEntity driver;

    }

    @PreRemove
    public void onRemoveSetActiveVehicleNull() {
        DriverEntity driver = primaryKey.getDriver();
        VehicleEntity vehicle = primaryKey.getVehicle();

        if (vehicle.equals(driver.getActiveVehicle())) {
            driver.setActiveVehicle(null);
        }
    }

}

package com.theusick.repository.entity;

import jakarta.persistence.AssociationOverride;
import jakarta.persistence.AssociationOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

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

    @ColumnDefault("false")
    @Column(columnDefinition = "BIT", nullable = false)
    @Convert(converter = org.hibernate.type.NumericBooleanConverter.class)
    private boolean active;

    @Getter
    @Setter
    @Embeddable
    public static class VehicleDriverId implements Serializable {

        @ManyToOne(cascade = CascadeType.ALL)
        private VehicleEntity vehicle;
        @ManyToOne(cascade = CascadeType.ALL)
        private DriverEntity driver;

    }
}

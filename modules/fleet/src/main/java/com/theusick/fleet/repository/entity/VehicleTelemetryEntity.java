package com.theusick.fleet.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import java.time.Instant;

@Entity
@Table(name = "vehicle_telemetry")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleTelemetryEntity {

    // https://habr.com/ru/companies/domclick/articles/558876/
    public static final int SRID_WGS84 = 4326;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(columnDefinition = "TIMESTAMP WITH TIMEZONE")
    private Instant timestamp;

    @NotNull
    @Column(columnDefinition = "geometry(Point, " + SRID_WGS84 + ")")
    private Point coordinates;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private VehicleEntity vehicle;

}

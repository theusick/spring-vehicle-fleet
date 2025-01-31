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
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull
    @NotBlank
    private String color;

    private double price;

    @Size(max = 30)
    @NotNull
    @NotBlank
    private String licensePlate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enterprise_id", nullable = false)
    private EnterpriseEntity enterprise;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private VehicleBrandEntity brand;

    @OneToMany(
        mappedBy = "primaryKey.vehicle",
        cascade = {CascadeType.PERSIST, CascadeType.MERGE},
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private Set<VehicleDriverEntity> vehicleDrivers;

}

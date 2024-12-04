package com.theusick.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
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
import org.hibernate.annotations.ColumnDefault;

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

    private String name;
    private int age;
    private double salary;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "enterprise_id", nullable = false)
    private EnterpriseEntity enterprise;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private VehicleEntity vehicle;

    @ColumnDefault("false")
    @Column(columnDefinition = "BIT")
    @Convert(converter = org.hibernate.type.NumericBooleanConverter.class)
    private boolean active;

}

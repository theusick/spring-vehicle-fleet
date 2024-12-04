package com.theusick.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "enterprises")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnterpriseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String city;

    @OneToMany(mappedBy = "enterprise", fetch = FetchType.LAZY)
    private List<VehicleEntity> vehicles;

    @OneToMany(mappedBy = "enterprise", fetch = FetchType.LAZY)
    private List<DriverEntity> drivers;

}

package com.theusick.repository.entity;

import com.theusick.security.repository.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

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

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String city;

    @OneToMany(mappedBy = "enterprise", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<VehicleEntity> vehicles;

    @OneToMany(mappedBy = "enterprise", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DriverEntity> drivers;

    @ManyToMany
    @JoinTable(
        name = "manager_enterprises",
        joinColumns = @JoinColumn(name = "enterprise_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "manager_id", referencedColumnName = "id")
    )
    private Set<User> managers;

}

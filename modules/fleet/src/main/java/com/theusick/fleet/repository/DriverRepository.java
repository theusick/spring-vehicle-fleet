package com.theusick.fleet.repository;

import com.theusick.fleet.repository.entity.DriverEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DriverRepository extends JpaRepository<DriverEntity, Long> {

    List<DriverEntity> findByEnterpriseId(Long enterpriseId);

    @EntityGraph(
        type = EntityGraph.EntityGraphType.LOAD,
        attributePaths = {"vehicleDrivers"}
    )
    Page<DriverEntity> findByEnterpriseId(Long enterpriseId, Pageable pageable);

    @EntityGraph(
        type = EntityGraph.EntityGraphType.LOAD,
        attributePaths = {"vehicleDrivers"}
    )
    Page<DriverEntity> findByActiveVehicleIsNotNullAndEnterpriseId(Long enterpriseId,
                                                                   Pageable pageable);
}

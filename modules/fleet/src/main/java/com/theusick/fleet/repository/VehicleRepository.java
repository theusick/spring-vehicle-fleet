package com.theusick.fleet.repository;

import com.theusick.fleet.repository.entity.VehicleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {

    List<VehicleEntity> findByEnterpriseId(Long enterpriseId);

    @EntityGraph(
        type = EntityGraph.EntityGraphType.LOAD,
        attributePaths = {"vehicleDrivers"}
    )
    Page<VehicleEntity> findByEnterpriseId(Long enterpriseId, Pageable pageable);

    Optional<VehicleEntity> findByIdAndEnterpriseId(Long id, Long enterpriseId);

}

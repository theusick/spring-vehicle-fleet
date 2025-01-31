package com.theusick.fleet.repository;

import com.theusick.fleet.repository.entity.EnterpriseEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnterpriseRepository extends JpaRepository<EnterpriseEntity, Long> {

    Optional<EnterpriseEntity> findByIdAndManagersId(Long enterpriseId, Long managerId);

    List<EnterpriseEntity> findAllByManagersId(Long managerId);

    @EntityGraph(
        type = EntityGraph.EntityGraphType.LOAD,
        attributePaths = {"vehicles.vehicleDrivers"}
    )
    List<EnterpriseEntity> findAllEnterprisesByManagersId(Long managerId);

}

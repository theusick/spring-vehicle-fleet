package com.theusick.fleet.repository;

import com.theusick.fleet.repository.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {

    List<VehicleEntity> findByEnterpriseId(Long enterpriseId);

    Optional<VehicleEntity> findByIdAndEnterpriseId(Long id, Long enterpriseId);

}

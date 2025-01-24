package com.theusick.fleet.repository;

import com.theusick.fleet.repository.entity.VehicleDriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleDriverRepository extends JpaRepository<VehicleDriverEntity,
    VehicleDriverEntity.VehicleDriverId> {
}

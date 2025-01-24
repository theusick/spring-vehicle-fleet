package com.theusick.fleet.repository;

import com.theusick.fleet.repository.entity.VehicleBrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleBrandRepository extends JpaRepository<VehicleBrandEntity, Long> {
}

package com.theusick.repository;

import com.theusick.repository.entity.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<DriverEntity, Long> {

    boolean existsByVehicleIdAndActive(Long vehicleId, boolean active);

}

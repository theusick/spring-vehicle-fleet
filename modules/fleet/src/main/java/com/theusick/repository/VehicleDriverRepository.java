package com.theusick.repository;

import com.theusick.repository.entity.VehicleDriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleDriverRepository extends JpaRepository<VehicleDriverEntity,
    VehicleDriverEntity.VehicleDriverId> {

    boolean existsByPrimaryKeyVehicleIdAndActive(Long vehicleId, boolean active);

}

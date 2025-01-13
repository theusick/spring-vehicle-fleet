package com.theusick.repository;

import com.theusick.repository.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {

    List<VehicleEntity> findByEnterpriseId(Long enterpriseId);

    Optional<VehicleEntity> findByIdAndEnterpriseId(Long id, Long enterpriseId);

}

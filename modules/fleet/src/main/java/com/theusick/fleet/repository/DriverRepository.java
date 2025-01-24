package com.theusick.fleet.repository;

import com.theusick.fleet.repository.entity.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DriverRepository extends JpaRepository<DriverEntity, Long> {

    List<DriverEntity> findByEnterpriseId(Long enterpriseId);

}

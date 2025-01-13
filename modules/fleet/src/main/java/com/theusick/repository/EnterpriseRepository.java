package com.theusick.repository;

import com.theusick.repository.entity.EnterpriseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnterpriseRepository extends JpaRepository<EnterpriseEntity, Long> {

    Optional<EnterpriseEntity> findByIdAndManagersId(Long enterpriseId, Long managerId);

    List<EnterpriseEntity> findAllByManagersId(Long managerId);

}

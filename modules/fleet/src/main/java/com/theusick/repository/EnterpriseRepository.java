package com.theusick.repository;

import com.theusick.repository.entity.EnterpriseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnterpriseRepository extends JpaRepository<EnterpriseEntity, Long> {

    List<EnterpriseEntity> findByManagersId(Long managerId);

}

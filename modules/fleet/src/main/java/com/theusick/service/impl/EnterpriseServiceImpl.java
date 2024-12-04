package com.theusick.service.impl;

import com.theusick.repository.EnterpriseRepository;
import com.theusick.repository.entity.EnterpriseEntity;
import com.theusick.service.EnterpriseService;
import com.theusick.service.exception.NoSuchEnterpriseException;
import com.theusick.service.mapper.EnterpriseMapper;
import com.theusick.service.model.EnterpriseModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EnterpriseServiceImpl implements EnterpriseService {

    private final EnterpriseRepository enterpriseRepository;
    private final EnterpriseMapper enterpriseMapper;

    @Override
    public EnterpriseModel getEnterprise(Long enterpriseId) throws NoSuchEnterpriseException {
        return enterpriseMapper.enterpriseModelFromEntity(enterpriseRepository.findById(enterpriseId)
            .orElseThrow(() -> new NoSuchEnterpriseException(enterpriseId)));
    }

    @Override
    public List<EnterpriseModel> getEnterprises() {
        return enterpriseRepository.findAll().stream()
            .map(enterpriseMapper::enterpriseModelFromEntity)
            .toList();
    }

    @Override
    public EnterpriseModel createEnterprise(EnterpriseModel enterpriseModel) {
        EnterpriseEntity enterpriseEntity = new EnterpriseEntity();
        enterpriseMapper.updateEnterpriseEntityFromModel(enterpriseEntity, enterpriseModel);
        enterpriseRepository.save(enterpriseEntity);
        return enterpriseMapper.enterpriseModelFromEntity(enterpriseEntity);
    }

    @Override
    public void updateEnterprise(EnterpriseModel enterpriseModel) throws NoSuchEnterpriseException {
        EnterpriseEntity enterpriseEntity = enterpriseRepository.findById(enterpriseModel.getId())
            .orElseThrow(() -> new NoSuchEnterpriseException(enterpriseModel.getId()));
        enterpriseMapper.updateEnterpriseEntityFromModel(enterpriseEntity, enterpriseModel);
        enterpriseRepository.save(enterpriseEntity);
    }

    @Override
    public void deleteEnterprise(Long enterpriseId) throws NoSuchEnterpriseException {
        EnterpriseEntity enterpriseEntity = enterpriseRepository.findById(enterpriseId)
            .orElseThrow(() -> new NoSuchEnterpriseException(enterpriseId));
        enterpriseRepository.delete(enterpriseEntity);
    }

}
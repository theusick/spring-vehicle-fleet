package com.theusick.service.impl;

import com.theusick.repository.EnterpriseRepository;
import com.theusick.repository.entity.EnterpriseEntity;
import com.theusick.security.repository.UserRepository;
import com.theusick.security.repository.entity.User;
import com.theusick.service.EnterpriseService;
import com.theusick.service.exception.NoAccessException;
import com.theusick.service.exception.NoSuchEnterpriseException;
import com.theusick.service.exception.NoSuchUserException;
import com.theusick.service.mapper.EnterpriseMapper;
import com.theusick.service.model.EnterpriseModel;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class EnterpriseServiceImpl implements EnterpriseService {

    private final EnterpriseRepository enterpriseRepository;
    private final EnterpriseMapper enterpriseMapper;
    private final UserRepository userRepository;

    @Override
    public EnterpriseModel getEnterprise(Long enterpriseId) throws NoSuchEnterpriseException {
        return enterpriseMapper.enterpriseModelFromEntity(enterpriseRepository.findById(enterpriseId)
            .orElseThrow(() -> new NoSuchEnterpriseException(enterpriseId)));
    }

    @Override
    public EnterpriseModel getEnterpriseForManager(Long enterpriseId, Long managerId) throws NoAccessException {
        return enterpriseMapper.enterpriseModelFromEntity(
            enterpriseRepository.findByIdAndManagersId(enterpriseId, managerId)
                .orElseThrow(() -> new NoAccessException(enterpriseId)));
    }

    @Override
    public List<Long> getVisibleEnterpriseIdsForManager(Long managerId) {
        return enterpriseRepository.findAllByManagersId(managerId).stream()
            .map(EnterpriseEntity::getId)
            .toList();
    }

    @Override
    @Transactional
    public <T, R> List<R> getVisibleEntitiesForManager(Long managerId,
                                                       Long enterpriseId,
                                                       Function<Long, List<T>> entitiesFetcher,
                                                       Function<T, R> mapper) throws NoAccessException {
        List<Long> visibleEnterpriseIds = getVisibleEnterpriseIdsForManager(managerId);

        if (!visibleEnterpriseIds.contains(enterpriseId)) {
            throw new NoAccessException(enterpriseId);
        }
        return entitiesFetcher.apply(enterpriseId).stream()
            .map(mapper)
            .toList();
    }

    @Override
    public List<EnterpriseModel> getEnterprises() {
        return enterpriseRepository.findAll().stream()
            .map(enterpriseMapper::enterpriseModelFromEntity)
            .toList();
    }

    @Override
    @Transactional
    public List<EnterpriseModel> getEnterprisesForManager(Long managerId) {
        return enterpriseRepository.findAllByManagersId(managerId).stream()
            .map(enterpriseMapper::enterpriseModelFromEntity)
            .toList();
    }

    @Override
    @Transactional
    public EnterpriseModel createEnterprise(EnterpriseModel enterpriseModel,
                                            Long managerId) throws NoSuchUserException {
        EnterpriseEntity enterpriseEntity = new EnterpriseEntity();
        enterpriseMapper.updateEnterpriseEntityFromModel(enterpriseEntity, enterpriseModel);

        User manager = userRepository.findById(managerId)
            .orElseThrow(() -> new NoSuchUserException(managerId));

        if (enterpriseEntity.getManagers() == null) {
            enterpriseEntity.setManagers(new HashSet<>());
        }
        enterpriseEntity.getManagers().add(manager);

        enterpriseRepository.save(enterpriseEntity);

        return enterpriseMapper.enterpriseModelFromEntity(enterpriseEntity);
    }

    @Override
    public EnterpriseModel updateEnterprise(EnterpriseModel enterpriseModel,
                                            Long managerId) throws NoAccessException {
        EnterpriseEntity enterpriseEntity =
            enterpriseRepository.findByIdAndManagersId(enterpriseModel.getId(), managerId)
                .orElseThrow(() -> new NoAccessException(enterpriseModel.getId()));
        enterpriseMapper.updateEnterpriseEntityFromModel(enterpriseEntity, enterpriseModel);
        enterpriseRepository.save(enterpriseEntity);
        return enterpriseMapper.enterpriseModelFromEntity(enterpriseEntity);
    }

    @Override
    public void deleteEnterprise(Long enterpriseId, Long managerId) throws NoAccessException {
        EnterpriseEntity enterpriseEntity =
            enterpriseRepository.findByIdAndManagersId(enterpriseId, managerId)
                .orElseThrow(() -> new NoAccessException(enterpriseId));
        enterpriseRepository.delete(enterpriseEntity);
    }

}

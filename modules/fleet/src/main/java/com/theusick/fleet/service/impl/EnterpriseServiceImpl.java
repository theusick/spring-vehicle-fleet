package com.theusick.fleet.service.impl;

import com.theusick.core.security.repository.UserRepository;
import com.theusick.core.security.repository.entity.User;
import com.theusick.core.service.exception.NoAccessException;
import com.theusick.fleet.repository.EnterpriseRepository;
import com.theusick.fleet.repository.entity.EnterpriseEntity;
import com.theusick.fleet.service.EnterpriseService;
import com.theusick.fleet.service.exception.NoSuchEnterpriseException;
import com.theusick.fleet.service.exception.NoSuchUserException;
import com.theusick.fleet.service.mapper.EnterpriseMapper;
import com.theusick.fleet.service.model.EnterpriseModel;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class EnterpriseServiceImpl implements EnterpriseService {

    private final EnterpriseRepository enterpriseRepository;
    private final EnterpriseMapper enterpriseMapper;

    private final UserRepository userRepository;

    @Override
    public EnterpriseModel getEnterprise(Long enterpriseId) throws NoSuchEnterpriseException {
        return enterpriseMapper.enterpriseModelFromEntity(
            enterpriseRepository.findById(enterpriseId)
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
    public List<EnterpriseModel> createEnterprises(List<EnterpriseModel> enterprises) {
        List<EnterpriseEntity> enterpriseEntities = enterprises.stream()
            .map(enterpriseModel -> {
                EnterpriseEntity enterpriseEntity = new EnterpriseEntity();
                enterpriseMapper.updateEnterpriseEntityFromModel(enterpriseEntity, enterpriseModel);
                return enterpriseEntity;
            })
            .toList();

        enterpriseRepository.saveAll(enterpriseEntities);
        return enterpriseEntities.stream()
            .map(enterpriseMapper::enterpriseModelFromEntity)
            .toList();
    }

    @Override
    @Transactional
    public <T, R> List<R> getVisibleEntitiesForManager(Long managerId,
                                                       Long enterpriseId,
                                                       Function<Long, List<T>> entitiesFetcher,
                                                       Function<T, R> mapper) throws NoAccessException {
        verifyManagerAccess(enterpriseId, managerId);
        return entitiesFetcher.apply(enterpriseId).stream()
            .map(mapper)
            .toList();
    }

    @Override
    @Transactional
    public <T, R> Page<R> getVisiblePageEntitiesForManager(Long managerId,
                                                           Long enterpriseId,
                                                           BiFunction<Long, Pageable, Page<T>> entitiesFetcher,
                                                           Function<Page<T>, Page<R>> mapper,
                                                           Pageable pageable) throws NoAccessException {
        verifyManagerAccess(enterpriseId, managerId);
        return mapper.apply(entitiesFetcher.apply(enterpriseId, pageable));
    }


    @Override
    public EnterpriseModel getEnterpriseForManager(Long managerId, Long enterpriseId) throws NoAccessException {
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
    public List<EnterpriseModel> getEnterprisesForManager(Long managerId) {
        return enterpriseRepository.findAllEnterprisesByManagersId(managerId).stream()
            .map(enterpriseMapper::enterpriseModelFromEntity)
            .toList();
    }

    @Override
    @Transactional
    public EnterpriseModel createEnterpriseForManager(EnterpriseModel enterpriseModel,
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
    public EnterpriseModel updateEnterpriseForManager(EnterpriseModel enterpriseModel,
                                                      Long managerId) throws NoAccessException {
        EnterpriseEntity enterpriseEntity =
            enterpriseRepository.findByIdAndManagersId(enterpriseModel.getId(), managerId)
                .orElseThrow(() -> new NoAccessException(enterpriseModel.getId()));

        enterpriseMapper.updateEnterpriseEntityFromModel(enterpriseEntity, enterpriseModel);
        enterpriseRepository.save(enterpriseEntity);
        return enterpriseMapper.enterpriseModelFromEntity(enterpriseEntity);
    }

    @Override
    public void deleteEnterpriseForManager(Long enterpriseId,
                                           Long managerId) throws NoAccessException {
        enterpriseRepository.delete(
            enterpriseRepository.findByIdAndManagersId(enterpriseId, managerId)
                .orElseThrow(() -> new NoAccessException(enterpriseId)));
    }

    @Override
    public EnterpriseEntity verifyManagerAccess(Long enterpriseId,
                                                Long managerId) throws NoAccessException {
        return enterpriseRepository.findByIdAndManagersId(enterpriseId, managerId)
            .orElseThrow(() -> new NoAccessException(enterpriseId));
    }

}

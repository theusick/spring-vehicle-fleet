package com.theusick.service;

import com.theusick.service.exception.NoSuchEnterpriseException;
import com.theusick.service.exception.NoSuchException;
import com.theusick.service.model.EnterpriseModel;

import java.util.List;
import java.util.function.Function;

public interface EnterpriseService {

    EnterpriseModel getEnterprise(Long enterpriseId) throws NoSuchEnterpriseException;

    <T, R> List<R> getVisibleEntitiesForManager(Long managerId, Long enterpriseId,
                                                Function<Long, List<Long>> visibleEnterpriseIdsFetcher,
                                                Function<Long, List<T>> entitiesFetcher,
                                                Function<T, R> mapper) throws NoSuchException;

    List<EnterpriseModel> getEnterprises();

    List<EnterpriseModel> getEnterprisesForManager(Long managerId);

    EnterpriseModel createEnterprise(EnterpriseModel enterpriseModel);

    void updateEnterprise(EnterpriseModel enterpriseModel) throws NoSuchEnterpriseException;

    void deleteEnterprise(Long enterpriseId) throws NoSuchEnterpriseException;

}

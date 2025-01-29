package com.theusick.fleet.service;

import com.theusick.core.service.exception.NoAccessException;
import com.theusick.fleet.service.exception.NoSuchEnterpriseException;
import com.theusick.fleet.service.exception.NoSuchUserException;
import com.theusick.fleet.service.model.EnterpriseModel;

import java.util.List;
import java.util.function.Function;

public interface EnterpriseService {

    EnterpriseModel getEnterprise(Long enterpriseId) throws NoSuchEnterpriseException;

    List<EnterpriseModel> getEnterprises();

    EnterpriseModel createEnterprise(EnterpriseModel enterpriseModel);

    List<EnterpriseModel> createEnterprises(List<EnterpriseModel> enterprises);

    <T, R> List<R> getVisibleEntitiesForManager(Long managerId,
                                                Long enterpriseId,
                                                Function<Long, List<T>> entitiesFetcher,
                                                Function<T, R> mapper) throws NoAccessException;

    EnterpriseModel getEnterpriseForManager(Long enterpriseId, Long managerId) throws NoAccessException;

    List<Long> getVisibleEnterpriseIdsForManager(Long managerId);

    List<EnterpriseModel> getEnterprisesForManager(Long managerId);

    EnterpriseModel createEnterpriseForManager(EnterpriseModel enterpriseModel,
                                               Long managerId) throws NoSuchUserException;

    EnterpriseModel updateEnterpriseForManager(EnterpriseModel enterpriseModel,
                                               Long managerId) throws NoAccessException;

    void deleteEnterpriseForManager(Long enterpriseId, Long managerId) throws NoAccessException;

}

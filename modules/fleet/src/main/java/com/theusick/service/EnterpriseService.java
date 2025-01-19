package com.theusick.service;

import com.theusick.service.exception.NoAccessException;
import com.theusick.service.exception.NoSuchEnterpriseException;
import com.theusick.service.exception.NoSuchUserException;
import com.theusick.service.model.EnterpriseModel;

import java.util.List;
import java.util.function.Function;

public interface EnterpriseService {

    EnterpriseModel getEnterprise(Long enterpriseId) throws NoSuchEnterpriseException;

    EnterpriseModel getEnterpriseForManager(Long enterpriseId, Long managerId) throws NoAccessException;

    List<Long> getVisibleEnterpriseIdsForManager(Long managerId);

    <T, R> List<R> getVisibleEntitiesForManager(Long managerId,
                                                Long enterpriseId,
                                                Function<Long, List<T>> entitiesFetcher,
                                                Function<T, R> mapper) throws NoAccessException;

    List<EnterpriseModel> getEnterprises();

    List<EnterpriseModel> getEnterprisesForManager(Long managerId);

    EnterpriseModel createEnterprise(EnterpriseModel enterpriseModel,
                                     Long managerId) throws NoSuchUserException;

    EnterpriseModel updateEnterprise(EnterpriseModel enterpriseModel,
                                     Long managerId) throws NoAccessException;

    void deleteEnterprise(Long enterpriseId, Long managerId) throws NoAccessException;

}

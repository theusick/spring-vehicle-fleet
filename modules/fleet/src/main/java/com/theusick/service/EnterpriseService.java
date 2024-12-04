package com.theusick.service;

import com.theusick.service.exception.NoSuchEnterpriseException;
import com.theusick.service.model.EnterpriseModel;

import java.util.List;

public interface EnterpriseService {

    EnterpriseModel getEnterprise(Long enterpriseId) throws NoSuchEnterpriseException;

    List<EnterpriseModel> getEnterprises();

    EnterpriseModel createEnterprise(EnterpriseModel enterpriseModel);

    void updateEnterprise(EnterpriseModel enterpriseModel) throws NoSuchEnterpriseException;

    void deleteEnterprise(Long enterpriseId) throws NoSuchEnterpriseException;

}

package com.theusick.fleet.service.exception;

import com.theusick.core.service.exception.NoSuchException;

public class NoSuchEnterpriseException extends NoSuchException {

    public NoSuchEnterpriseException(Long enterpriseId) {
        super(enterpriseId);
    }

}

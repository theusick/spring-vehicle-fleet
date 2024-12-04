package com.theusick.service.exception;

public class NoSuchEnterpriseException extends NoSuchException {

    public NoSuchEnterpriseException(Long enterpriseId) {
        super(enterpriseId);
    }

}

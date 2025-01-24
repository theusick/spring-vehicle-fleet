package com.theusick.fleet.service.exception;

import com.theusick.core.service.exception.NoSuchException;

public class NoSuchDriverException extends NoSuchException {

    public NoSuchDriverException(Long driverId) {
        super(driverId);
    }

}

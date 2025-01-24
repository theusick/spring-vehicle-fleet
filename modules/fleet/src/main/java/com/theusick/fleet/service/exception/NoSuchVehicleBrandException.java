package com.theusick.fleet.service.exception;

import com.theusick.core.service.exception.NoSuchException;

public class NoSuchVehicleBrandException extends NoSuchException {

    public NoSuchVehicleBrandException(Long vehicleBrandId) {
        super(vehicleBrandId);
    }

}

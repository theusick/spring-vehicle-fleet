package com.theusick.fleet.service.exception;

import com.theusick.core.service.exception.NoSuchException;

public class NoSuchVehicleException extends NoSuchException {

    public NoSuchVehicleException(Long vehicleId) {
        super(vehicleId);
    }

}

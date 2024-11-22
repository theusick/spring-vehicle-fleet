package com.theusick.service.exception;

public class NoSuchVehicleException extends NoSuchException {

    public NoSuchVehicleException(Long vehicleId) {
        super(vehicleId);
    }

}

package com.theusick.service.exception;

public class NoSuchVehicleBrandException extends NoSuchException {

    public NoSuchVehicleBrandException(Long vehicleBrandId) {
        super(vehicleBrandId);
    }

}

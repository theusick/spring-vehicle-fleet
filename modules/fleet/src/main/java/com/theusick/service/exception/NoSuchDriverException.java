package com.theusick.service.exception;

public class NoSuchDriverException extends NoSuchException {

    public NoSuchDriverException(Long driverId) {
        super(driverId);
    }

}

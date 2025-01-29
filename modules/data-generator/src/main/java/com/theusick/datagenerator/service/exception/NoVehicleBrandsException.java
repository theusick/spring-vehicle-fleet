package com.theusick.datagenerator.service.exception;

public class NoVehicleBrandsException extends RuntimeException {

    private static final String NO_BRAND_MESSAGE = "There are no fields with brands";

    public NoVehicleBrandsException() {
        super(NO_BRAND_MESSAGE);
    }

}

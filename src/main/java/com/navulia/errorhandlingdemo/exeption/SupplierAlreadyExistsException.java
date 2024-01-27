package com.navulia.errorhandlingdemo.exeption;

public class SupplierAlreadyExistsException extends RuntimeException{

    public SupplierAlreadyExistsException() {
    }

    public SupplierAlreadyExistsException(String message) {
        super(message);
    }
}

package com.navulia.errorhandlingdemo.dto;

import lombok.Getter;

@Getter
public class SupplierOutputDTO extends SupplierInputDTO {
    private final long id;

    public SupplierOutputDTO(final long id, final String name, final String address, final int registrationNo){
        super(name, address, registrationNo);
        this.id = id;
    }

}

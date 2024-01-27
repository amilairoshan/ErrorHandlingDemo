package com.navulia.errorhandlingdemo.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SupplierInputDTO {
    private final String name;
    private final String address;
    private final int registrationNo;
}

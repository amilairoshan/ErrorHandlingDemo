package com.navulia.errorhandlingdemo.service;

import com.navulia.errorhandlingdemo.dto.SupplierInputDTO;
import com.navulia.errorhandlingdemo.dto.SupplierOutputDTO;

import java.util.List;

public interface SupplierService {

    public List<SupplierOutputDTO> getAllSuppliers();
    public SupplierOutputDTO getSupplierById(Long id);
    public SupplierOutputDTO createSupplier(SupplierInputDTO supplierDTO);
    public SupplierOutputDTO updateSupplier(long id, SupplierInputDTO supplierDTO);



}

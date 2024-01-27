package com.navulia.errorhandlingdemo.controller;

import com.navulia.errorhandlingdemo.dto.SupplierInputDTO;
import com.navulia.errorhandlingdemo.dto.SupplierOutputDTO;
import com.navulia.errorhandlingdemo.service.SupplierServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import java.util.List;

@RestController
@RequestMapping(value="/api/suppliers")
@RequiredArgsConstructor
@Validated
public class SupplierController {

    private final SupplierServiceImpl supplierService;
    @GetMapping
    public ResponseEntity<List<SupplierOutputDTO>> getAllSuppliers() {
        return new ResponseEntity<>(supplierService.getAllSuppliers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierOutputDTO> getSupplierById(@PathVariable Long id) {
        return ResponseEntity.ok(supplierService.getSupplierById(id));
    }

    @PostMapping
    public ResponseEntity<SupplierOutputDTO> createSupplier(@Valid @RequestBody  SupplierInputDTO supplierDto) {
        return new ResponseEntity<>(supplierService.createSupplier(supplierDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierOutputDTO>  updateSupplier(@PathVariable Long id,@RequestBody SupplierInputDTO supplierDTO){
        return new ResponseEntity<>(supplierService.updateSupplier(id,supplierDTO),HttpStatus.OK);
    }



}
package com.onlineshop.controller;

import com.onlineshop.controller.dto.SupplierDTO;
import com.onlineshop.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/supplier")
public class AdminSupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping("/all")
    public List<SupplierDTO> findAllSupplier() {
        return supplierService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierDTO> findByIdSupplier(@PathVariable Integer id) {
        SupplierDTO supplier = supplierService.findById(id);
        if (supplier == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(supplier);
    }

    @PostMapping("/add")
    public ResponseEntity<SupplierDTO> addSupplier(@RequestBody SupplierDTO supplierDTO) {
        SupplierDTO supplier = supplierService.add(supplierDTO);
        if (supplier == null) {
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(supplier);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SupplierDTO> updateSupplier(@PathVariable Integer id, @RequestBody SupplierDTO supplierDTO) {
        SupplierDTO supplier = supplierService.update(id, supplierDTO);
        if (supplier == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(supplier);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<SupplierDTO> deleteSupplier(@PathVariable Integer id) {
        SupplierDTO supplier = supplierService.delete(id);
        if (supplier == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(supplier);
    }

}

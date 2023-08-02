package com.onlineshop.service;

import com.onlineshop.controller.dto.SupplierDTO;
import com.onlineshop.domain.Country;
import com.onlineshop.domain.Supplier;
import com.onlineshop.repository.SupplierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private CountryService countryService;

    static final Logger log = LoggerFactory.getLogger(Supplier.class);

    //  private final ModelMapper modelMapper;

    public List<SupplierDTO> findAll() {
        List<Supplier> suppliers = supplierRepository.findAll();
        List<SupplierDTO> result = new ArrayList<>();
        suppliers.forEach(supplier -> result.add(SupplierDTO.getInstance(supplier)));
        return result;
    }

    public SupplierDTO findById(Integer id) {
        Supplier supplier = supplierRepository.findById(id).orElse(null);
        if (supplier != null) {
            return SupplierDTO.getInstance(supplier); // без маппера
            // return modelMapper.map(Supplier,SupplierDTO.class); // с маппером
        }
        log.error("Supplier not found supplierId: {}", id);
        return null;
    }

    public SupplierDTO add(SupplierDTO supplierDTO) {
        Supplier newSupplier = new Supplier();
        newSupplier.setSupplierName(supplierDTO.getSupplierName());
        newSupplier.setAddress(supplierDTO.getAddress());
        Country country = countryService.findOrCreateCountryByName(supplierDTO.getCountry().getCountryName());
        newSupplier.setCountry(country);
        newSupplier = supplierRepository.save(newSupplier);
        return SupplierDTO.getInstance(newSupplier);
    }

    public SupplierDTO update(Integer id, SupplierDTO supplierDTO) {
        Supplier updSupplier = supplierRepository.findById(id).orElse(null);
        if (updSupplier != null) {
            updSupplier.setSupplierName(supplierDTO.getSupplierName());
            updSupplier.setAddress(supplierDTO.getAddress());
            Country country = countryService.findOrCreateCountryByName(supplierDTO.getCountry().getCountryName());
            updSupplier.setCountry(country);
            supplierRepository.save(updSupplier);
            log.info("Supplier updated: {}", id);
            return SupplierDTO.getInstance(updSupplier);
        }
        log.error("Supplier not found, supplierId={}", id);
        return null;
    }

    public SupplierDTO delete(Integer id) {
        Supplier supplier = supplierRepository.findById(id).orElse(null);
        if (supplier != null) {
            supplierRepository.delete(supplier);
        }
        log.info("Supplier deleted: {}", id);
        return SupplierDTO.getInstance(supplier);
    }
/*
    public SupplierDTO findOrCreateSupplierByName(String name){
        Supplier supplier = supplierRepository.findByName(name);
        if (supplier == null) {
            supplier = new Supplier();
            supplier.getSupplierName(name);
            supplier.setAddress("");
            Country country = countryService.findOrCreateCountryByName(supplierDTO.getCountry().getCountryName());
            supplier.setCountry(country);
            supplierRepository.save(supplier);
            log.info("Country added: {}", supplier);
        }
        return supplier;
    }

 */
}

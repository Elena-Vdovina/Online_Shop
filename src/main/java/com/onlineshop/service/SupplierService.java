package com.onlineshop.service;

import com.onlineshop.controller.dto.SupplierDTO;
import com.onlineshop.domain.Country;
import com.onlineshop.domain.Supplier;
import com.onlineshop.repository.CountryRepository;
import com.onlineshop.repository.SupplierRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private CountryRepository countryRepository;

    public List<SupplierDTO> findAll() {
        List<Supplier> suppliers = supplierRepository.findAll();
        List<SupplierDTO> result = new ArrayList<>();
        suppliers.forEach(supplier -> result.add(SupplierDTO.getInstance(supplier)));
        log.info("Find list og suppliers");
        return result;
    }

    public SupplierDTO findById(Integer id) {
        Optional<Supplier> supplier = supplierRepository.findById(id);
        if (supplier.isPresent()) {
            log.info("Find Supplier supplierId: {}", id);
            return SupplierDTO.getInstance(supplier.get());
        }
        log.error("Not found Supplier supplierId: {}", id);
        return null;
    }

    public SupplierDTO add(SupplierDTO supplierDTO) {
        Supplier newSupplier = new Supplier();
        newSupplier.setSupplierName(supplierDTO.getSupplierName());
        newSupplier.setAddress(supplierDTO.getAddress());
        Integer countryId = supplierDTO.getCountry().getCountryId();
        Optional<Country> country = countryRepository.findById(countryId);
        if (country.isEmpty()) {
            log.error("Not found for add Supplier Country countryId: {}", countryId);
            return null;
        }
        newSupplier.setCountry(country.get());
        newSupplier = supplierRepository.save(newSupplier);
        log.info("Supplier added successfully supplierId: {}", newSupplier.getSupplierId());
        return SupplierDTO.getInstance(newSupplier);
    }

    public SupplierDTO update(Integer id, SupplierDTO supplierDTO) {
        Optional<Supplier> supplier = supplierRepository.findById(id);
        if (supplier.isPresent()) {
            Supplier updSupplier = supplier.get();
            updSupplier.setSupplierName(supplierDTO.getSupplierName());
            updSupplier.setAddress(supplierDTO.getAddress());
            Integer countryId = supplierDTO.getCountry().getCountryId();
            Optional<Country> country = countryRepository.findById(countryId);
            if (country.isEmpty()) {
                log.error("Not found for update Supplier Country countryId: {}", countryId);
                return null;
            }
            updSupplier.setCountry(country.get());
            supplierRepository.save(updSupplier);
            log.info("Supplier updated successfully supplierId: {}", id);
            return SupplierDTO.getInstance(updSupplier);
        }
        log.error("Not found for update Supplier supplierId: {}", id);
        return null;
    }

    public SupplierDTO delete(Integer id) {
        Optional<Supplier> supplier = supplierRepository.findById(id);
        if (supplier.isPresent()) {
            Supplier delSupplier = supplier.get();
            supplierRepository.delete(delSupplier);
            log.info("Supplier deleted successfully supplierId: {}", id);
            return SupplierDTO.getInstance(delSupplier);
        }
        log.error("Not found for delete Supplier supplierId: {}", id);
        return null;
    }

}

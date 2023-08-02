package com.onlineshop.service;

import com.onlineshop.controller.dto.SupplierDTO;
import com.onlineshop.domain.Country;
import com.onlineshop.domain.Supplier;
import com.onlineshop.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private CountryService countryService;

    public List<SupplierDTO> findAll() {
        List<Supplier> suppliers = supplierRepository.findAll();
        List<SupplierDTO> result = new ArrayList<>();
        suppliers.forEach(supplier -> result.add(SupplierDTO.getInstance(supplier)));
        return result;
    }

    public SupplierDTO findById(Integer id) {
        Optional<Supplier> supplier = supplierRepository.findById(id);
        if (supplier.isPresent()) {
            return SupplierDTO.getInstance(supplier.get());
        }
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
        Optional<Supplier> supplier = supplierRepository.findById(id);
        if (supplier.isPresent()) {
            Supplier updSupplier = supplier.get();
            updSupplier.setSupplierName(supplierDTO.getSupplierName());
            updSupplier.setAddress(supplierDTO.getAddress());
            Country country = countryService.findOrCreateCountryByName(supplierDTO.getCountry().getCountryName());
            updSupplier.setCountry(country);
            supplierRepository.save(updSupplier);
            return SupplierDTO.getInstance(updSupplier);
        }
        return null;
    }

    public SupplierDTO delete(Integer id) {
        Optional<Supplier> supplier = supplierRepository.findById(id);
        if (supplier.isPresent()) {
            Supplier delSupplier = supplier.get();
            supplierRepository.delete(delSupplier);
            return SupplierDTO.getInstance(delSupplier);
        }
        return null;
    }

}

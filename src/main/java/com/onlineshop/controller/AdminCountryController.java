package com.onlineshop.controller;

import com.onlineshop.controller.dto.CountryDTO;
import com.onlineshop.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/country")
public class AdminCountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping("/all")
    public List<CountryDTO> findAllCountry() {
        return countryService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryDTO> findByIdCountry(@PathVariable Integer id) {
        CountryDTO country = countryService.findById(id);
        if (country == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(country);
    }

    @PostMapping("/add")
    public ResponseEntity<CountryDTO> addCountry(@RequestBody CountryDTO countryDTO) {
        CountryDTO country = countryService.add(countryDTO);
        if (country == null) {
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(country);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CountryDTO> updateCountry(@PathVariable Integer id, @RequestBody CountryDTO countryDTO) {
        CountryDTO country = countryService.update(id, countryDTO);
        if (country == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(country);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CountryDTO> deleteCountry(@PathVariable Integer id) {
        CountryDTO country = countryService.delete(id);
        if (country == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(country);
    }

}

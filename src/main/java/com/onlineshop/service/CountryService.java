package com.onlineshop.service;

import com.onlineshop.controller.dto.CountryDTO;
import com.onlineshop.domain.Country;
import com.onlineshop.repository.CountryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public List<CountryDTO> findAll() {
        List<Country> countries = countryRepository.findAll();
        List<CountryDTO> result = new ArrayList<>();
        countries.forEach(country -> result.add(CountryDTO.getInstance(country)));
        log.info("Found list of Countries");
        return result;
    }

    public CountryDTO findById(Integer id) {
        Optional<Country> country = countryRepository.findById(id);
        if (country.isPresent()) {
            log.info("Found Country countryId: {}", id);
            return CountryDTO.getInstance(country.get());
        }
        log.error("Not found Country countryId: {}", id);
        return null;
    }

    public CountryDTO add(CountryDTO countryDTO) {
        Country newCountry = new Country();
        newCountry.setCountryName(countryDTO.getCountryName());
        newCountry = countryRepository.save(newCountry);
        log.info("Country added successfully countryId: {}", newCountry.getCountryId());
        return CountryDTO.getInstance(newCountry);
    }

    public CountryDTO update(Integer id, CountryDTO countryDTO) {
        Optional<Country> country = countryRepository.findById(id);
        if (country.isPresent()) {
            Country updCountry = country.get();
            updCountry.setCountryName(countryDTO.getCountryName());
            countryRepository.save(updCountry);
            log.info("Country updated successfully countryId: {}", id);
            return CountryDTO.getInstance(updCountry);
        }
        log.error("Not found for update Country countryId: {}", id);
        return null;
    }

    public CountryDTO delete(Integer id) {
        Optional<Country> country = countryRepository.findById(id);
        if (country.isPresent()) {
            Country delCountry = country.get();
            countryRepository.delete(delCountry);
            log.info("Country deleted successfully countryId: {}", id);
            return CountryDTO.getInstance(delCountry);
        }
        log.error("Not found for delete Country countryId: {}", id);
        return null;
    }

}

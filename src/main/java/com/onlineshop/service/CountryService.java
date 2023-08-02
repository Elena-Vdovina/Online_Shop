package com.onlineshop.service;

import com.onlineshop.controller.dto.CountryDTO;
import com.onlineshop.domain.Country;
import com.onlineshop.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    static final Logger log = LoggerFactory.getLogger(Country.class);

    //  private final ModelMapper modelMapper;

    public List<CountryDTO> findAll() {
        List<Country> countries = countryRepository.findAll();
        List<CountryDTO> result = new ArrayList<>();
        countries.forEach(country -> result.add(CountryDTO.getInstance(country)));
        return result;
    }

    public CountryDTO findById(Integer id) {
        Country country = countryRepository.findById(id).orElse(null);
        if (country != null) {
            return CountryDTO.getInstance(country); // без маппера
            // return modelMapper.map(country,CountryDTO.class); // с маппером
        }
        log.error("Country not found countryId: {}", id);
        return null;
    }

    public CountryDTO add(CountryDTO countryDTO) {
        Country newCountry = new Country();
        newCountry.setCountryName(countryDTO.getCountryName());
        newCountry = countryRepository.save(newCountry);
        return CountryDTO.getInstance(newCountry);
    }

    public CountryDTO update(Integer id, CountryDTO countryDTO) {
        Country updCountry = countryRepository.findById(id).orElse(null);
        if (updCountry != null) {
            updCountry.setCountryName(countryDTO.getCountryName());
            countryRepository.save(updCountry);
            log.info("Country updated: {}", id);
            return CountryDTO.getInstance(updCountry);
        }
        log.error("Country not found, countryId={}", id);
        return null;
    }

    public CountryDTO delete(Integer id) {
        Country country = countryRepository.findById(id).orElse(null);
        if (country != null) {
            countryRepository.delete(country);
        }
        log.info("Country deleted: {}", id);
        return CountryDTO.getInstance(country);
    }

    public Country findOrCreateCountryByName(String name){
        Country country = countryRepository.findByName(name);
        if (country == null) {
            country = new Country();
            country.setCountryName(name);
            countryRepository.save(country);
            log.info("Country added: {}", country);
        }
        return country;
    }
}

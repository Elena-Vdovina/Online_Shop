package com.onlineshop.service;

import com.onlineshop.controller.dto.CustomerDTO;
import com.onlineshop.domain.Country;
import com.onlineshop.domain.Customer;
import com.onlineshop.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    
    @Autowired
    private CustomerRepository customerRepository;


    @Autowired
    private CountryService countryService;

    static final Logger log = LoggerFactory.getLogger(Customer.class);

    //  private final ModelMapper modelMapper;

    public List<CustomerDTO> findAll() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> result = new ArrayList<>();
        customers.forEach(customer -> result.add(CustomerDTO.getInstance(customer)));
        return result;
    }

    public CustomerDTO findById(Integer id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer != null) {
            return CustomerDTO.getInstance(customer); // без маппера
            // return modelMapper.map(customer,customerDTO.class); // с маппером
        }
        log.error("customer not found customerId: {}", id);
        return null;
    }

    public CustomerDTO add(CustomerDTO customerDTO) {
        Customer newCustomer = new Customer();
        newCustomer.setCustomerName(customerDTO.getCustomerName());
        newCustomer.setAddress(customerDTO.getAddress());
        Country country = countryService.findOrCreateCountryByName(customerDTO.getCountry().getCountryName());
        newCustomer.setCountry(country);
        newCustomer = customerRepository.save(newCustomer);
        return customerDTO.getInstance(newCustomer);
    }

    public CustomerDTO update(Integer id, CustomerDTO customerDTO) {
        Customer updCustomer = customerRepository.findById(id).orElse(null);
        if (updCustomer != null) {
            updCustomer.setCustomerName(customerDTO.getCustomerName());
            updCustomer.setAddress(customerDTO.getAddress());
            Country country = countryService.findOrCreateCountryByName(customerDTO.getCountry().getCountryName());
            updCustomer.setCountry(country);
            customerRepository.save(updCustomer);
            log.info("customer updated: {}", id);
            return customerDTO.getInstance(updCustomer);
        }
        log.error("customer not found, customerId={}", id);
        return null;
    }

    public CustomerDTO delete(Integer id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer != null) {
            customerRepository.delete(customer);
        }
        log.info("customer deleted: {}", id);
        return CustomerDTO.getInstance(customer);
    }

}

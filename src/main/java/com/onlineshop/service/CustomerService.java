package com.onlineshop.service;

import com.onlineshop.controller.dto.CustomerDTO;
import com.onlineshop.domain.Country;
import com.onlineshop.domain.Customer;
import com.onlineshop.repository.CountryRepository;
import com.onlineshop.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CountryRepository countryRepository;

    public List<CustomerDTO> findAll() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> result = new ArrayList<>();
        customers.forEach(customer -> result.add(CustomerDTO.getInstance(customer)));
        log.info("Found list of Customers");
        return result;
    }

    public CustomerDTO findById(Integer id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            log.info("Found Customer customerId: {}", id);
            return CustomerDTO.getInstance(customer.get());
        }
        log.error("Not found Customer customerId: {}", id);
        return null;
    }

    public CustomerDTO add(CustomerDTO customerDTO) {
        Customer newCustomer = new Customer();
        newCustomer.setCustomerName(customerDTO.getCustomerName());
        newCustomer.setAddress(customerDTO.getAddress());
        Integer countryId = customerDTO.getCountry().getCountryId();
        Optional<Country> country = countryRepository.findById(countryId);
        if (country.isEmpty()){
            log.error("Not found for add Customer Country countryId: {} ", countryId);
            return null;
        }
        newCustomer.setCountry(country.get());
        newCustomer = customerRepository.save(newCustomer);
        log.info("Customer added customerId: {}", newCustomer.getCustomerId());
        return CustomerDTO.getInstance(newCustomer);
    }

    public CustomerDTO update(Integer id, CustomerDTO customerDTO) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            Customer updCustomer = customer.get();
            updCustomer.setCustomerName(customerDTO.getCustomerName());
            updCustomer.setAddress(customerDTO.getAddress());
            Integer countryId = customerDTO.getCountry().getCountryId();
            Optional<Country> country = countryRepository.findById(countryId);
            if (country.isEmpty()){
                log.error("Not found for update Customer Country countryId: {} ", countryId);
                return null;
            }
            updCustomer.setCountry(country.get());
            customerRepository.save(updCustomer);
            log.info("Customer updated customerId: {}", updCustomer.getCustomerId());
            return CustomerDTO.getInstance(updCustomer);
        }
        log.error("Not found for update Customer customerId: {}", id);
        return null;
    }

    public CustomerDTO delete(Integer id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            Customer delCustomer = customer.get();
            customerRepository.delete(delCustomer);
            log.info("Customer deleted successfully customerId: {}", id);
            return CustomerDTO.getInstance(delCustomer);
        }
        log.info("Not found for delete Customer customerId: {}", id);
        return null;
    }

}

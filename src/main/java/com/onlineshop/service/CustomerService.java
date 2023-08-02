package com.onlineshop.service;

import com.onlineshop.controller.dto.CustomerDTO;
import com.onlineshop.domain.Country;
import com.onlineshop.domain.Customer;
import com.onlineshop.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;


    @Autowired
    private CountryService countryService;

    public List<CustomerDTO> findAll() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> result = new ArrayList<>();
        customers.forEach(customer -> result.add(CustomerDTO.getInstance(customer)));
        return result;
    }

    public CustomerDTO findById(Integer id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            return CustomerDTO.getInstance(customer.get());
        }
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
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            Customer updCustomer = customer.get();
            updCustomer.setCustomerName(customerDTO.getCustomerName());
            updCustomer.setAddress(customerDTO.getAddress());
            Country country = countryService.findOrCreateCountryByName(customerDTO.getCountry().getCountryName());
            updCustomer.setCountry(country);
            customerRepository.save(updCustomer);
            return customerDTO.getInstance(updCustomer);
        }
        return null;
    }

    public CustomerDTO delete(Integer id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            Customer delCustomer = customer.get();
            customerRepository.delete(delCustomer);
            return CustomerDTO.getInstance(delCustomer);
        }
        return null;
    }

}

package com.onlineshop.controller;

import com.onlineshop.controller.dto.CustomerDTO;
import com.onlineshop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/customer")
public class AdminCustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/all")
    public List<CustomerDTO> findAllCustomer() {
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> findByIdCustomer(@PathVariable Integer id) {
        CustomerDTO customer = customerService.findById(id);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

    @PostMapping("/add")
    public ResponseEntity<CustomerDTO> addCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO customer = customerService.add(customerDTO);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Integer id, @RequestBody CustomerDTO customerDTO) {
        CustomerDTO customer = customerService.update(id, customerDTO);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomerDTO> deleteCustomer(@PathVariable Integer id) {
        CustomerDTO customer = customerService.delete(id);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

}

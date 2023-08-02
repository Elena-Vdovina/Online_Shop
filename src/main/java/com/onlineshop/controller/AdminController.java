package com.onlineshop.controller;

import com.onlineshop.controller.dto.*;
import com.onlineshop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    /*     Operation with Category     */
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category/all")
    public List<CategoryDTO> findAllCategory() {
        return categoryService.findAll();
    }

    @GetMapping("/category/{id}")
    public CategoryDTO findByIdCategory(@PathVariable Integer id) {
        return categoryService.findById(id);
    }

    @PostMapping("/category/add")
    public CategoryDTO addCategory(@RequestBody CategoryDTO categoryDTO) {
        return categoryService.add(categoryDTO);
    }

    @PutMapping("/category/update/{id}")
    public CategoryDTO updateCategory(@PathVariable Integer id, @RequestBody CategoryDTO categoryDTO) {
        return categoryService.update(id, categoryDTO);
    }

    @DeleteMapping("/category/delete/{id}")
    public CategoryDTO deleteCategory(@PathVariable Integer id) {
        return categoryService.delete(id);
    }


    /*     Operation with Country     */
    @Autowired
    private CountryService countryService;

    @GetMapping("/country/all")
    public List<CountryDTO> findAllCountry() {
        return countryService.findAll();
    }

    @GetMapping("/country/{id}")
    public CountryDTO findByIdCountry(@PathVariable Integer id) {
        return countryService.findById(id);
    }

    @PostMapping("/country/add")
    public CountryDTO addCountry(@RequestBody CountryDTO countryDTO) {
        return countryService.add(countryDTO);
    }

    @PutMapping("/country/update/{id}")
    public CountryDTO updateCountry(@PathVariable Integer id, @RequestBody CountryDTO countryDTO) {
        return countryService.update(id, countryDTO);
    }

    @DeleteMapping("/country/delete/{id}")
    public CountryDTO deleteCountry(@PathVariable Integer id) {
        return countryService.delete(id);
    }


    /*     Operation with Customer     */
    @Autowired
    private CustomerService customerService;

    @GetMapping("/customer/all")
    public List<CustomerDTO> findAllCustomer() {
        return customerService.findAll();
    }

    @GetMapping("/customer/{id}")
    public CustomerDTO findByIdCustomer(@PathVariable Integer id) {
        return customerService.findById(id);
    }

    @PostMapping("/customer/add")
    public CustomerDTO addCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.add(customerDTO);
    }

    @PutMapping("/customer/update/{id}")
    public CustomerDTO updateCustomer(@PathVariable Integer id, @RequestBody CustomerDTO customerDTO) {
        return customerService.update(id, customerDTO);
    }

    @DeleteMapping("/customer/delete/{id}")
    public CustomerDTO deleteCustomer(@PathVariable Integer id) {
        return customerService.delete(id);
    }


    /*     Operation with Product     */
    @Autowired
    private ProductService productService;

    /* GetMapping in ProductController */

    @PostMapping("/product/add")
    public ProductDTO addProduct(@RequestBody ProductDTO productDTO){
        return productService.add(productDTO);
    }

    @PutMapping("/product/update/{id}")
    public ProductDTO updateProduct(@PathVariable Integer id, @RequestBody ProductDTO productDTO) {
        return productService.update(id, productDTO);
    }

    @DeleteMapping("/product/delete/{id}")
    public ProductDTO deleteProduct(@PathVariable Integer id) {
        return productService.delete(id);
    }


    /*     Operation with ProductShop   */


    /*      Operation with Shop          */
    @Autowired
    private ShopService shopService;

    @GetMapping("/shop/all")
    public List<ShopDTO> findAllShop() {
        return shopService.findAll();
    }

    @GetMapping("/shop/{id}")
    public ShopDTO findByIdShop(@PathVariable Integer id) {
        return shopService.findById(id);
    }

    @PostMapping("/shop/add")
    public ShopDTO addShop(@RequestBody ShopDTO shopDTO) {
        return shopService.add(shopDTO);
    }

    @PutMapping("/shop/update/{id}")
    public ShopDTO updateShop(@PathVariable Integer id, @RequestBody ShopDTO shopDTO) {
        return shopService.update(id, shopDTO);
    }

    @DeleteMapping("/shop/delete/{id}")
    public ShopDTO deleteShop(@PathVariable Integer id) {
        return shopService.delete(id);
    }


    /*     Operation with Supplier     */
    @Autowired
    private SupplierService supplierService;

    @GetMapping("/supplier/all")
    public List<SupplierDTO> findAllSupplier() {
        return supplierService.findAll();
    }

    @GetMapping("/supplier/{id}")
    public SupplierDTO findByIdSupplier(@PathVariable Integer id) {
        return supplierService.findById(id);
    }

    @PostMapping("/supplier/add")
    public SupplierDTO addSupplier(@RequestBody SupplierDTO supplierDTO) {
        return supplierService.add(supplierDTO);
    }

    @PutMapping("/supplier/update/{id}")
    public SupplierDTO updateSupplier(@PathVariable Integer id, @RequestBody SupplierDTO supplierDTO) {
        return supplierService.update(id, supplierDTO);
    }

    @DeleteMapping("/supplier/delete/{id}")
    public SupplierDTO deleteSupplier(@PathVariable Integer id) {
        return supplierService.delete(id);
    }

}

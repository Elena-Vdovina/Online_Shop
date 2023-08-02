package com.onlineshop.service;

import com.onlineshop.controller.dto.ProductDTO;
import com.onlineshop.controller.dto.ProductsDTO;
import com.onlineshop.domain.Category;
import com.onlineshop.domain.Country;
import com.onlineshop.domain.Product;
import com.onlineshop.domain.Supplier;
import com.onlineshop.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SupplierService supplierService;

    static final Logger log = LoggerFactory.getLogger(Supplier.class);

    public ProductsDTO findAll() {
        List<Product> products = productRepository.findAll();
        return ProductsDTO.getInstance(products);
    }

    public ProductDTO findById(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return ProductDTO.getInstance(product.get());
        }
        return null;
    }

    public ProductsDTO findByCategoryId(Integer categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        return ProductsDTO.getInstance(products);
    }

    public ProductsDTO findByPartDescription(String partDescription) {
        List<Product> products = productRepository.findByDescriptionLikeIgnoreCase('%'+partDescription+'%');
        return ProductsDTO.getInstance(products);
    }


    public ProductDTO add(ProductDTO productDTO) {
        Product newProduct = new Product();
        Category category = categoryService.findOrCreateCategoryByName(productDTO.getCategory().getCategoryName());
        newProduct.setCategory(category);
        Supplier supplier = supplierService.findOrCreateSupplierByName(productDTO.getSupplier().getSupplierName());
        newProduct.setSupplier(supplier);
        newProduct.setProductName(productDTO.getProductName());
        newProduct.setDescription(productDTO.getDescription());
        newProduct.setPrice(productDTO.getPrice());
        newProduct.setIsDeleted(productDTO.getIsDeleted());
        newProduct = productRepository.save(newProduct);
        return productDTO.getInstance(newProduct);
    }

    public ProductDTO update(Integer id, ProductDTO productDTO) {
        Product updProduct = productRepository.findById(id).orElse(null);
        if (updProduct != null) {
            updProduct.setProductName(productDTO.getProductName());
            Category category = categoryService.findOrCreateCategoryByName(productDTO.getCategory().getCategoryName());
            updProduct.setCategory(category);
            Supplier supplier = supplierService.findOrCreateSupplierByName(productDTO.getSupplier().getSupplierName());
            updProduct.setSupplier(supplier);
            updProduct.setProductName(productDTO.getProductName());
            updProduct.setDescription(productDTO.getDescription());
            updProduct.setPrice(productDTO.getPrice());
            updProduct.setIsDeleted(productDTO.getIsDeleted());
            productRepository.save(updProduct);
            log.info("product updated: {}", id);
            return productDTO.getInstance(updProduct);
        }
        log.error("Product not found, productId={}", id);
        return null;
    }

    public ProductDTO delete(Integer id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            productRepository.delete(product);
        }
        log.info("product deleted: {}", id);
        return ProductDTO.getInstance(product);
    }

}

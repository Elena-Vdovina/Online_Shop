package com.onlineshop.service;

import com.onlineshop.controller.dto.ProductDTO;
import com.onlineshop.controller.dto.ProductsDTO;
import com.onlineshop.domain.Category;
import com.onlineshop.domain.Product;
import com.onlineshop.domain.Supplier;
import com.onlineshop.repository.CategoryRepository;
import com.onlineshop.repository.ProductRepository;
import com.onlineshop.repository.SupplierRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    public ProductsDTO findAll() {
        List<Product> products = productRepository.findAll();
        log.info("Found list of products");
        return ProductsDTO.getInstance(products);
    }

    public ProductDTO findById(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            log.info("Found Product productId: {} by id", id);
            return ProductDTO.getInstance(product.get());
        }
        log.error("Not found Product productId: {}", id);
        return null;
    }

    public ProductsDTO findByCategoryId(Integer categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        log.info("Found list of products by Category categoryId: {}", categoryId);
        return ProductsDTO.getInstance(products);
    }

    public ProductsDTO findByPartDescription(String partDescription) {
        List<Product> products = productRepository.findByDescriptionLikeIgnoreCase('%' + partDescription + '%');
        log.info("Found list of products by part description");
        return ProductsDTO.getInstance(products);
    }

    public ProductDTO add(ProductDTO productDTO) {
        Product newProduct = newOrUpdateProduct(new Product(), productDTO);
        if (newProduct == null) {
            return null;
        }
        log.info("Product added successfully productId: {}", newProduct.getProductId());
        return ProductDTO.getInstance(newProduct);
    }

    public ProductDTO update(Integer id, ProductDTO productDTO) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            log.error("Not found for update Product productId: {}", productDTO.getProductId());
            return null;
        }
        Product updProduct = newOrUpdateProduct(product.get(), productDTO);
        if (updProduct == null) {
            return null;
        }
        log.info("Product updated successfully productId: {} ", productDTO.getProductId());
        return ProductDTO.getInstance(updProduct);
    }

    private Product newOrUpdateProduct(Product product, ProductDTO productDTO) {
        product.setProductName(productDTO.getProductName());
        Integer categoryId = productDTO.getCategory().getCategoryId();
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isEmpty()) {
            log.error("Not found for add/update Product Category categoryId: {} ", categoryId);
            return null;
        }
        product.setCategory(category.get());
        Integer supplierId = productDTO.getSupplier().getSupplierId();
        Optional<Supplier> supplier = supplierRepository.findById(supplierId);
        if (supplier.isEmpty()) {
            log.error("Not found for add/update Product Supplier supplierId: {} ", supplierId);
            return null;
        }
        product.setSupplier(supplier.get());
        product.setProductName(productDTO.getProductName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setIsDeleted(productDTO.getIsDeleted());
        return productRepository.save(product);
    }

    public ProductDTO delete(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            Product delProduct = product.get();
            productRepository.delete(delProduct);
            log.info("Product deleted successfully productId: {}", id);
            return ProductDTO.getInstance(delProduct);
        }
        log.error("Product for delete not found productId: {}", id);
        return null;
    }

}

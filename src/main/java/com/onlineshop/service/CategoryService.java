package com.onlineshop.service;

import com.onlineshop.controller.dto.CategoryDTO;
import com.onlineshop.domain.Category;
import com.onlineshop.domain.Country;
import com.onlineshop.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    static final Logger log = LoggerFactory.getLogger(Category.class);

    //  private final ModelMapper modelMapper;

    public List<CategoryDTO> findAll() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> result = new ArrayList<>();
        categories.forEach(category -> result.add(CategoryDTO.getInstance(category)));
        return result;
    }

    public CategoryDTO findById(Integer id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null) {
            return CategoryDTO.getInstance(category); // без маппера
            // return modelMapper.map(category,categoryDTO.class); // с маппером
        }
        log.error("category not found categoryId: {}", id);
        return null;
    }

    public CategoryDTO add(CategoryDTO categoryDTO) {
        Category newCategory = new Category();
        newCategory.setCategoryName(categoryDTO.getCategoryName());
        newCategory.setDescription(categoryDTO.getDescription());
        newCategory = categoryRepository.save(newCategory);
        return CategoryDTO.getInstance(newCategory);
    }

    public CategoryDTO update(Integer id, CategoryDTO categoryDTO) {
        Category updCategory = categoryRepository.findById(id).orElse(null);
        if (updCategory != null) {
            updCategory.setCategoryName(categoryDTO.getCategoryName());
            updCategory.setDescription(categoryDTO.getDescription());
            categoryRepository.save(updCategory);
            log.info("category updated: {}", id);
            return CategoryDTO.getInstance(updCategory);
        }
        log.error("category not found, categoryId={}", id);
        return null;
    }

    public CategoryDTO delete(Integer id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null) {
            categoryRepository.delete(category);
        }
        log.info("category deleted: {}", id);
        return CategoryDTO.getInstance(category);
    }

    public Category findOrCreateCategoryByName(String name){
        Category category = categoryRepository.findByName(name);
        if (category == null) {
            category = new Category();
            category.setCategoryName(name);
            categoryRepository.save(category);
            log.info("Category added: {}", category);
        }
        return category;
    }
}

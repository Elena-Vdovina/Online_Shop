package com.onlineshop.service;

import com.onlineshop.controller.dto.CategoryDTO;
import com.onlineshop.domain.Category;
import com.onlineshop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDTO> findAll() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> result = new ArrayList<>();
        categories.forEach(category -> result.add(CategoryDTO.getInstance(category)));
        return result;
    }

    public CategoryDTO findById(Integer id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            return CategoryDTO.getInstance(category.get());
        }
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
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            Category updCategory = category.get();
            updCategory.setCategoryName(categoryDTO.getCategoryName());
            updCategory.setDescription(categoryDTO.getDescription());
            categoryRepository.save(updCategory);
            return CategoryDTO.getInstance(updCategory);
        }
        return null;
    }

    public CategoryDTO delete(Integer id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            Category delCategory = category.get();
            categoryRepository.delete(delCategory);
            return CategoryDTO.getInstance(delCategory);
        }
        return null;
    }

}

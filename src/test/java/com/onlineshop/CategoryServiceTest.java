package com.onlineshop;

import com.onlineshop.controller.dto.CategoryDTO;
import com.onlineshop.service.CategoryService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    @Order(1)
    public void findAllTest() {
        CategoryDTO categoryBeverages = new CategoryDTO(null, "Beverages", "Soft drinks, coffees, teas, beers, and ales ");
        categoryService.add(categoryBeverages);
        CategoryDTO categoryCondiments = new CategoryDTO(null, "Condiments", "Sweet and savory sauces, relishes, spreads, and seasonings ");
        categoryService.add(categoryCondiments);

        List<CategoryDTO> categories = categoryService.findAll();

        Assertions.assertEquals(2, categories.size());
    }

    @Test
    @Order(2)
    public void findByIdTest() {
        CategoryDTO categoryFound = categoryService.findById(1);

        Assertions.assertEquals("Beverages", categoryFound.getCategoryName());
    }

    @Test
    @Order(3)
    public void addTest() {
        CategoryDTO categoryConfections = new CategoryDTO(null, "Confections", "Desserts, candies, and sweet breads");
        CategoryDTO categoryAdded = categoryService.add(categoryConfections);

        Assertions.assertEquals("Confections", categoryAdded.getCategoryName());
    }

    @Test
    @Order(4)
    public void updateTest() {
        CategoryDTO categorySeafood = new CategoryDTO(null, "Seafood", "Seaweed and fish");
        CategoryDTO categoryUpdated = categoryService.update(3, categorySeafood);

        Assertions.assertEquals("Seafood", categoryUpdated.getCategoryName());
    }

    @Test
    @Order(5)
    public void deleteTest() {
        CategoryDTO categoryDeleted = categoryService.delete(3);

        Assertions.assertEquals("Seafood", categoryDeleted.getCategoryName());
    }
}

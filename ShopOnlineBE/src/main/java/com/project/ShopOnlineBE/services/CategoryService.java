package com.project.ShopOnlineBE.services;

import com.project.ShopOnlineBE.components.LocalizationUtils;
import com.project.ShopOnlineBE.dtos.CategoryDTO;
import com.project.ShopOnlineBE.models.Category;
import com.project.ShopOnlineBE.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{
    private final CategoryRepository categoryRepository;
    private final LocalizationUtils localizationUtils;
    @Override
    public Category createCategory(CategoryDTO category) {
        Category newCategory = Category.builder().name(category.getName()).build();
        return categoryRepository.save(newCategory);
    }

    @Override
    public Category getCategoryById(long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category updateCategory(long categoryId, CategoryDTO category) {
        Category existingCategory = getCategoryById(categoryId);
        existingCategory.setName(category.getName());
        categoryRepository.save(existingCategory);
        return existingCategory;
    }

    @Override
    public Category deleteCategory(long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null) {
            try {
                categoryRepository.deleteById(id);
            } catch (Exception e) {
                throw new RuntimeException("Failed to delete category with id: " + id);
            }
        } else {
            throw new RuntimeException("Category not found with id: " + id);
        }
        return category;
    }
}

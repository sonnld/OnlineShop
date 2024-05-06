package com.project.ShopOnlineBE.services;

import com.project.ShopOnlineBE.dtos.CategoryDTO;
import com.project.ShopOnlineBE.models.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDTO category);
    Category getCategoryById(long id);
    List<Category> getAllCategories();
    Category updateCategory(long categoryId, CategoryDTO category);
    Category deleteCategory(long id);
}

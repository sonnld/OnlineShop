package com.project.ShopOnlineBE.controller;

import com.project.ShopOnlineBE.dtos.CategoryDTO;
import com.project.ShopOnlineBE.models.Category;
import com.project.ShopOnlineBE.responses.category.CategoryResponse;
import com.project.ShopOnlineBE.responses.category.UpdateCategoryResponse;
import com.project.ShopOnlineBE.services.CategoryService;
import com.project.ShopOnlineBE.components.LocalizationUtils;
import com.project.ShopOnlineBE.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
//@Validated
public class CategoryController {
    private final CategoryService categoryService;
    private final LocalizationUtils localizationUtils;


    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createCategory(
            @Valid @RequestBody CategoryDTO categoryDTO,
            BindingResult result) {
        try
        {
        CategoryResponse categoryResponse = new CategoryResponse();
        if(result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            categoryResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.INSERT_CATEGORY_FAILED));
            categoryResponse.setErrors(errorMessages);
            return ResponseEntity.badRequest().body(categoryResponse);
        }
        Category category = categoryService.createCategory(categoryDTO);
        categoryResponse.setCategory(category);
        categoryResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.INSERT_CATEGORY_SUCCESSFULLY));
        return ResponseEntity.ok(categoryResponse);
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    }

    //Hiện tất cả các categories
    @GetMapping("")
    public ResponseEntity<?> getAllCategories() {
        try{
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(
            @PathVariable("id") Long categoryId
    ) {
        try {
            Category existingCategory = categoryService.getCategoryById(categoryId);
            return ResponseEntity.ok(existingCategory);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDTO categoryDTO,
            BindingResult result
    ) {
        try{
            UpdateCategoryResponse updateCategoryResponse = new UpdateCategoryResponse();
            if(result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                updateCategoryResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.INSERT_CATEGORY_FAILED));
                updateCategoryResponse.setErrors(errorMessages);
                return ResponseEntity.badRequest().body(updateCategoryResponse);
            }
        categoryService.updateCategory(id, categoryDTO);
        updateCategoryResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.UPDATE_CATEGORY_SUCCESSFULLY));
        return ResponseEntity.ok(updateCategoryResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok().body(localizationUtils.getLocalizedMessage(MessageKeys.DELETE_CATEGORY_SUCCESSFULLY, id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Category cannot be deleted because the category's products exist in it");
        }
    }
}

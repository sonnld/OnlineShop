package com.project.ShopOnlineBE.repositories;

import com.project.ShopOnlineBE.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}

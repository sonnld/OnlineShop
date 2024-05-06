package com.project.ShopOnlineBE.repositories;

import com.project.ShopOnlineBE.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);

    Page<Product> findAll(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE " +
            "(:categoryId IS NULL OR :categoryId = 0 OR p.category.id = :categoryId) " +
            "AND (:keyword IS NULL OR :keyword = '' OR LOWER(p.name) LIKE LOWER(concat('%', :keyword, '%')) " +
            "OR LOWER(p.description) LIKE LOWER(concat('%', :keyword, '%')))")
    Page<Product> searchProducts
            (@Param("categoryId") Long categoryId,
             @Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.id IN :productIds")
    List<Product> findProductsByIds(@Param("productIds") List<Long> productIds);
}

package com.project.ShopOnlineBE.repositories;

import com.project.ShopOnlineBE.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    //Tìm các đơn hàng của 1 user nào đó
    List<Order> findByUserId(Long userId);

    @Query("SELECT o FROM Order o WHERE " +
            "(:keyword IS NULL OR :keyword = '' OR LOWER(o.fullName) LIKE LOWER(concat('%', :keyword, '%')) " +
            "OR LOWER(o.address) LIKE LOWER(concat('%', :keyword, '%')) " +
            "OR LOWER(o.note) LIKE LOWER(concat('%', :keyword, '%')))")
    Page<Order> findByKeyword(String keyword, Pageable pageable);
}

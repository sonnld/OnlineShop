package com.project.ShopOnlineBE.services;

import com.project.ShopOnlineBE.dtos.OrderDTO;
import com.project.ShopOnlineBE.exceptions.DataNotFoundException;
import com.project.ShopOnlineBE.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrderService {
    Order createOrder(OrderDTO orderDTO) throws Exception;
    Order getOrder(Long id);
    Order updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException;
    void deleteOrder(Long id);

    void restoreOder(Long id);

    List<Order> findByUserId(Long userId);

    Page<Order> getOrdersByKeyword(String keyword, Pageable pageable);
}

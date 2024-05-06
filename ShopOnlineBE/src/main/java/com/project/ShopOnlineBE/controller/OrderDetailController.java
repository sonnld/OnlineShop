package com.project.ShopOnlineBE.controller;

import com.project.ShopOnlineBE.components.LocalizationUtils;
import com.project.ShopOnlineBE.dtos.OrderDetailDTO;
import com.project.ShopOnlineBE.exceptions.DataNotFoundException;
import com.project.ShopOnlineBE.models.OrderDetail;
import com.project.ShopOnlineBE.responses.order.OrderDetailResponse;
import com.project.ShopOnlineBE.services.OrderDetailService;
import com.project.ShopOnlineBE.utils.MessageKeys;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("${api.prefix}/order_details")
@RequiredArgsConstructor
public class OrderDetailController {
    private final OrderDetailService orderDetailService;
    private final LocalizationUtils localizationUtils;
    //Thêm mới 1 order detail
    @PostMapping("")
    @Transactional
    public ResponseEntity<?> createOrderDetail(
            @Valid  @RequestBody OrderDetailDTO orderDetailDTO, BindingResult result) {
        try {
            if(result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(error -> error.getDefaultMessage())
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            OrderDetail newOrderDetail = orderDetailService.createOrderDetail(orderDetailDTO);
            return ResponseEntity.ok().body(OrderDetailResponse.fromOrderDetail(newOrderDetail));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(
             @PathVariable("id") Long id) throws DataNotFoundException {
        try{
        OrderDetail orderDetail = orderDetailService.getOrderDetail(id);
        return ResponseEntity.ok().body(OrderDetailResponse.fromOrderDetail(orderDetail));
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    }
    //lấy ra danh sách các order_details của 1 order nào đó
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetails(
             @PathVariable("orderId") Long orderId
    ) {
        try{
        List<OrderDetail> orderDetails = orderDetailService.findByOrderId(orderId);
        List<OrderDetailResponse> orderDetailResponses = orderDetails
                .stream()
                .map(OrderDetailResponse::fromOrderDetail)
                .toList();
        return ResponseEntity.ok(orderDetailResponses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> updateOrderDetail(
            @PathVariable("id") Long id,
        @Valid @RequestBody OrderDetailDTO orderDetailDTO, BindingResult result) {
        try {
            if(result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(error -> error.getDefaultMessage())
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            OrderDetail orderDetail = orderDetailService.updateOrderDetail(id, orderDetailDTO);
            return ResponseEntity.ok().body(orderDetail);
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteOrderDetail(
             @PathVariable("id") Long id) {
        try{
        orderDetailService.deleteById(id);
        return ResponseEntity.ok().body(localizationUtils.getLocalizedMessage(MessageKeys.DELETE_ORDER_DETAIL_SUCCESSFULLY,id));
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    }
}

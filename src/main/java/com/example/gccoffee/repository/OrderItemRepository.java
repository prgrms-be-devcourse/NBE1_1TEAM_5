package com.example.gccoffee.repository;

import com.example.gccoffee.model.OrderItem;

import java.util.List;
import java.util.UUID;

public interface OrderItemRepository {

    // orderId를 통한 OrderItem 목록조회
    List<OrderItem> findByOrderId(UUID orderId);

}

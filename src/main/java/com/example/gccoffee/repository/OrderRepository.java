package com.example.gccoffee.repository;

import com.example.gccoffee.model.Email;
import com.example.gccoffee.model.Order;
import com.example.gccoffee.model.OrderStatus;

import java.util.List;
import java.util.UUID;

public interface OrderRepository {

  Order insert(Order order);

  List<Order> findByEmail(Email email);

  void deleteByOrderId(UUID orderId);

  Order findByOrderId(UUID orderId);

  Order changeOrderStatus(UUID orderId, OrderStatus orderStatus);
}

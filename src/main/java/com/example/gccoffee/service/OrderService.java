package com.example.gccoffee.service;

import com.example.gccoffee.controller.OrderResponse;
import com.example.gccoffee.model.Email;
import com.example.gccoffee.model.Order;
import com.example.gccoffee.model.OrderItem;

import java.util.List;
import java.util.UUID;

public interface OrderService {
  Order createOrder(Email email, String address, String postcode, List<OrderItem> orderItems);

  List<OrderResponse> getOrderListByEmail(Email email);

  void deleteOrder(UUID orderId, Email email) throws Exception;

  OrderResponse cancelOrder(UUID orderId, Email email) throws Exception;

}

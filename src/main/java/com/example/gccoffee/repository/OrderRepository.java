package com.example.gccoffee.repository;

import com.example.gccoffee.model.Order;

import java.util.UUID;

public interface OrderRepository {

  Order insert(Order order);
  boolean existsByProductId(UUID productId);

}

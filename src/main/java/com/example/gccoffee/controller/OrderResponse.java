package com.example.gccoffee.controller;

import com.example.gccoffee.model.Email;
import com.example.gccoffee.model.OrderItem;
import com.example.gccoffee.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderResponse(UUID orderId, Email email, String address, String postcode, List<OrderItem> orderItems, OrderStatus orderStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
}

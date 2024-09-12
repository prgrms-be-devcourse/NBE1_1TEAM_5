package com.example.gccoffee.model;

import java.util.UUID;

public record OrderItem(UUID productId,String productName, Category category, long price, int quantity) {
}
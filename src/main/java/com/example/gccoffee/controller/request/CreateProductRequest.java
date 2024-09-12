package com.example.gccoffee.controller.request;

import com.example.gccoffee.model.Category;
import lombok.Builder;


public record CreateProductRequest(String productName, Category category, long price, String description) {
}

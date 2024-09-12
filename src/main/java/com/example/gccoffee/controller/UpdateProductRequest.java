package com.example.gccoffee.controller;

import com.example.gccoffee.model.Category;

//업데이트할 정보만 포함하고 있으며, Product 객체를 직접 변경
public record UpdateProductRequest(
        String productName,
        Category category,
        long price,
        String description
) {}

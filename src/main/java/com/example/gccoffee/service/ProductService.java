package com.example.gccoffee.service;

import com.example.gccoffee.controller.UpdateProductRequest;
import com.example.gccoffee.model.Category;
import com.example.gccoffee.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {

  List<Product> getProductsByCategory(Category category);

  List<Product> getAllProducts();

  Product createProduct(String productName, Category category, long price);

  Product createProduct(String productName, Category category, long price, String description);

  Product updateProduct(UUID product_id, UpdateProductRequest request);

  void deleteProduct(UUID product_id);

}

package com.example.gccoffee.controller.api;


import com.example.gccoffee.model.Category;
import com.example.gccoffee.model.Product;
import com.example.gccoffee.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ProductRestController {

  private final ProductService productService;

  public ProductRestController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("/api/v1/products")
  public List<Product> productList(@RequestParam Optional<Category> category) {
    return category
      .map(productService::getProductsByCategory)
      .orElse(productService.getAllProducts());
  }

  //상품 업데이트
  @PutMapping("/{productId}")
  public ResponseEntity<Product> updateProduct(@PathVariable UUID productId, @RequestBody UpdateProductRequest request) {
    Product updatedProduct = productService.updateProduct(productId, request);

    return ResponseEntity.ok(updatedProduct);
  }

  //상품 삭제
  @DeleteMapping("/{productId}")
  public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId) {
    productService.deleteProduct(productId);

    return ResponseEntity.noContent().build();
  }

}

package com.example.gccoffee.controller;

import com.example.gccoffee.model.Category;
import com.example.gccoffee.model.Product;
import com.example.gccoffee.service.ProductService;
import org.h2.command.dml.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("/products")
  public String productsPage(Model model) {
    var products = productService.getAllProducts();
    model.addAttribute("products", products);
    return "product-list";
  }

  @GetMapping("new-product")
  public String newProductPage() {
    return "new-product";
  }

  @PostMapping("/products")
  public String newProduct(CreateProductRequest createProductRequest) {
    productService.createProduct(
      createProductRequest.productName(),
      createProductRequest.category(),
      createProductRequest.price(),
      createProductRequest.description());
    return "redirect:/products";
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

package com.example.gccoffee.service;

import com.example.gccoffee.controller.UpdateProductRequest;
import com.example.gccoffee.model.Category;
import com.example.gccoffee.model.Order;
import com.example.gccoffee.model.Product;
import com.example.gccoffee.repository.OrderRepository;
import com.example.gccoffee.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DefaultProductService implements ProductService {

  private final ProductRepository productRepository;
  private final OrderRepository orderRepository;

  public DefaultProductService(ProductRepository productRepository, OrderRepository orderRepository) {
    this.productRepository = productRepository;
      this.orderRepository = orderRepository;
  }

  @Override
  public List<Product> getProductsByCategory(Category category) {
    return productRepository.findByCategory(category);
  }

  @Override
  public List<Product> getAllProducts() {
    return productRepository.findAll();
  }

  @Override
  public Product createProduct(String productName, Category category, long price) {
    var product = new Product(UUID.randomUUID(), productName, category, price);
    return productRepository.insert(product);
  }

  @Override
  public Product createProduct(String productName, Category category, long price, String description) {
    var product = new Product(UUID.randomUUID(), productName, category, price, description, LocalDateTime.now(), LocalDateTime.now());
    return productRepository.insert(product);
  }

  @Override
  public Product updateProduct(UUID product_id, UpdateProductRequest request) {
    //받아온 product_id로 product 조회
    Product product = productRepository.findById(product_id)
            .orElseThrow(()->new IllegalArgumentException("Product not found"));

    // 제품 정보 업데이트
    product.setProductName(request.productName());
    product.setCategory(request.category());
    product.setPrice(request.price());
    product.setDescription(request.description());

    //업데이트
    return productRepository.update(product);
  }

  @Override
  public void deleteProduct(UUID productId) {

    // 제품이 존재하는지 확인
    Product product = productRepository.findById(productId)
            .orElseThrow(() -> new IllegalArgumentException("Product not found"));

    // product_id가 order_items 테이블에서 참조되는지
    if (orderRepository.existsByProductId(productId)) {
      throw new IllegalArgumentException("Product cannot be deleted because it is referenced in order items.");
    }

    // 제품 삭제
    productRepository.delete(productId);
  }

}

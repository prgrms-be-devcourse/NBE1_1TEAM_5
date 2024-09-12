package com.example.gccoffee.controller.api;

import com.example.gccoffee.controller.CreateOrderRequest;
import com.example.gccoffee.controller.OrderResponse;
import com.example.gccoffee.controller.request.CreateOrderRequest;
import com.example.gccoffee.model.Email;
import com.example.gccoffee.model.Order;
import com.example.gccoffee.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class OrderRestController {

  private final OrderService orderService;

  public OrderRestController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping("/api/v1/orders")
  public Order createOrder(@RequestBody CreateOrderRequest orderRequest) {
    return orderService.createOrder(
            new Email(orderRequest.email()),
            orderRequest.address(),
            orderRequest.postcode(),
            orderRequest.orderItems()
    );
  }

  // 주문 목록 조회
  // 일단은 그냥 PathVariable을 이용해서 이메일을 받아오는것으로 구현
  // 예외처리 전
  @GetMapping("/api/v1/orders/list/{email}")
  public List<OrderResponse> getOrderList(@PathVariable("email") Email email) {
    List<OrderResponse> findOrderList = orderService.getOrderListByEmail(email);
    if (findOrderList == null) {
      return null;
      // throw new ORDER_NOT_FOUDN // 추후 예외처리
    }
    return findOrderList;
  }

  // 주문 취소(주문상태가 배송전일때만 가능)
  // 우선 email, orderId 를 PathVariable로 처리
  @PutMapping("/api/v1/orders/cancel/{email}/{orderId}")
  public OrderResponse cancelOrder(@PathVariable("email") Email email, @PathVariable("orderId") UUID orderId) {
      try {
        OrderResponse orderResponse = orderService.cancelOrder(orderId, email);
        return orderResponse;
      } catch (Exception e) {
          throw new RuntimeException(e);
      }
  }

  // 주문 내역 삭제(주문상태가 완료혹은 취소일때 가능)
  @PutMapping("/api/v1/orders/delete/{email}/{orderId}")
  public String deleteOrderHistory(@PathVariable("email") Email email, @PathVariable("orderId") UUID orderId) {
    try {
      orderService.deleteOrder(orderId, email);
      return "주문내역 삭제 완료!";
    } catch (Exception e) {
      e.printStackTrace();
      return "주문내역 삭제 불가능!";
    }
  }

}

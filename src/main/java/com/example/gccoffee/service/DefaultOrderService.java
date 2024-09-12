package com.example.gccoffee.service;

import ch.qos.logback.core.status.ErrorStatus;
import com.example.gccoffee.controller.OrderResponse;
import com.example.gccoffee.apiResponse.code.status.ErrorStatus;
import com.example.gccoffee.apiResponse.exception.GeneralException;
import com.example.gccoffee.model.Email;
import com.example.gccoffee.model.Order;
import com.example.gccoffee.model.OrderItem;
import com.example.gccoffee.model.OrderStatus;
import com.example.gccoffee.repository.OrderItemRepository;
import com.example.gccoffee.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DefaultOrderService implements OrderService {

  private final OrderRepository orderRepository;
  private final OrderItemRepository orderItemRepository;

  public DefaultOrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
    this.orderRepository = orderRepository;
      this.orderItemRepository = orderItemRepository;
  }

  @Override
  public Order createOrder(Email email, String address, String postcode, List<OrderItem> orderItems) {

    Order order = new Order(
      UUID.randomUUID(),
      email,
      address,
      postcode,
      orderItems,
      OrderStatus.ACCEPTED,
      LocalDateTime.now(),
      LocalDateTime.now());
    return orderRepository.insert(order);

  }


  // OrderList 조회
  @Override
  public List<OrderResponse> getOrderListByEmail(Email email) {
    List<Order> orderList = orderRepository.findByEmail(email);
    if (orderList == null) {
//      throw new GeneralException(ErrorStatus.ORDER_NOT_FOUND);
      return null; // 예외처리 하기 전
    }

    return orderList.stream().map(order -> {
      List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getOrderId());

      return new OrderResponse(
              order.getOrderId(),
              order.getEmail(),
              order.getAddress(),
              order.getPostcode(),
              orderItems,
              order.getOrderStatus(),
              order.getCreatedAt(),
              order.getUpdatedAt()
      );
    }).collect(Collectors.toList());
  }

  // Order 삭제 (주문 상태가 완료, 취소일때만 가능)
  @Override
  public void deleteOrder(UUID orderId, Email email) throws Exception { // 이후 Email 받아오는거 변경
    // orderId로 Order조회
    Order byOrderId = orderRepository.findByOrderId(orderId);
    if (byOrderId == null) { // 해당 Order가 없을 경우
      throw new Exception("해당 Order가 없음");
//      throw new GeneralException(ErrorStatus.ORDER_NOT_FOUND); 예외처리
    }
    if (byOrderId.getEmail().equals(email) && (byOrderId.getOrderStatus().equals(OrderStatus.SETTLED) || byOrderId.getOrderStatus().equals(OrderStatus.CANCELLED))) {
      orderRepository.deleteByOrderId(orderId);
    }else{
      throw new Exception("본인의 order가 아니거나 배송상태가 취소 혹은 완료가 아님");
//      throw new GeneralException(ErrorStatus.ORDER_DELETE_FAIL) // 예외 처리
    }
  }

  // Order 취소 (주문 상태가 READY_FOR_DELIVERY 이전일때 가능)
  @Override
  public OrderResponse cancelOrder(UUID orderId, Email email) throws Exception { // 이후 Email 받아오는거 변경
    Order byOrderId = orderRepository.findByOrderId(orderId);
    if (byOrderId == null) {
      throw new Exception("Order가 없음");
//      throw new GeneralException(ErrorStatus.ORDER_NOT_FOUND); 예외처리
    }
    if (byOrderId.getEmail().equals(email) && (byOrderId.getOrderStatus().equals(OrderStatus.ACCEPTED) || (byOrderId.getOrderStatus().equals(OrderStatus.PAYMENT_CONFIRMED)))) {
      Order updatedOrder = orderRepository.changeOrderStatus(orderId, OrderStatus.CANCELLED);
      return new OrderResponse(updatedOrder.getOrderId(), updatedOrder.getEmail(), updatedOrder.getAddress(), updatedOrder.getPostcode(),
              updatedOrder.getOrderItems(), updatedOrder.getOrderStatus(), updatedOrder.getCreatedAt(), updatedOrder.getUpdatedAt());
    }else{
      throw new Exception("본인의 주문이 아니거나 주문취소가 불가능한 상태임");
      //      throw new GeneralException(ErrorStatus.ORDER_CANCEL_FAIL) // 예외 처리
    }
  }

}

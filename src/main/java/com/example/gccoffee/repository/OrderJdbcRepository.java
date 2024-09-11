package com.example.gccoffee.repository;

import com.example.gccoffee.model.Email;
import com.example.gccoffee.model.Order;
import com.example.gccoffee.model.OrderItem;
import com.example.gccoffee.model.OrderStatus;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class OrderJdbcRepository implements OrderRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final OrderItemRepository orderItemRepository;

  public OrderJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate, OrderItemRepository orderItemRepository) {
    this.jdbcTemplate = jdbcTemplate;
      this.orderItemRepository = orderItemRepository;
  }

  // 사용자 이메일로 주문목록 조회
  @Override
  @Transactional
  public List<Order> findByEmail(Email email) {
    return jdbcTemplate.query(
            "SELECT BIN_TO_UUID(order_id) as order_id, email, address, postcode, order_status, created_at, updated_at " +
                    "FROM orders WHERE email = :email",
            Map.of("email", email.getAddress()),  // 이메일로 파라미터 매핑
            (rs, rowNum) -> new Order(
                    UUID.fromString(rs.getString("order_id")),
                    email,  // 이미 Email 객체를 가지고 있기 때문에 직접 넣어줌
                    rs.getString("address"),
                    rs.getString("postcode"),
                    null,
                    OrderStatus.valueOf(rs.getString("order_status")),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null
            )
    );
  }

  // orderId로 Order 삭제
  @Transactional
  @Override
  public void deleteByOrderId(UUID orderId) {
    jdbcTemplate.update(
            "DELETE FROM orders WHERE order_id = UUID_TO_BIN(:orderId)",
            Map.of("orderId", orderId.toString().getBytes())
    );
  }

  // 주문 상태 수정
  @Transactional
  @Override
  public Order changeOrderStatus(UUID orderId, OrderStatus orderStatus) {
    int updatedRows = jdbcTemplate.update(
            "UPDATE orders SET order_status = :orderStatus, updated_at = NOW() WHERE order_id = UUID_TO_BIN(:orderId)",
            Map.of("orderStatus", orderStatus.name(),
                    "orderId", orderId.toString().getBytes()
            )
    );

    if (updatedRows > 0) {
      return findByOrderId(orderId);
    }else {
//      throw new GeneralException(ErrorStatus.ORDER_STATUS_CHANGE_FAIL); 에러처리
      return null;
    }
  }

  // orderId로 Order 객체 조회
  @Override
  public Order findByOrderId(UUID orderId) {
    return jdbcTemplate.queryForObject(
            "SELECT BIN_TO_UUID(order_id) as order_id, email, address, postcode, order_status, created_at, updated_at "
                    + "FROM orders WHERE order_id = UUID_TO_BIN(:orderId)",
            Map.of("orderId", orderId.toString().getBytes()),
            (rs, rowNum) -> new Order(
                    UUID.fromString(rs.getString("order_id")),
                    new Email(rs.getString("email")),
                    rs.getString("address"),
                    rs.getString("postcode"),
                    orderItemRepository.findByOrderId(orderId),
                    OrderStatus.valueOf(rs.getString("order_status")),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null
            )
    );
  }

  @Override
  @Transactional
  public Order insert(Order order) {
    jdbcTemplate.update("INSERT INTO orders(order_id, email, address, postcode, order_status, created_at, updated_at) " +
        "VALUES (UUID_TO_BIN(:orderId), :email, :address, :postcode, :orderStatus, :createdAt, :updatedAt)",
      toOrderParamMap(order));
    order.getOrderItems()
      .forEach(item ->
        jdbcTemplate.update("INSERT INTO order_items(order_id, product_id, category, price, quantity, created_at, updated_at) " +
            "VALUES (UUID_TO_BIN(:orderId), UUID_TO_BIN(:productId), :category, :price, :quantity, :createdAt, :updatedAt)",
          toOrderItemParamMap(order.getOrderId(), order.getCreatedAt(), order.getUpdatedAt(), item)));
    return order;
  }

  private Map<String, Object> toOrderParamMap(Order order) {
    var paramMap = new HashMap<String, Object>();
    paramMap.put("orderId", order.getOrderId().toString().getBytes());
    paramMap.put("email", order.getEmail().getAddress());
    paramMap.put("address", order.getAddress());
    paramMap.put("postcode", order.getPostcode());
    paramMap.put("orderStatus", order.getOrderStatus().toString());
    paramMap.put("createdAt", order.getCreatedAt());
    paramMap.put("updatedAt", order.getUpdatedAt());
    return paramMap;
  }

  private Map<String, Object> toOrderItemParamMap(UUID orderId, LocalDateTime createdAt, LocalDateTime updatedAt, OrderItem item) {
    var paramMap = new HashMap<String, Object>();
    paramMap.put("orderId", orderId.toString().getBytes());
    paramMap.put("productId", item.productId().toString().getBytes());
    paramMap.put("category", item.category().toString());
    paramMap.put("price", item.price());
    paramMap.put("quantity", item.quantity());
    paramMap.put("createdAt", createdAt);
    paramMap.put("updatedAt", updatedAt);
    return paramMap;
  }
}

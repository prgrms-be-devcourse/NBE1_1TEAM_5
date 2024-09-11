package com.example.gccoffee.repository;

import com.example.gccoffee.model.Category;
import com.example.gccoffee.model.OrderItem;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class OrderItemJdbcRepository implements OrderItemRepository{

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OrderItemJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<OrderItem> findByOrderId(UUID orderId) {
        return jdbcTemplate.query(
                "SELECT BIN_TO_UUID(product_id) as product_id, category, price, quantity " +
                        "FROM order_items WHERE order_id = UUID_TO_BIN(:orderId)",
                Map.of("orderId", orderId.toString().getBytes()),
                (rs, rowNum) -> new OrderItem(
                        UUID.fromString(rs.getString("product_id")),
                        Category.valueOf(rs.getString("category")), // Category는 적절히 변환해줘야 함
                        rs.getLong("price"),
                        rs.getInt("quantity")
                )
        );
    }

}

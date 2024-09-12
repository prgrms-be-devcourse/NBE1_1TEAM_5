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
    private final ProductRepository productRepository;

    public OrderItemJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate, ProductRepository productRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.productRepository = productRepository;
    }

    @Override
    public List<OrderItem> findByOrderId(UUID orderId) {
        return jdbcTemplate.query(
                "SELECT BIN_TO_UUID(o.product_id) as product_id, p.product_name, o.category, o.price, o.quantity " +
                        "FROM order_items o " +
                        "JOIN products p ON o.product_id = p.product_id " +
                        "WHERE o.order_id = UUID_TO_BIN(:orderId)",
                Map.of("orderId", orderId.toString().getBytes()),
                (rs, rowNum) -> new OrderItem(
                        UUID.fromString(rs.getString("product_id")),
                        rs.getString("product_name"),
                        Category.valueOf(rs.getString("category")),
                        rs.getLong("price"),
                        rs.getInt("quantity")
                )
        );
    }

}

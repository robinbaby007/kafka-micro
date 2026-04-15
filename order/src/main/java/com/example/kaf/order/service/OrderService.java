package com.example.kaf.order.service;

import com.example.kaf.order.events.OrderCreatedEvent;
import com.example.kaf.order.kafka.OrderProducer;
import com.example.kaf.order.models.CreateOrderRequest;
import com.example.kaf.order.models.Order;
import com.example.kaf.order.models.OrderResponse;
import com.example.kaf.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProducer OrderProducer;

    public OrderResponse createOrder(CreateOrderRequest request) {
        // Generate unique order ID
        String orderId = UUID.randomUUID().toString();

        // Create entity
        Order order = new Order();
        order.setOrderId(orderId);
        order.setCustomerName(request.getCustomerName());
        order.setTotalAmount(request.getTotalAmount());
        order.setStatus("PENDING");

        // Save to database
        Order savedOrder = orderRepository.save(order);

        // 🔥 NEW: Publish event to Kafka (async, notify service will hear this)
        OrderCreatedEvent event = new OrderCreatedEvent(
                savedOrder.getOrderId(),
                savedOrder.getCustomerName(),
                savedOrder.getTotalAmount(),
                savedOrder.getStatus(),
                savedOrder.getCreatedAt()
        );

        OrderProducer.publishOrderCreatedEvent(event);

        // Convert to DTO and return
        return new OrderResponse(
                savedOrder.getOrderId(),
                savedOrder.getCustomerName(),
                savedOrder.getTotalAmount(),
                savedOrder.getStatus(),
                savedOrder.getCreatedAt()
        );
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(order -> new OrderResponse(
                        order.getOrderId(),
                        order.getCustomerName(),
                        order.getTotalAmount(),
                        order.getStatus(),
                        order.getCreatedAt()
                ))
                .toList();
    }
}

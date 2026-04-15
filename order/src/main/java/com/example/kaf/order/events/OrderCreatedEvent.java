package com.example.kaf.order.events;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {
    private String orderId;
    private String customerName;
    private Double totalAmount;
    private String status;
    private LocalDateTime createdAt;
}

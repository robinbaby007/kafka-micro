package com.example.kaf.order.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @Column(columnDefinition = "VARCHAR(36)")
    private String orderId; // UUID as String

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private Double totalAmount;

    @Column(nullable = false)
    private String status; // PENDING, CONFIRMED, etc.

    @CreationTimestamp
    private LocalDateTime createdAt;
}


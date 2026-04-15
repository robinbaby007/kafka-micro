package com.example.kaf.notify.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String logId;

    @Column(nullable = false)
    private String type; // "order", "payment", etc.

    @Column(nullable = false)
    private String entityId; // order ID, payment ID, etc.

    @Column(nullable = false)
    private String customerId;

    @Column(nullable = false)
    private Double amount;

    @Column(columnDefinition = "TEXT")
    private String details; // Additional info

    @CreationTimestamp
    private LocalDateTime createdAt;
}


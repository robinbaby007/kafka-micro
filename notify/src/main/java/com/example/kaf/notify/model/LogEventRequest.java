package com.example.kaf.notify.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// LogEventRequest.java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogEventRequest {
    private String type;
    private String id;          // entityId (order ID)
    private String customerId;
    private Double amount;
    private String details;     // Optional
}

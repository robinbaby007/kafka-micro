package com.example.kaf.notify.controller;

import com.example.kaf.notify.model.Log;
import com.example.kaf.notify.service.LogService;
import com.example.kaf.order.models.CreateOrderRequest;
import com.example.kaf.order.models.OrderResponse;
import com.example.kaf.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class OrderController {

    private final LogService logService;

    @GetMapping
    public ResponseEntity<List<Log>> createOrder() {
        List<Log> logList = logService.getAllLogs();
        return ResponseEntity.status(HttpStatus.CREATED).body(logList);
    }


}

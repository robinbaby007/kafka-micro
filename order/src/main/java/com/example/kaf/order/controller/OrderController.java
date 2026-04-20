package com.example.kaf.order.controller;

import com.example.kaf.order.models.CreateOrderRequest;
import com.example.kaf.order.models.OrderResponse;
import com.example.kaf.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(
            @RequestHeader(value = "X-User-Email", required = false) String userEmail,
            @RequestBody CreateOrderRequest request) {
        if (isAuthError(userEmail)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(getAuthErrorResponse());
        }
        OrderResponse response = orderService.createOrder(request,userEmail);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders(
            @RequestHeader(value = "X-User-Email", required = false) String userEmail
    ) {
        if (isAuthError(userEmail)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(getAuthErrorResponse());
        }
        List<OrderResponse> response = orderService.findAllByEmail(userEmail);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    private Boolean isAuthError(String userEmail) {
        if (userEmail == null || userEmail.isEmpty()) {
            String errorMessage = "Unauthorized: User email not found in X-User-Email header";
            System.out.println(errorMessage);
            return true;
        } else {
            return false;
        }
    }

    private HashMap<String,String> getAuthErrorResponse(){
        HashMap<String, String> errorMap = new HashMap<>();
        errorMap.put("error", "Unauthorized: User email not found in X-User-Email header");
        return errorMap;
    }
}

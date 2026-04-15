package com.example.kaf.notify.kafka;

import com.example.kaf.notify.model.LogEventRequest;
import com.example.kaf.notify.model.OrderCreatedEvent;
import com.example.kaf.notify.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderEventListener {

    private final LogService logService;

    @KafkaListener(topics = "order-events", groupId = "notify-group")
    public void handleOrderCreatedEvent(OrderCreatedEvent event) {
        System.out.println("logeme" + event.toString());
        logService.logEvent(new LogEventRequest(
                "ORDER_CREATED",
                event.getOrderId(),
                "1",
                event.getTotalAmount(),
                "By Robin"
        ));
    }
}
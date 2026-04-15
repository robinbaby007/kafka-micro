package com.example.kaf.order.kafka;

import com.example.kaf.order.events.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component  // ← Marks this as a Spring-managed bean
@RequiredArgsConstructor  // ← Lombok auto-injects KafkaTemplate
public class OrderProducer {

    private static final String TOPIC = "order-events";  // Topic name


    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public void publishOrderCreatedEvent(OrderCreatedEvent event) {
        kafkaTemplate.send(TOPIC, event.getOrderId(), event);  // Key: orderId, Value: event object
    }

}

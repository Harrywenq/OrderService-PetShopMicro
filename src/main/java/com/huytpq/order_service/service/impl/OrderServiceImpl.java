package com.huytpq.order_service.service.impl;

import com.huytpq.order_service.entity.Order;
import com.huytpq.order_service.event.OrderPlacedEvent;
import com.huytpq.order_service.repository.OrderRepository;
import com.huytpq.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public Order create(Order order) {
        Order saved = orderRepository.save(order);

        // gửi event kafka
        OrderPlacedEvent event = OrderPlacedEvent.builder()
                .orderId(saved.getId())
                .userId(saved.getUserId())
                .total(saved.getTotal())
                .build();

        kafkaTemplate.send("order-topic", event);
        System.out.println("Kafka event sent: " + event);

        return saved;
    }
}

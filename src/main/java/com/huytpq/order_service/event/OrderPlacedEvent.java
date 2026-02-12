package com.huytpq.order_service.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class OrderPlacedEvent {
    private Long orderId;
    private Long userId;
    private Double total;
}

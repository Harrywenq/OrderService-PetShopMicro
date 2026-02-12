package com.huytpq.order_service.dto.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OrderOutput {
    private Long id;

    private String product;

    private Double price;

    private UserOutput user;

}

package com.huytpq.order_service.dto.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserOutput {
    private Long id;

    private String name;

    private String email;
}

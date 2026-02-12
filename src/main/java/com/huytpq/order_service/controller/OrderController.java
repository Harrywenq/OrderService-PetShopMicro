package com.huytpq.order_service.controller;

import com.huytpq.order_service.dto.output.OrderOutput;
import com.huytpq.order_service.dto.output.UserOutput;
import com.huytpq.order_service.entity.Order;
import com.huytpq.order_service.feign.UserClient;
import com.huytpq.order_service.grpc.GrpcUserClient;
import com.huytpq.order_service.grpc.UserResponse;
import com.huytpq.order_service.repository.OrderRepository;
import com.huytpq.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")

public class OrderController {

    private final OrderRepository orderRepository;
    private final UserClient userClient;
    private final OrderService orderService;

    private final GrpcUserClient grpcUserClient;

    @GetMapping
    public List<Order> getList() {
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    public OrderOutput get(@PathVariable Long id) {
        Order order = orderRepository.findById(id).orElseThrow();

        // Gọi user-service bằng Feign
        UserOutput user = userClient.getUserById(order.getUserId());

        return new OrderOutput(order.getId(), order.getProduct(), order.getPrice(), user);
    }

//    @PostMapping
//    public Order create(@RequestBody Order order) {
//        return orderRepository.save(order);
//    }

//    --for jwt--
//    @PostMapping
//    public Order placeOrder(@RequestBody Order order) {
//        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        order.setUserId(userId);
//        return orderService.create(order);
//    }

    @PostMapping
    public Order placeOrder(@RequestBody Order order, JwtAuthenticationToken auth) {
        String sub = auth.getToken().getSubject(); // lấy Keycloak-sub (UUID)
        UserOutput user = userClient.getUserByKeycloakId(sub);

        order.setUserId(user.getId());
        return orderService.create(order);
    }

    //call gprc client
    @GetMapping("/grpc/{id}")
    public OrderOutput getOrderGrpc(@PathVariable Long id) {
        Order order = orderRepository.findById(id).orElseThrow();

        // Gọi gRPC client để lấy thông tin user từ user-service
        UserResponse user = grpcUserClient.getUserById(order.getUserId());

        UserOutput userDto = new UserOutput(user.getId(), user.getName(), user.getEmail());
        return new OrderOutput(order.getId(), order.getProduct(), order.getPrice(), userDto);
    }

}

package com.huytpq.order_service.feign;

import com.huytpq.order_service.config.FeignClientConfig;
import com.huytpq.order_service.dto.output.UserOutput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service",configuration = FeignClientConfig.class)
public interface UserClient {

    @GetMapping("/api/users/{id}")
    UserOutput getUserById(@PathVariable("id") Long id);

    @GetMapping("/api/users/keycloak/{sub}")
    UserOutput getUserByKeycloakId(@PathVariable("sub") String keycloakId);
}

package com.huytpq.order_service.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@Configuration
public class FeignClientConfig {
//    --for jwt--
//    @Bean
//    public RequestInterceptor requestInterceptor() {
//        return new RequestInterceptor() {
//            @Override
//            public void apply(RequestTemplate template) {
//                String token = getToken();
//                if (token != null) {
//                    template.header("Authorization", token);
//                }
//            }
//
//            private String getToken() {
//                return "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJodXl0cHFoaGhAZ21haWwuY29tIiwiaWF0IjoxNzU2MjI2OTk3LCJleHAiOjE3NTYzMTMzOTd9.vuqwxjts3eZoj_yQvO_jahS1oO7o4qlfUXJeUtMcOuw";
//            }
//        };
//    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof JwtAuthenticationToken jwtAuth) {
                String token = jwtAuth.getToken().getTokenValue();
                requestTemplate.header("Authorization", "Bearer " + token);
            }
        };
    }
}
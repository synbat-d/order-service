package com.polarbookshop.order_service.order.web;

import com.polarbookshop.order_service.order.domain.Order;
import com.polarbookshop.order_service.order.domain.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    @GetMapping
    public Flux<Order> getAllOrders(@AuthenticationPrincipal Jwt jwt) {
        log.info("Fetching all orders for user {}", jwt.getSubject());
        return orderService.getAllOrders(jwt.getSubject());
    }

    @PostMapping
    public Mono<Order> submitOrder(
            @RequestBody @Valid OrderRequest orderRequest
    ) {
        log.info("Submitting order for ISBN {} with quantity {}", orderRequest.isbn(), orderRequest.quantity());
        return orderService.submitOrder(orderRequest.isbn(), orderRequest.quantity());
    }
}

package com.shouvick.orderservice.controller;

import com.shouvick.orderservice.dto.PurchaseOrderRequestDto;
import com.shouvick.orderservice.dto.PurchaseOrderResponseDto;
import com.shouvick.orderservice.service.OrderFulfillmentService;
import com.shouvick.orderservice.service.OrderQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("order")
public class PurchaseOrderController {

    @Autowired
    private OrderFulfillmentService orderFulfillmentService;

    @Autowired
    private OrderQueryService orderQueryService;

    @PostMapping
    public Mono<PurchaseOrderResponseDto> order(@RequestBody Mono<PurchaseOrderRequestDto> purchaseOrderRequestDtoMono){
        return orderFulfillmentService.processOrder(purchaseOrderRequestDtoMono);
    }

    @GetMapping("user/{userId}")
    public Flux<PurchaseOrderResponseDto> getOrdersByUserId(@PathVariable int userId){
        return orderQueryService.getProductsByUserId(userId);
    }
}

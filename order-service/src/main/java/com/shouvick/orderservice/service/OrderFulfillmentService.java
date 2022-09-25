package com.shouvick.orderservice.service;

import com.shouvick.orderservice.client.ProductClient;
import com.shouvick.orderservice.client.UserClient;
import com.shouvick.orderservice.dto.PurchaseOrderRequestDto;
import com.shouvick.orderservice.dto.PurchaseOrderResponseDto;
import com.shouvick.orderservice.dto.RequestContext;
import com.shouvick.orderservice.repository.PurchaseOrderRepository;
import com.shouvick.orderservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Service
public class OrderFulfillmentService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private UserClient userClient;

    public Mono<PurchaseOrderResponseDto> processOrder(Mono<PurchaseOrderRequestDto> purchaseOrderRequestDtoMono){
        return purchaseOrderRequestDtoMono.map(RequestContext::new)
                .flatMap(this::productRequestResponse)
                .doOnNext(EntityDtoUtil::setTransactionRequestDto)
                .flatMap(this::userRequestResponse)
                .map(EntityDtoUtil::getPurchaseOrder)
                .map(purchaseOrderRepository::save)
                .map(EntityDtoUtil::getPurchaseOrderResponseDto)
                .subscribeOn(Schedulers.boundedElastic());
    }

    private Mono<RequestContext> productRequestResponse(RequestContext rc){
       return productClient.getProductById(rc.getPurchaseOrderRequestDto().getProductId())
                .doOnNext(rc::setProductDto)
                .thenReturn(rc);
    }

    private Mono<RequestContext> userRequestResponse(RequestContext rc){
        return userClient.authorizeTransaction(rc.getTransactionRequestDto())
                .doOnNext(rc::setTransactionResponseDto)
                .thenReturn(rc);
    }
}

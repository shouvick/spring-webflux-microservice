package com.shouvick.orderservice.service;

import com.shouvick.orderservice.dto.PurchaseOrderResponseDto;
import com.shouvick.orderservice.repository.PurchaseOrderRepository;
import com.shouvick.orderservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Service
public class OrderQueryService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    public Flux<PurchaseOrderResponseDto> getProductsByUserId(int userId){
       return Flux.fromStream(() -> purchaseOrderRepository.findByUserId(userId).stream())
                .map(EntityDtoUtil::getPurchaseOrderResponseDto)
                .subscribeOn(Schedulers.boundedElastic());

    }
}

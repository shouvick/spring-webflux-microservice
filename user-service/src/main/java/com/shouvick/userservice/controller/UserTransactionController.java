package com.shouvick.userservice.controller;

import com.shouvick.userservice.dto.TransactionRequestDto;
import com.shouvick.userservice.dto.TransactionResponseDto;
import com.shouvick.userservice.entity.UserTransaction;
import com.shouvick.userservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user/transaction")
public class UserTransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public Mono<TransactionResponseDto> createTransaction(@RequestBody Mono<TransactionRequestDto> transactionRequestDtoMono){
        return transactionRequestDtoMono.flatMap(transactionService::createTransaction);
    }

    @GetMapping
    public Flux<UserTransaction> getByUserId(@RequestParam("userId") int userId){
        return transactionService.getByUserId(userId);
    }

}

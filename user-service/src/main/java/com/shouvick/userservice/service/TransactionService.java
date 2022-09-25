package com.shouvick.userservice.service;

import com.shouvick.userservice.dto.TransactionRequestDto;
import com.shouvick.userservice.dto.TransactionResponseDto;
import com.shouvick.userservice.dto.TransactionStatus;
import com.shouvick.userservice.entity.UserTransaction;
import com.shouvick.userservice.repository.UserRepository;
import com.shouvick.userservice.repository.UserTransactionRepository;
import com.shouvick.userservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTransactionRepository userTransactionRepository;

    public Mono<TransactionResponseDto> createTransaction(final TransactionRequestDto transactionRequestDto){
        return userRepository.updateUserBalance(transactionRequestDto.getUserId(), transactionRequestDto.getAmount())
                .filter(Boolean::booleanValue)
                .map(b -> EntityDtoUtil.toEntity(transactionRequestDto))
                .flatMap(userTransactionRepository::save)
                .map(ut -> EntityDtoUtil.toDto(transactionRequestDto, TransactionStatus.APPROVED))
                .defaultIfEmpty(EntityDtoUtil.toDto(transactionRequestDto, TransactionStatus.DECLINED));

    }

    public Flux<UserTransaction> getByUserId(int userId) {
       return userTransactionRepository.findByUserId(userId);
    }
}

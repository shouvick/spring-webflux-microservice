package com.shouvick.userservice.util;

import com.shouvick.userservice.dto.TransactionRequestDto;
import com.shouvick.userservice.dto.TransactionResponseDto;
import com.shouvick.userservice.dto.TransactionStatus;
import com.shouvick.userservice.dto.UserDto;
import com.shouvick.userservice.entity.User;
import com.shouvick.userservice.entity.UserTransaction;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

public class EntityDtoUtil {

    public static UserDto toDto(User user){
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user,userDto);
        return userDto;
    }

    public static User toEntity(UserDto userDto){
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        return user;
    }

    public static UserTransaction toEntity(TransactionRequestDto transactionRequestDto){
        UserTransaction userTransaction = new UserTransaction();
        userTransaction.setUserId(transactionRequestDto.getUserId());
        userTransaction.setAmount(transactionRequestDto.getAmount());
        userTransaction.setTransactionDate(LocalDateTime.now());
        return userTransaction;
    }

    public static TransactionResponseDto toDto(TransactionRequestDto transactionRequestDto, TransactionStatus transactionStatus){
        TransactionResponseDto transactionResponseDto = new TransactionResponseDto();
        transactionResponseDto.setAmount(transactionRequestDto.getAmount());
        transactionResponseDto.setUserId(transactionRequestDto.getUserId());
        transactionResponseDto.setStatus(transactionStatus);
        return transactionResponseDto;
    }
}

package com.shouvick.userservice.entity;


import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@ToString
public class UserTransaction {

    @Id
    private Integer id;
    private Integer userId;
    private Double amount;
    private LocalDateTime transactionDate;
}

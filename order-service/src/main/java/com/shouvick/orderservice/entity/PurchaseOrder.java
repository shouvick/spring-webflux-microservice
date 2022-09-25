package com.shouvick.orderservice.entity;

import com.shouvick.orderservice.dto.OrderStatus;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@ToString
public class PurchaseOrder {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer userId;
    private String productId;
    private Double amount;
    private OrderStatus orderStatus;
}

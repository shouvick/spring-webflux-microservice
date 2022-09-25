package com.shouvick.orderservice.util;

import com.shouvick.orderservice.dto.*;
import com.shouvick.orderservice.entity.PurchaseOrder;
import org.hibernate.criterion.Order;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {

    public static PurchaseOrderResponseDto getPurchaseOrderResponseDto(PurchaseOrder purchaseOrder){
        PurchaseOrderResponseDto purchaseOrderResponseDto = new PurchaseOrderResponseDto();
        BeanUtils.copyProperties(purchaseOrder, purchaseOrderResponseDto);
        purchaseOrderResponseDto.setOrderId(purchaseOrder.getId());
        return purchaseOrderResponseDto;
    }

    public static void setTransactionRequestDto(RequestContext requestContext){
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setUserId(requestContext.getPurchaseOrderRequestDto().getUserId());
        transactionRequestDto.setAmount(requestContext.getProductDto().getPrice());
        requestContext.setTransactionRequestDto(transactionRequestDto);
    }

    public static PurchaseOrder getPurchaseOrder(RequestContext requestContext){
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setUserId(requestContext.getPurchaseOrderRequestDto().getUserId());
        purchaseOrder.setProductId(requestContext.getPurchaseOrderRequestDto().getProductId());
        purchaseOrder.setAmount(requestContext.getProductDto().getPrice());

        TransactionStatus status = requestContext.getTransactionResponseDto().getStatus();
        OrderStatus orderStatus = TransactionStatus.APPROVED.equals(status) ? OrderStatus.COMPLETED : OrderStatus.FAILED;

        purchaseOrder.setOrderStatus(orderStatus);
        return purchaseOrder;
    }
}

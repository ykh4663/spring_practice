package jpabook.jpashop.domain.order.dto;

import jpabook.jpashop.domain.order.OrderItem;

public record OrderItemDto(String itemName,
                           int orderPrice,
                           int count) {
    public static OrderItemDto from(OrderItem orderItem){
        return new OrderItemDto(
                orderItem.getItem().getName(),
                orderItem.getOrderPrice(),
                orderItem.getCount()
        );

    }
}

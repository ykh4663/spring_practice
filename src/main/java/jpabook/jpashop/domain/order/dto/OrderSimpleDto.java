package jpabook.jpashop.domain.order.dto;

import jpabook.jpashop.domain.common.Address;
import jpabook.jpashop.domain.order.Order;
import jpabook.jpashop.domain.order.OrderStatus;

import java.time.LocalDateTime;

public record OrderSimpleDto(Long orderId,
                             String name,
                             LocalDateTime orderDate,
                             OrderStatus orderStatus,
                             Address address) {

    public static OrderSimpleDto from(Order order){
        return new OrderSimpleDto(
                order.getId(),
                order.getMember().getName(),
                order.getOrderDate(),
                order.getStatus(),
                order.getDelivery().getAddress()
        );
    }
}

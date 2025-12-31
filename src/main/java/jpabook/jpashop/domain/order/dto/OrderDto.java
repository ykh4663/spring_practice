package jpabook.jpashop.domain.order.dto;

import jpabook.jpashop.domain.common.Address;
import jpabook.jpashop.domain.order.Order;
import jpabook.jpashop.domain.order.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(Long orderId,
                       String name,
                       LocalDateTime orderDate,
                       OrderStatus orderStatus,
                       Address address,

                       List<OrderItemDto> orderItems
                       ) {
    public static OrderDto from(Order order) {
        return new OrderDto(
                order.getId(),
                order.getMember().getName(),
                order.getOrderDate(),
                order.getStatus(),
                order.getDelivery().getAddress(),
                order.getOrderItems().stream()
                        .map(OrderItemDto::from)
                        .toList()
        );

    }
}

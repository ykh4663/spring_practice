package jpabook.jpashop.domain.order.dto;

public record CreateOrderItemRequest(Long itemId, int count) {
}

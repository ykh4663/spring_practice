package jpabook.jpashop.domain.order.dto;

import jpabook.jpashop.domain.order.OrderStatus;

public record OrderSearch(String memberName, OrderStatus orderStatus) {

}

package jpabook.jpashop.domain.order.repository;

import jpabook.jpashop.domain.order.Order;
import jpabook.jpashop.domain.order.dto.OrderSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomOrderRepository {
    Page<Order> search(OrderSearch orderSearch, Pageable pageable);

}

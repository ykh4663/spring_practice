package jpabook.jpashop.domain.order.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.common.Address;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.domain.order.Order;
import jpabook.jpashop.domain.order.OrderStatus;
import jpabook.jpashop.domain.order.dto.OrderItemDto;
import jpabook.jpashop.domain.order.repository.OrderRepository;
import jpabook.jpashop.global.error.ApplicationException;

import jpabook.jpashop.global.error.ItemErrorCode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static jpabook.jpashop.global.error.OrderErrorCode.NOT_FOUND_ORDER;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {
    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;
    Member member;
    Book book;
    @BeforeEach
    public void setUp() throws Exception{
        member = Member.builder().name("회원1").address(new Address("서울", "강가", "123-23")).build();
        em.persist(member);

        book = Book.builder().name("시골 JPA").price(10000).stockQuantity(10).author("김영한").isbn("123-4567-890").build();
        em.persist(book);


    }


    @Test
    public void 상품주문() throws Exception{
        //given
        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), List.of(new OrderItemDto(book.getId(), orderCount)));


        //then
        Order getOrder = orderRepository.findById(orderId).orElseThrow(()->new ApplicationException(NOT_FOUND_ORDER));
        assertThat(orderId).isEqualTo(getOrder.getId());
        assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(getOrder.getOrderItems().size()).isEqualTo(1);
        assertThat(getOrder.getTotalPrice()).isEqualTo(book.getPrice() * orderCount);
        assertThat(book.getStockQuantity()).isEqualTo(8);


    }
    @Test
    public void 상품주문_재고수량초과() throws Exception{
        //given
        int orderCount = 11;

        //when

        assertThatThrownBy(()->orderService.order(member.getId(), List.of(new OrderItemDto(book.getId(), orderCount))))
                .isInstanceOf(ApplicationException.class)
                .extracting("errorCode")
                .isEqualTo(ItemErrorCode.NOT_ENOUGH_STOCK);
    }
    @Test
    public void 주문취소() throws Exception{
        //given
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), List.of(new OrderItemDto(book.getId(), orderCount)));

        //when
        orderService.cancelOrder(orderId);

        //then
        assertThat(book.getStockQuantity()).isEqualTo(10);
    }


}
package jpabook.jpashop;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.common.Address;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.domain.order.Delivery;
import jpabook.jpashop.domain.order.Order;
import jpabook.jpashop.domain.order.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }
    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;

        public void dbInit1(){
            Member member = Member.builder()
                    .name("userA")
                    .address(new Address("서울", "강가", "123-123"))
                    .build();
            em.persist(member);

            Book book1 = Book.builder().name("JPA1 BOOK").price(10000).stockQuantity(100).build();
            em.persist(book1);

            Book book2 = Book.builder().name("JPA2 BOOK").price(20000).stockQuantity(100).build();
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = Delivery.builder().address(member.getAddress()).build();
            Order order = Order.createOrder(member, delivery, List.of(orderItem1, orderItem2));
            em.persist(order);
        }
        public void dbInit2(){
            Member member = Member.builder()
                    .name("userB")
                    .address(new Address("부산", "진주", "321-456"))
                    .build();
            em.persist(member);

            Book book1 = Book.builder().name("SPRING1 BOOK").price(20000).stockQuantity(200).build();
            em.persist(book1);

            Book book2 = Book.builder().name("SPRING2 BOOK").price(40000).stockQuantity(300).build();
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);

            Delivery delivery = Delivery.builder().address(member.getAddress()).build();
            Order order = Order.createOrder(member, delivery, List.of(orderItem1, orderItem2));
            em.persist(order);
        }

    }




}

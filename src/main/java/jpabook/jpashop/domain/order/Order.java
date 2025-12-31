package jpabook.jpashop.domain.order;

import jakarta.persistence.*;
import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.global.error.ApplicationException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;

import static jpabook.jpashop.global.error.OrderErrorCode.CANNOT_CANCEL_ORDER;

@Table(name = "orders")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public void changeMember(Member member){
        this.member = member;
        member.addOrder(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.changeOrder(this);
    }

    public void changeDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.changeOrder(this);
    }

    //===생성 메서드===//
    public static Order createOrder(Member member, Delivery delivery, List<OrderItem> orderItems){
        Order order = Order.builder().orderDate(LocalDateTime.now()).status(OrderStatus.ORDER).build();
        order.changeMember(member);
        order.changeDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        return order;

    }

    //===비즈니스 로직===//
    /**
     * 주문 취소
     */

    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw new ApplicationException(CANNOT_CANCEL_ORDER);
        }

        this.status = OrderStatus.CANCEL;
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    /**
     * 전체 주문 가격 조회
     * @return
     */
    public int getTotalPrice(){
        int totalPrice = 0;

        totalPrice = orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
        return totalPrice;
    }



}

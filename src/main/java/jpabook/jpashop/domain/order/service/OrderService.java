package jpabook.jpashop.domain.order.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.item.repository.ItemRepository;
import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.domain.member.repository.MemberRepository;
import jpabook.jpashop.domain.order.Delivery;
import jpabook.jpashop.domain.order.Order;
import jpabook.jpashop.domain.order.OrderItem;
import jpabook.jpashop.domain.order.dto.OrderItemDto;
import jpabook.jpashop.domain.order.repository.OrderRepository;
import jpabook.jpashop.global.error.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static jpabook.jpashop.global.error.ItemErrorCode.NOT_FOUND_ITEM;
import static jpabook.jpashop.global.error.MemberErrorCode.NOT_FOUND_MEMBER;
import static jpabook.jpashop.global.error.OrderErrorCode.NOT_FOUND_ORDER;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    //주문
    @Transactional//회원 선택, 상품 선택 하면 각 아이디들이 넘어오는 것임-> 화면에서 보고 싶은 정보들 뽑아오기
    public Long order(Long memberId, List<OrderItemDto> itemDtos){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ApplicationException(NOT_FOUND_MEMBER));
        Delivery delivery = Delivery.builder().address(member.getAddress()).build();

        List<OrderItem> orderItems = getOrderItems(itemDtos);
        Order order = Order.createOrder(member, delivery, orderItems);

        orderRepository.save(order);
        return order.getId();


    }


    //취소
    @Transactional
    public void cancelOrder(Long orderId){
        //주문 엔티티 조회
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ApplicationException(NOT_FOUND_ORDER));
        //주문 취소
        order.cancel();
    }

    //검색 querydsl로 변경 예정
//    List<Order>findOrders(){
//        return orderRepository.findAll(orderSearch);
//    }

    private List<OrderItem> getOrderItems(List<OrderItemDto> itemDtos) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDto itemDto : itemDtos) {
            Item item = itemRepository.findById(itemDto.itemId()).orElseThrow(() -> new ApplicationException(NOT_FOUND_ITEM));
            OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), itemDto.count());
            orderItems.add(orderItem);
        }
        return orderItems;
    }
}

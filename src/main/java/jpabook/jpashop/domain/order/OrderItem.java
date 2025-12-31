package jpabook.jpashop.domain.order;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "order_item")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    int orderPrice;
    int count;

    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = OrderItem.builder().orderPrice(orderPrice).count(count).item(item).build();
        item.removeStock(count);
        return orderItem;

    }
    public void changeOrder(Order order){
        this.order = order;
    }

    //=== 비즈니스 로직 ===//
    public void cancel() {
        this.item.addStock(count);
    }

    //==조회 로직==//
    /**
     * 전체 주분 가격 조회
     */
    public int getTotalPrice() {
        return orderPrice * count;
    }
}

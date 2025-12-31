package jpabook.jpashop.domain.order.repository;

import jpabook.jpashop.domain.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    //@Query("select o from Order o join fetch o.member m join fetch o.delivery d")
    @EntityGraph(attributePaths = {"member", "delivery"})
    @Query("select o from Order o")
    List<Order> findAllWithMemberDelivery();


    @EntityGraph(attributePaths = {"member", "delivery", "orderItems", "orderItems.item"})
    @Query("select o from Order o")
    List<Order> findAllWithItem();
    @EntityGraph(attributePaths = {"member", "delivery"})
    @Query("select o from Order o")
    Page<Order> findAllWithMemberDelivery(Pageable pageable);
}

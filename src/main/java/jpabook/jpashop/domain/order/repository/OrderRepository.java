package jpabook.jpashop.domain.order.repository;

import jpabook.jpashop.domain.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, CustomOrderRepository {

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

//기본적으로 EntityGraph는 left join만 지원하기 때문에 다른 조인을 할 필요가 있을 때는 JPQL로 직접 쿼리 작성
    @Query("select o from Order o join fetch o.member m join fetch o.delivery d")
    Page<Order> findJpqlWithMemberDelivery(Pageable pageable);
}

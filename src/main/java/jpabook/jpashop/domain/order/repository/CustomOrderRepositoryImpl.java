package jpabook.jpashop.domain.order.repository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.domain.order.Order;
import jpabook.jpashop.domain.order.OrderStatus;
import jpabook.jpashop.domain.order.dto.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.List;

import static jpabook.jpashop.domain.member.QMember.member;
import static jpabook.jpashop.domain.order.QDelivery.delivery;
import static jpabook.jpashop.domain.order.QOrder.order;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class CustomOrderRepositoryImpl implements CustomOrderRepository {

    private final JPAQueryFactory queryFactory;
    @Override
    public Page<Order> search(OrderSearch orderSearch, Pageable pageable) {
        List<Order> content = queryFactory.selectFrom(order)
                .join(order.member, member).fetchJoin()
                .join(order.delivery, delivery).fetchJoin()
                .where(memberNameEq(orderSearch.memberName()),
                        orderStatusEq(orderSearch.orderStatus()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = queryFactory.select(order.count()).from(order)
                .join(order.member, member)
                .where(memberNameEq(orderSearch.memberName()),
                        orderStatusEq(orderSearch.orderStatus()))
                .fetchOne();

        count = count != null ? count : 0L;
        return new PageImpl<>(content, pageable, count);
    }

    private BooleanExpression orderStatusEq(OrderStatus orderStatus) {
        return orderStatus != null ? order.status.eq(orderStatus) : null;

    }

    private BooleanExpression memberNameEq(String memberName) {
        return hasText(memberName) ? member.name.eq(memberName) : null;
    }



}

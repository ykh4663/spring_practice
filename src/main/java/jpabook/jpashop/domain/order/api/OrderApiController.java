package jpabook.jpashop.domain.order.api;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.order.Order;
import jpabook.jpashop.domain.order.dto.OrderDto;
import jpabook.jpashop.domain.order.dto.OrderSearch;
import jpabook.jpashop.domain.order.dto.OrderSimpleDto;
import jpabook.jpashop.domain.order.repository.OrderRepository;
import jpabook.jpashop.global.common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
//    @GetMapping("/api/orders")
//    public ResponseEntity<CommonResponse<List<OrderSimpleDto>>> findOrders() {
//        List<Order> orders = orderRepository.findAll();
//        List<OrderSimpleDto> result = orders.stream()
//                .map(OrderSimpleDto::from)
//                .toList();
//        return ResponseEntity.ok(CommonResponse.createSuccess(result));
//    }

    @GetMapping("/api/simple/orders")
    public ResponseEntity<CommonResponse<List<OrderSimpleDto>>> findSimpleOrders() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<OrderSimpleDto> result = orders.stream()
                .map(OrderSimpleDto::from)
                .toList();
        return ResponseEntity.ok(CommonResponse.createSuccess(result));
    }

    @GetMapping("/api/fetch/orders")
    public ResponseEntity<CommonResponse<List<OrderDto>>> findOrders() {
        List<Order> orders = orderRepository.findAllWithItem();
        List<OrderDto> result = orders.stream()
                .map(OrderDto::from)
                .toList();
        return ResponseEntity.ok(CommonResponse.createSuccess(result));
    }

    @GetMapping("/api/page/orders")
    public ResponseEntity<CommonResponse<Page<OrderDto>>> findOrders_Page(
            @RequestParam(value = "offset", defaultValue = "0")int offset,
            @RequestParam(value = "limit", defaultValue = "100")int limit
    ) {
        PageRequest pageRequest = PageRequest.of(offset, limit, Sort.Direction.DESC, "member.name");

        Page<Order> orders = orderRepository.findAllWithMemberDelivery(pageRequest);

        Page<OrderDto> result = orders.map(OrderDto::from);
        return ResponseEntity.ok(CommonResponse.createSuccess(result));
    }

    @GetMapping("/api/dynamicpage/orders")
    public ResponseEntity<CommonResponse<Page<OrderDto>>> findDynamicOrders_Page(
            @RequestParam(value = "offset", defaultValue = "0")int offset,
            @RequestParam(value = "limit", defaultValue = "100")int limit,
            @ModelAttribute OrderSearch orderSearch
            ) {
        PageRequest pageRequest = PageRequest.of(offset, limit);

        Page<Order> orders = orderRepository.search(orderSearch, pageRequest);

        Page<OrderDto> result = orders.map(OrderDto::from);
        return ResponseEntity.ok(CommonResponse.createSuccess(result));
    }


}

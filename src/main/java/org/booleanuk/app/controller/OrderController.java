package org.booleanuk.app.controller;

import java.util.List;

import org.booleanuk.app.service.OrderService;
import org.booleanuk.app.model.Order;
import org.booleanuk.app.dto.OrderDto;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping ("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping 
    public List<OrderDto> getAllOrders() {
        return orderService.getAllOrders()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @GetMapping ("/{id}")
    public OrderDto getOrder(@PathVariable Long id) {
        return toDto(orderService.getOrder(id));
    }

    @PostMapping
    public OrderDto createOrder(@RequestBody OrderDto dto) {
        return toDto(orderService.createOrder(dto));
    }

    @PutMapping("/{id}")
    public OrderDto updateOrder(@PathVariable Long id, @RequestBody OrderDto dto) {
        return toDto(orderService.updateOrder(id, dto));
    }

    @DeleteMapping ("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

    private OrderDto toDto(Order order) {
        return new OrderDto(
            order.getId(),
            order.getCreatedAt(),
            order.getTotalAmount(),
            order.getCustomer().getId(),
            order.getProducts()
                    .stream()
                    .map(product -> product.getId())
                    .toList()
        );
    }
}

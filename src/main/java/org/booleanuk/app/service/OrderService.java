package org.booleanuk.app.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.booleanuk.app.dto.OrderDto;
import org.booleanuk.app.model.Order;
import org.booleanuk.app.model.Customer;
import org.booleanuk.app.model.Product;
import org.booleanuk.app.repository.CustomerRepository;
import org.booleanuk.app.repository.OrderRepository;
import org.booleanuk.app.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service 
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public Order createOrder(OrderDto dto) {
        Customer customer = customerRepository.findById(dto.customerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        List<Product> products = productRepository.findAllById(dto.productIds());
        BigDecimal totalAmount = products.stream()
            .map(Product::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        Order order = new Order();
        order.setCustomer(customer);
        order.setProducts(products);
        order.setCreatedAt(LocalDateTime.now());
        order.setTotalAmount(totalAmount);
        return orderRepository.save(order);
    }

    public Order updateOrder(Long id, OrderDto dto) {
        Order order = getOrder(id);
        Customer customer = customerRepository.findById(dto.customerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        List<Product> products = productRepository.findAllById(dto.productIds());
        BigDecimal totalAmount = products.stream()
            .map(Product::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setCustomer(customer);
        order.setProducts(products);
        order.setTotalAmount(totalAmount);
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}

package org.booleanuk.app.service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import org.booleanuk.app.dto.OrderValueDto;
import org.booleanuk.app.dto.SalesDto;
import org.booleanuk.app.dto.ValueDto;
import org.booleanuk.app.model.Product;
import org.booleanuk.app.repository.CustomerRepository;
import org.booleanuk.app.repository.OrderRepository;
import org.booleanuk.app.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service 
public class ReportService {
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public ReportService(
        CustomerRepository customerRepository,
        ProductRepository productRepository,
        OrderRepository orderRepository) {
            this.customerRepository = customerRepository;
            this.productRepository = productRepository;
            this.orderRepository = orderRepository;
        }
    
    public List<ValueDto> getCustomerValues() {
        return customerRepository.findAll()
            .stream()
            .map(customer -> {
                BigDecimal total = customer.getOrders() == null
                    ? BigDecimal.ZERO : customer.getOrders()
                    .stream()
                    .flatMap(order -> order.getProducts().stream())
                    .map(Product::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add

                    );
                return new ValueDto(
                    customer.getId(),
                    customer.getName(),
                    total
                );
            })
            .toList();
    }

    public List<SalesDto> getProductSales() {
        return  productRepository.findAll()
            .stream()
            .map(product -> {
                long sold = orderRepository.findAll()
                    .stream()
                    .flatMap(order -> order.getProducts().stream())
                    .filter(p -> p.getId().equals(product.getId()))
                    .count();

                return new SalesDto(
                    product.getId(),
                    product.getName(),
                    sold
                );
            })
            .toList();
    }

    public List<OrderValueDto> getOrdersByValue() {
         return  orderRepository.findAll()
            .stream()
            .map(order -> {
                BigDecimal total = order.getProducts()
                    .stream()
                    .map(Product::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add
                    );

                return new OrderValueDto(
                    order.getId(),
                    total
                );
            })
            .sorted(
                Comparator.comparing(
                    OrderValueDto::totalValue
                ).reversed()
            )
            .toList();
    }
}

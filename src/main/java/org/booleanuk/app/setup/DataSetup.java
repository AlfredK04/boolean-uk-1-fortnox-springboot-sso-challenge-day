package org.booleanuk.app.setup;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.booleanuk.app.model.Customer;
import org.booleanuk.app.model.Order;
import org.booleanuk.app.model.Product;
import org.booleanuk.app.repository.CustomerRepository;
import org.booleanuk.app.repository.OrderRepository;
import org.booleanuk.app.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration 
public class DataSetup {
    @Bean
    CommandLineRunner initDatabase(CustomerRepository customerRepository, ProductRepository productRepository, OrderRepository orderRepository) {
        return args -> {
            if (productRepository.count() > 0) {
                return;
            }
            Product laptop = productRepository.save(new Product(null, "Laptop", new BigDecimal("999.99")));
            Product mouse = productRepository.save(new Product(null, "Mouse", new BigDecimal("19.99")));
            Product keyboard = productRepository.save(new Product(null, "Keyboard", new BigDecimal("49.99")));
            Product monitor = productRepository.save(new Product(null, "Monitor", new BigDecimal("199.99")));
            Product headset = productRepository.save(new Product(null, "Headset", new BigDecimal("79.99")));

            Customer john = customerRepository.save(new Customer(null, "John Doe", "john@demo.com", null));
            Customer giovanni = customerRepository.save(new Customer(null, "Giovanni", "guybrush@wow.dev", null));
            Customer alfred = customerRepository.save(new Customer(null, "Alfred", "alfred@wow.dev", null));

            Order order1 = new Order();
            order1.setCustomer(alfred);
            order1.setProducts(List.of(monitor, mouse));
            order1.setCreatedAt(LocalDateTime.now());
            order1.setTotalAmount(
                order1.getProducts()
                    .stream().map(Product::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)  
            );

            
            Order order2 = new Order();
            order2.setCustomer(john);
            order2.setProducts(List.of(keyboard, headset));
            order2.setCreatedAt(LocalDateTime.now());
            order2.setTotalAmount(
                order2.getProducts()
                    .stream().map(Product::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)  
            );

            
            Order order3 = new Order();
            order3.setCustomer(giovanni);
            order3.setProducts(List.of(laptop, mouse, keyboard));
            order3.setCreatedAt(LocalDateTime.now());
            order3.setTotalAmount(
                order3.getProducts()
                    .stream().map(Product::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)  
            );

            orderRepository.saveAll(List.of(order1, order2, order3));
        };
    }
}

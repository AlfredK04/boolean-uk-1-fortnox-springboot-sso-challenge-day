package org.booleanuk.app.model;

import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Entity 
@Table (name = "orders")
public class Order {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;

    private BigDecimal totalAmount;

    @ManyToOne 
    private Customer customer;

    @ManyToMany 
    @JoinTable (
        name = "order_products",
        joinColumns = @JoinColumn (name = "order_id"),
        inverseJoinColumns = @JoinColumn (name = "product_id")
    )
    private List<Product> products;
}

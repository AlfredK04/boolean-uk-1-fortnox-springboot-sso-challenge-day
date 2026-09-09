package org.booleanuk.app.controller;

import java.util.List;

import org.booleanuk.app.service.ProductService;
import org.booleanuk.app.dto.ProductDto;
import org.booleanuk.app.model.Product;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping ("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping 
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @GetMapping ("/{id}")
    public ProductDto getProduct(@PathVariable Long id) {
        return toDto(productService.getProduct(id));
    }

    @PostMapping 
    public ProductDto createProduct(@RequestBody ProductDto dto){
        Product product = toEntity(dto);
        return toDto(productService.createProduct(product));
    }

    @PutMapping ("/{id}")
    public ProductDto updateProduct(@PathVariable Long id, @RequestBody ProductDto dto) {
        return toDto(productService.updateProduct(id, toEntity(dto)));
    }

    @DeleteMapping ("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    private ProductDto toDto(Product product) {
        return new ProductDto(
            product.getId(),
            product.getName(),
            product.getPrice()
        );
    }

    private Product toEntity(ProductDto dto) {
        Product product = new Product();
        product.setName(dto.name());
        product.setPrice(dto.price());
        return product;
    }
}

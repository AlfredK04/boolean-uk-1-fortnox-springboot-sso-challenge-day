package org.booleanuk.app.controller;

import java.util.List;

import org.booleanuk.app.service.CustomerService;
import org.booleanuk.app.model.Customer;
import org.booleanuk.app.dto.CustomerDto;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping ("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping 
    public List<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @GetMapping ("/{id}")
    public CustomerDto getCustomer(@PathVariable Long id) {
        return toDto(customerService.getCustomer(id));
    }

    @PostMapping 
    public CustomerDto createCustomer(@RequestBody CustomerDto dto) {
        Customer customer = toEntity(dto);
        return toDto(
            customerService.createCustomer(customer)
        );
    }

    @PutMapping ("/{id}")
    public CustomerDto updateCustomer(@PathVariable Long id, @RequestBody CustomerDto dto) {
        return toDto(customerService.updateCustomer(id, toEntity(dto)));
    }

    @DeleteMapping ("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }

    private CustomerDto toDto(Customer customer) {
        return new CustomerDto(
            customer.getId(),
            customer.getName(),
            customer.getEmail()
        );
    }

    private Customer toEntity(CustomerDto dto) {
        Customer customer = new Customer();
        customer.setName(dto.name());
        customer.setEmail(dto.email());
        return customer;
    }
}

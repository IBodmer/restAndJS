package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.CustomerDTO;
import ru.kata.spring.boot_security.demo.models.Customer;
import ru.kata.spring.boot_security.demo.service.CustomerService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AdminController {
    private final CustomerService customerService;

    public AdminController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping("/users")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findAllCustomers());
    }

    @PostMapping("/new")
    public ResponseEntity<CustomerDTO> saveCustomer(@RequestBody CustomerDTO customerDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.saveCustomer(customerDTO));
    }

    @PatchMapping("/user/update/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.updateCustomer(id, customerDTO));
    }


    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("user/{id}")
    public ResponseEntity<CustomerDTO> getUser(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getById(id));

    }

    @GetMapping("/user")
    public ResponseEntity<Customer> getCustomerForFront(Principal principal) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findByUsername("pewpew308@gmail.com"));
    }

}

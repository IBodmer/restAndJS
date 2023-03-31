package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.dto.CustomerDTO;
import ru.kata.spring.boot_security.demo.models.Customer;

import java.util.List;

public interface CustomerService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    List<CustomerDTO> findAllCustomers();

    Customer findByUsername(String email);

    void update(Long id, Customer customer);
    void deleteCustomer(Long id);
}

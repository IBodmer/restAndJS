package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dto.CustomerDTO;
import ru.kata.spring.boot_security.demo.exeptions.CustomerNotFoundException;
import ru.kata.spring.boot_security.demo.models.Customer;
import ru.kata.spring.boot_security.demo.repo.CustomerRepo;
import ru.kata.spring.boot_security.demo.repo.RoleRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepo customerRepo;
    private final RoleRepo repo;

    public CustomerServiceImpl(CustomerRepo customerRepo, RoleRepo repo) {
        this.customerRepo = customerRepo;
        this.repo = repo;
    }

    @Override
    public List<CustomerDTO> findAllCustomers() {
        return customerRepo.findAll().stream().map(Customer::toCustomerDTO).collect(Collectors.toList());
    }

    public Customer findByUsername(String email) {
        return customerRepo.findByEmail(email);
    }

    @Transactional
    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = Customer.builder()
                .firstname(customerDTO.getFirstname())
                .lastname(customerDTO.getLastname())
                .age(customerDTO.getAge())
                .email(customerDTO.getEmail())
                .roles(customerDTO.getRoles().stream().map(x -> repo.findByRole(x.getRole())).collect(Collectors.toSet()))
                .build();
        customerRepo.save(customer);
        return customerDTO;
    }

    @Transactional
    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer customer = customerRepo
                .findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Пользователя: " + customerDTO.getEmail() + " не найдено"));
        customer.setFirstname(customerDTO.getFirstname());
        customer.setLastname(customerDTO.getLastname());
        customer.setEmail(customerDTO.getEmail());
        customer.setAge(customerDTO.getAge());
        customer.setRoles(customerDTO.getRoles().stream().map(x -> repo.findByRole(x.getRole())).collect(Collectors.toSet()));
        customerRepo.save(customer);
        return customerDTO;
    }

    @Transactional
    @Override
    public void deleteCustomer(Long id) {
        customerRepo.deleteById(id);
    }

}

package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dto.CustomerDTO;
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

    @Transactional
    public void update(Long id, Customer customer) {
        Customer customer1 = customerRepo.findById(id).orElseThrow(() -> new RuntimeException("ne po plany"));
        customer1.setFirstname(customer.getFirstname());
        customer1.setLastname(customer.getLastname());
        customer1.setEmail(customer.getEmail());
        customer1.setAge(customer.getAge());
        customer1.setRoles(customer.getRoles());
        customerRepo.save(customer1);
    }

    @Transactional
    @Override
    public void deleteCustomer(Long id) {
        customerRepo.deleteById(id);
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
                .roles(customerDTO.getRoles().stream().map(x->repo.findByRole(x.getRole())).collect(Collectors.toSet()))
                .build();
        customerRepo.save(customer);
        return customerDTO;
    }

    @Override
    public List<CustomerDTO> findAllCustomers() {
        return customerRepo.findAll().stream().map(Customer::toCustomerDTO).collect(Collectors.toList());
    }

}

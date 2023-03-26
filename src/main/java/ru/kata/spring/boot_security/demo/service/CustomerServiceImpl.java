package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.Customer;
import ru.kata.spring.boot_security.demo.repo.CustomerRepo;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepo customerRepo;

    public CustomerServiceImpl(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    public void update(Long id, Customer customer) {
        Customer customer1 = customerRepo.findById(id).orElseThrow(() -> new RuntimeException("ne po plany"));
        customer1.setFirstname(customer.getFirstname());
        customer1.setLastname(customer.getLastname());
        customer1.setEmail(customer.getEmail());
        customer1.setAge(customer.getAge());
        customer1.setRoles(customer.getRoles());
        customerRepo.save(customer1);
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepo.deleteById(id);
    }

    public Customer findByUsername(String email) {
        return customerRepo.findByEmail(email);
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

    @Override
    public List<Customer> findAllCustomers() {
        return customerRepo.findAll();
    }
}

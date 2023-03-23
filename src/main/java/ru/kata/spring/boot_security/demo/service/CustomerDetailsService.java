package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.Customer;
import ru.kata.spring.boot_security.demo.repo.CustomerRepo;

import java.util.List;

@Service
public class CustomerDetailsService implements UserDetailsService, CustomerService {
    private final CustomerRepo customerRepo;

    public CustomerDetailsService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    public Customer findByUsername(String username) {
        return customerRepo.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = findByUsername(username);
        if (customer == null)
            throw new UsernameNotFoundException(String.format("Пользователя '%s' не найдено", username));
        return customer;
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

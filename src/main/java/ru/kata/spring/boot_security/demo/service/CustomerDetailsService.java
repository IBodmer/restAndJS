package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.Customer;

@Service
public class CustomerDetailsService implements UserDetailsService {
    private final CustomerService customerService;

    public CustomerDetailsService(CustomerService customerService) {
        this.customerService = customerService;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerService.findByUsername(email);
        if (customer == null)
            throw new UsernameNotFoundException(String.format("Пользователя '%s' не найдено", email));
        return customer;
    }

}

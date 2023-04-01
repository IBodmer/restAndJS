package ru.kata.spring.boot_security.demo.exeptions;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomerNotFoundException extends UsernameNotFoundException {
    public CustomerNotFoundException(String msg) {
        super(msg);
    }
}

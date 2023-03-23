package ru.kata.spring.boot_security.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
    Customer findByUsername(String username);
}

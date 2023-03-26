package ru.kata.spring.boot_security.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
    @Query("Select u from Customer u left join fetch u.roles where u.email=:email")
    Customer findByEmail(String email);
}

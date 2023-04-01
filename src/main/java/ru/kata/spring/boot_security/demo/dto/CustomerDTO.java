package ru.kata.spring.boot_security.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kata.spring.boot_security.demo.models.Customer;
import ru.kata.spring.boot_security.demo.models.Role;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class CustomerDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private Integer age;
    private Set<RoleDTO> roles = new HashSet<>();

    public static CustomerDTO toCustomerDTO(Customer customer) {
        return CustomerDTO.builder()
                .id(customer.getId())
                .firstname(customer.getFirstname())
                .lastname(customer.getLastname())
                .email(customer.getEmail())
                .age(customer.getAge())
                .roles(customer.getRoles().stream().map(Role::toRoleDto).collect(Collectors.toSet()))
                .build();
    }
}

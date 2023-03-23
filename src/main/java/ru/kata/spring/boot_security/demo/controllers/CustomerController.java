package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.models.Customer;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.service.CustomerDetailsService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CustomerController {
    private final CustomerDetailsService customerDetailsService;

    public CustomerController(CustomerDetailsService customerDetailsService) {
        this.customerDetailsService = customerDetailsService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    //    @GetMapping("/")
//    public String startPage() {
//        return "start";
//    }
    @GetMapping("/admin")
    public String getAllUsers(Model model, Principal principal) {
        int isRoleContainsAdmin = 0;
        Customer customer = customerDetailsService.findByUsername(principal.getName());
        List<Role> roles = customer.getRoles();
        List<Role> rolesWithoutAdmin = containsAdmin(roles);
        if (rolesWithoutAdmin.size() != roles.size()) isRoleContainsAdmin = 1;
        model.addAttribute("customer", customer);
        model.addAttribute("isRoleContainsAdmin", isRoleContainsAdmin); //1 это значит админ есть и мы отдаем отфильтрованный список. условимся на том, что админ высшая роль
        model.addAttribute("listRolesWithoutAdmin", rolesWithoutAdmin);
        return "admin";
    }

    public List<Role> containsAdmin(List<Role> someList) {
        List<Role> newList;
        if (someList.stream().map(Role::getRole).collect(Collectors.toList()).contains("ROLE_ADMIN")) {
            newList = someList.stream().filter(r -> !r.getRole().contains("ROLE_ADMIN")).collect(Collectors.toList());
        } else return someList;
        return newList;
    }

}

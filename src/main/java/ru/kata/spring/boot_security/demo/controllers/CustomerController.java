package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Customer;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.repo.RoleRepo;
import ru.kata.spring.boot_security.demo.service.CustomerDetailsService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class CustomerController {
    private final CustomerDetailsService customerDetailsService;
    private final RoleRepo repo;

    public CustomerController(CustomerDetailsService customerDetailsService, RoleRepo repo) {
        this.customerDetailsService = customerDetailsService;
        this.repo = repo;
    }



    //    @GetMapping("/")
//    public String startPage() {
//        return "start";
//    }
    @GetMapping
    public String getAllUsers(Model model, Principal principal) {

        int isRoleContainsAdmin = 0;
        Customer customer = customerDetailsService.findByUsername(principal.getName());
        List<Role> roles = customer.getRoles();
        List<Role> allPossibleRolesWithOutPrefix = repo.findAll().stream().map(x -> x.roleWithOutPrefix(x)).collect(Collectors.toList());

        String rolesWithoutBrackets = roles.stream().map(x -> x.toString().replace("[", "")
                .replace("]", "")).map(x -> x.substring(5)).collect(Collectors.joining(", "));

        List<String> rolesWithoutAdmin = containsAdmin(roles);
        if (rolesWithoutAdmin.size() != roles.size()) isRoleContainsAdmin = 1;
        List<Customer> allCustomers = customerDetailsService.findAllCustomers();
        model.addAttribute("customer", customer);
        model.addAttribute("isRoleContainsAdmin", isRoleContainsAdmin); //1 это значит админ есть и мы отдаем отфильтрованный список. условимся на том, что админ высшая роль
        model.addAttribute("listRolesWithoutAdmin", rolesWithoutAdmin);
        model.addAttribute("allCustomers",allCustomers);
        model.addAttribute("rolesWithoutBrackets",rolesWithoutBrackets);
        model.addAttribute("allPossibleRolesWithOutPrefix", allPossibleRolesWithOutPrefix);
        return "admin";
    }
    @PostMapping("/edit/{id}")
    public String updateCustomer(@PathVariable Long id, @ModelAttribute Customer customer) {
        customerDetailsService.update(id, customer);
        return "redirect:/";
    }

    public List<String> containsAdmin(List<Role> someList) {
        List<String> newList;
        if (someList.stream().map(Role::getRole).collect(Collectors.toList()).contains("ROLE_ADMIN")) {
            newList = someList.stream().filter(r -> !r.getRole().contains("ROLE_ADMIN")).map(x -> x.toString().replace("[", "")
                    .replace("]", "")).map(x -> x.substring(5)).collect(Collectors.toList());
        } else return someList.stream().map(x -> x.toString().replace("[", "")
                .replace("]", "")).map(x -> x.substring(5)).collect(Collectors.toList());
        return newList;
    }

}

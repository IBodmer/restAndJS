package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Customer;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.service.CustomerService;
import ru.kata.spring.boot_security.demo.service.RoleService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class CustomerController {
    private final CustomerService customerService;
    private final RoleService roleService;

    public CustomerController(CustomerService customerService, RoleService roleService) {
        this.customerService = customerService;
        this.roleService = roleService;
    }


    @GetMapping
    public String getAllUsers(Model model, Principal principal) {

        Customer customer = customerService.findByUsername(principal.getName());
        List<Role> allRoles = roleService.findAllRoles();
        List<Customer> allCustomers = customerService.findAllCustomers();
        model.addAttribute("customer", customer);
        model.addAttribute("allCustomers", allCustomers);
        model.addAttribute("allPossibleRoles", allRoles);
        model.addAttribute("emptyUser", new Customer());
        return "admin";
    }

    @PatchMapping("/edit/{id}")
    public String updateCustomer(@ModelAttribute("emptyUser") Customer customer, @PathVariable("id") Long id,
                                 @RequestParam(value = "customerRolesSelector") String[] selectResult) {
        for (String s : selectResult) {
            customer.addRole(roleService.getRoleByName("ROLE_" + s));
        }
        customerService.update(id, customer);
        return "redirect:/admin";
    }
    @DeleteMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/admin";
    }
    @PostMapping("/new")
    public String createUser(@ModelAttribute("emptyUser") Customer customer,
                             @RequestParam(value = "checkedRoles") String[] selectResult) {
        for (String s : selectResult) {
            customer.addRole(roleService.getRoleByName("ROLE_" + s));
        }
        customerService.saveCustomer(customer);
        return "redirect:/admin";
    }


}

package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.service.CustomerService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String getUserPage(Model model, Principal principal) {
        model.addAttribute("customer", customerService.findByUsername(principal.getName()));
        return "/user";
    }
}

package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.CustomerDTO;
import ru.kata.spring.boot_security.demo.service.CustomerService;
import ru.kata.spring.boot_security.demo.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final CustomerService customerService;
    private final RoleService roleService;

    public AdminController(CustomerService customerService, RoleService roleService) {
        this.customerService = customerService;
        this.roleService = roleService;
    }


    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findAllCustomers());
    }
    @PostMapping("/new")
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerDTO customerDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.saveCustomer(customerDTO));
    }

//    @PatchMapping("/edit/{id}")
//    public String updateCustomer() {
//    }

    @DeleteMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/admin";
    }


//    @PostMapping("/new")
//    public String createUser(@ModelAttribute("emptyUser") Customer customer,
//                             @RequestParam(value = "checkedRoles") String[] selectResult) {
//        for (String s : selectResult) {
//            customer.addRole(roleService.getRoleByName("ROLE_" + s));
//        }
//        customerService.saveCustomer(customer);
//        return "redirect:/admin";
//    }

}

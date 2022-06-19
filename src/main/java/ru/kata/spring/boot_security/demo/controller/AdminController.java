package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.MyUserDetailsService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Set;

@Controller
public class AdminController {

    private final MyUserDetailsService myUserDetailsService;


    @Autowired
    AdminController(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;

    }

    @GetMapping("/admin/info")
    public String goHome(Principal principal, Model model){
        User user = myUserDetailsService.findByUserName(principal.getName());
        model.addAttribute("activeUser", user);

        return "admin_info";
    }
}

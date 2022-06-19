package ru.kata.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.MyUserDetailsService;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MyRestController2 {
    private final RoleRepository roleRepository;


    private final MyUserDetailsService userService;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    @GetMapping("/rest/principal")
    public User getPrincipalInfo(Principal principal) {
        return userService.findByUserName(principal.getName());
    }

    @GetMapping("/rest")
    public List<User> findAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/rest/{id}")
    public User findOneUser(@PathVariable long id) {
        User user = userService.findUserById(id);
        return user;
    }

    @PostMapping("/rest")
    public User addNewUser(@RequestBody User user) {

        roleRepository.saveAll(user.getRoles());

        userService.save(user);

        return user;
    }

    @PutMapping("/rest/{id}")
    public User updateUser(@RequestBody User user, @PathVariable("id") long id) {
        roleRepository.saveAll(user.getRoles());
        if (user.getPassword() == null ||
                user.getPassword().equals("") || user.getPassword().equals(userService.findUserById((long) id).getPassword())) {
            user.setPassword(userService.findUserById(id).getPassword());
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }

        userService.saveAndFlush(user);
        return user;
    }

    @DeleteMapping("/rest/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteById(id);
    }
}
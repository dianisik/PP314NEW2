package ru.kata.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.MyUserDetailsService;
import ru.kata.spring.boot_security.demo.service.UpdateUserService;

import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class AdminRestController {
    private final RoleRepository roleRepository;


    private final MyUserDetailsService userService;

    private final UpdateUserService updateUserService;


    @GetMapping("/api/principal")
    public User getPrincipalInfo(Principal principal) {
        return userService.findByUserName(principal.getName());
    }

    @GetMapping("/api/{id}")
    public User findOneUser(@PathVariable long id) {
        User user = userService.findUserById(id);
        return user;
    }

    @PostMapping("/api")
    public ResponseEntity addNewUser(@RequestBody User user) {
        user.setRoles(userService.getRoles(userService.rolesToId(user.getRoles())));
        userService.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(location).body(user);
    }
    @GetMapping("/api")
    public ResponseEntity<List<User>> findAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }
    @PutMapping("/api/{id}")
    public User updateUser(@RequestBody User user, @PathVariable("id") long id) {
        //roleRepository.saveAll(user.getRoles());
        user.setRoles(userService.getRoles(userService.rolesToId(user.getRoles())));
        updateUserService.setPassword(user, id);
        userService.saveAndFlush(user);
        return user;
    }
    @DeleteMapping("/api/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteById(id);
    }
}

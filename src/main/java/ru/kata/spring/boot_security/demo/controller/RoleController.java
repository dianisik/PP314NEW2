package ru.kata.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.MyUserDetailsService;
import ru.kata.spring.boot_security.demo.service.UpdateUserService;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoleController {
    private final RoleRepository roleRepository;


    private final MyUserDetailsService userService;

    private final UpdateUserService updateUserService;


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
    public ResponseEntity addNewUser(@RequestBody User user) {

        roleRepository.saveAll(user.getRoles());

        userService.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(location).body(user);
    }

    @PutMapping("/rest/{id}")
    public User updateUser(@RequestBody User user, @PathVariable("id") long id) {
        roleRepository.saveAll(user.getRoles());
        updateUserService.setPassword(user, id);
        userService.saveAndFlush(user);
        return user;
    }

    @DeleteMapping("/rest/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteById(id);
    }
}

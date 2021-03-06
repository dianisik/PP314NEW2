package ru.kata.spring.boot_security.demo.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.*;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService, UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    public MyUserDetailsService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public Set<Role> getRoles (ArrayList<Long> roles){
        return roleRepository.findByIdIn(roles);
    }
    public User findByUserName(String userName){
        return userRepository.findByName(userName);    }

    @Override
    public UserDetails loadUserByUsername(String name)
            throws UsernameNotFoundException {
        return userRepository.findByName(name);

    }
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public void saveAndFlush(User user) {
        userRepository.saveAndFlush(user);
    }

    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String name) {
        return null;
    }
   @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    @Override
    public void saveOrUpdate(User user) {

    }
    @Override
    public void delete(Long id) {

    }
    public ArrayList <Long> rolesToId (Set<Role> roles){
        ArrayList<Long> rolesId = new ArrayList<>();
        for (Role role:roles){
            rolesId.add(Long.valueOf(role.getName()));
        }
        return rolesId;
    }
}
package com.mybook.api.services.impl;

import com.mybook.api.models.Role;
import com.mybook.api.models.Status;
import com.mybook.api.models.User;
import com.mybook.api.repositories.RoleRepository;
import com.mybook.api.repositories.UserRepository;
import com.mybook.api.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User register(User user) {
        List<Role> roles = new ArrayList<>();
        if (userRepository.count()>0) {
            roles.add(roleRepository.findRoleByName("ROLE_USER"));
        }else {
            roles = roleRepository.findAll();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roles);
        user.setStatus(Status.ACTIVE);
        User registeredUser = userRepository.save(user);
        return registeredUser;
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        return result;
    }

    @Override
    public User findByEmail(String email) {
        User result = userRepository.findUserByEmail(email);
        return result;
    }

    @Override
    public User findById(long id) {
        User result = userRepository.findById(id).orElse(null);
        if (result == null)
            return null;
        return result;
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }
}

package com.mybook.api.controllers;

import com.mybook.api.dto.RegistrationDTO;
import com.mybook.api.models.User;
import com.mybook.api.repositories.UserRepository;
import com.mybook.api.services.UserService;
import com.mybook.api.services.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class Rest {

    private UserRepository userRepository;
    private UserServiceImpl userService;

    @PostMapping("/api/auth/register")
    public void register(@RequestBody User candidate) {
        System.out.println(candidate);
        if (userService.findByEmail(candidate.getEmail()) != null) {
            System.out.println("Errore");
        }
        userService.register(candidate);
    }
    @PostMapping("/api/auth/login")
    public String login(){

        return "yes";
    }
}

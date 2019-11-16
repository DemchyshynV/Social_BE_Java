package com.mybook.api.controllers;

import com.mybook.api.models.User;
import com.mybook.api.repositories.UserRepository;
import com.mybook.api.services.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class AuthController {

    private UserServiceImpl userService;

    @PostMapping("/api/auth/register")
    public ResponseEntity register(@RequestBody User candidate) {
//        System.out.println(candidate);
        HttpHeaders httpHeaders = new HttpHeaders();
        if (userService.findByEmail(candidate.getEmail()) != null) {
            httpHeaders.add("email", "false");
            return new ResponseEntity(httpHeaders, HttpStatus.CONFLICT);
        }
        userService.register(candidate);
        httpHeaders.add("email", "true");
        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }
}

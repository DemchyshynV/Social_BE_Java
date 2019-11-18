package com.mybook.api.controllers;

import com.mybook.api.dto.SiteLayoutDTO;
import com.mybook.api.models.User;
import com.mybook.api.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {
    private UserRepository userRepository;

    @GetMapping("/api/profile")
    public SiteLayoutDTO profile() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User userByEmail = userRepository.findUserByEmail(email);
        SiteLayoutDTO profile = SiteLayoutDTO.builder()
                .avatar(userByEmail.getAvatar().getAvatar())
                .name(userByEmail.getName())
                .surname(userByEmail.getSurname())
                .build();
        return profile;
    }
}

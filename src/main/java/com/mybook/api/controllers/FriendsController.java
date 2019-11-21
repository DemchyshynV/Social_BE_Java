package com.mybook.api.controllers;

import com.mybook.api.dto.FriendsDTO;
import com.mybook.api.models.User;
import com.mybook.api.services.impl.FriendServiceImpl;
import com.mybook.api.services.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

@RestController
@AllArgsConstructor
public class FriendsController {
    private FriendServiceImpl friendService;
    private UserServiceImpl userService;

    @GetMapping("/api/friends/find")
    public List<FriendsDTO> findAll() {
        return friendService.findFriends();
    }
    @GetMapping("/api/friends/avatar/{id}")
    public ResponseEntity<Resource> getAvatar(@PathVariable long id) {
        String filePath = userService.findById(id).getProfile().getAvatar();
        File file = new File(filePath);
        Resource resource = new FileSystemResource(file);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }
}

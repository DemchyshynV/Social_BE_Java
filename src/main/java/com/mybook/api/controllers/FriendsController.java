package com.mybook.api.controllers;

import com.mybook.api.dto.FriendsDTO;
import com.mybook.api.models.User;
import com.mybook.api.services.impl.FriendServiceImpl;
import com.mybook.api.services.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

@RestController
@AllArgsConstructor
public class FriendsController {
    private FriendServiceImpl friendService;
    private UserServiceImpl userService;

    @GetMapping("/api/friends/findFriends")
    public List<FriendsDTO> findFriends() {
        return friendService.findFriends();

    }

    @GetMapping("/api/friends/myRequests")
    public List<FriendsDTO> myRequests() {
        return friendService.myRequests();
    }

    @GetMapping("/api/friends/avatar/{id}")
    public ResponseEntity<Resource> getAvatar(@PathVariable long id) {

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(friendService.getAvatar(id));
    }

    @PostMapping("/api/friends/save")
    public ResponseEntity<Boolean> save(@RequestBody long id) {
        friendService.save(id);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/api/friends/myFriends")
    public List<FriendsDTO> myFriends() {
        return friendService.myFriends();

    }

    @DeleteMapping("/api/friends/del/{id}")
    public ResponseEntity<Boolean> del(@PathVariable long id) {
        friendService.del(id);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/api/friends/friendsRequest")
    public List<FriendsDTO> friendsRequest() {
        return friendService.friendsRequest();
    }
}

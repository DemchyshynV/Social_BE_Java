package com.mybook.api.controllers;

import com.mybook.api.models.Friends;
import com.mybook.api.models.Profile;
import com.mybook.api.repositories.FriendRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class TestController {
    private FriendRepository repository;
    @GetMapping("/api/{id}")
    public List<Long> get(@PathVariable long id){
        List<Long> allByFriendOrderByFriend = repository.getListFriendId(id);
        return allByFriendOrderByFriend;
    }
}

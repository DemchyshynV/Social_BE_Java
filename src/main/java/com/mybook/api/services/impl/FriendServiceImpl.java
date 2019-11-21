package com.mybook.api.services.impl;

import com.mybook.api.dto.FriendsDTO;
import com.mybook.api.models.Profile;
import com.mybook.api.models.User;
import com.mybook.api.services.FriendService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FriendServiceImpl implements FriendService {
    private UserServiceImpl userService;


    @Override
    public List<FriendsDTO> getFriends() {
        List<FriendsDTO> result = new ArrayList<>();
        List<Profile> friends = userService.getCurrentUser().getProfile().getFriends();
        for (Profile friend : friends) {
            result.add(
                    FriendsDTO.builder()
                            .id(friend.getId())
                            .avatar(friend.getAvatar() == null ? null : ("/api/friends/avatar/" + friend.getId()))
                            .name(friend.getName())
                            .surname(friend.getSurname())
                            .build()
            );
        }
        return result;
    }

    @Override
    public List<FriendsDTO> findFriends() {
        List<User> all = userService.getAll();
        long exclude = userService.getCurrentUser().getId();
        List<FriendsDTO> result = new ArrayList<>();

        for (User user : all) {
            if (user.getId() == exclude)
                continue;
//            if (user.getProfile().getAvatar() == null)
            result.add(
                    FriendsDTO.builder()
                            .name(user.getProfile().getName())
                            .surname(user.getProfile().getSurname())
                            .avatar(user.getProfile().getAvatar() == null ? null : ("/api/friends/avatar/" + user.getId()))
                            .id(user.getId())
                            .build()
            );
        }
        return result;
    }

    @Override
    public void saveFriend(long id) {

    }
}

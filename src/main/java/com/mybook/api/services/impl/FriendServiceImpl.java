package com.mybook.api.services.impl;

import com.mybook.api.dto.FriendsDTO;
import com.mybook.api.models.Profile;
import com.mybook.api.models.User;
import com.mybook.api.repositories.FriendRepository;
import com.mybook.api.services.FriendService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FriendServiceImpl implements FriendService {
    private UserServiceImpl userService;
    private FriendRepository friendRepository;


    @Override
    public List<FriendsDTO> myFriends() {
        User currentUser = userService.getCurrentUser();
        List<Profile> friends = currentUser.getProfile().getFriends();
        List<Long> listFriendId = friendRepository.getListFriendId(currentUser.getId());
        List<FriendsDTO> result = new ArrayList<>();
        for (Profile friend : friends) {
            listFriendId.forEach(aLong -> {
                if (friend.getId() == aLong) {
                    result.add(
                            FriendsDTO.builder()
                                    .id(friend.getId())
                                    .avatar(friend.getAvatar() == null ? null : ("/api/friends/avatar/" + friend.getId()))
                                    .name(friend.getName())
                                    .surname(friend.getSurname())
                                    .build()
                    );
                }
            });
        }
        return result;
    }

    @Override
    public List<FriendsDTO> findFriends() {
        List<User> all = userService.getAll();
        User currentUser = userService.getCurrentUser();
        List<Long> exceptions = new ArrayList<>();
        exceptions.add(currentUser.getId());
        List<Profile> friends = currentUser.getProfile().getFriends();
        for (Profile friend : friends) {
            exceptions.add(friend.getId());
        }
        List<FriendsDTO> result = new ArrayList<>();

        exceptions.forEach(ex -> all.removeIf(user -> user.getId() == ex));
        all.forEach(user ->
                result.add(
                        FriendsDTO.builder()
                                .name(user.getProfile().getName())
                                .surname(user.getProfile().getSurname())
                                .avatar(user.getProfile().getAvatar() == null ? null : ("/api/friends/avatar/" + user.getId()))
                                .id(user.getId())
                                .build()
                ));

        return result;
    }

    @Override
    public void saveFriend(long id) {
        User friend = userService.findById(id);
        User currentUser = userService.getCurrentUser();
        currentUser.getProfile().getFriends().add(friend.getProfile());
        userService.save(currentUser);
    }

    @Override
    public void del(long id) {
        User currentUser = userService.getCurrentUser();
        List<Profile> friends = currentUser.getProfile().getFriends();
        User target = userService.findById(id);
        List<Profile> targetFriends = target.getProfile().getFriends();
        friends.removeIf(profile -> profile.getId() ==id);
        targetFriends.removeIf(profile -> profile.getId() == currentUser.getId());
        currentUser.getProfile().setFriends(friends);
        target.getProfile().setFriends(targetFriends);
        userService.save(currentUser);
        userService.save(target);
    }
}

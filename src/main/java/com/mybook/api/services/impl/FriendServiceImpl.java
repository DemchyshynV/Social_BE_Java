package com.mybook.api.services.impl;

import com.mybook.api.dto.FriendsDTO;
import com.mybook.api.models.Profile;
import com.mybook.api.models.User;
import com.mybook.api.repositories.FriendRepository;
import com.mybook.api.services.FriendService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FriendServiceImpl implements FriendService {
    private UserServiceImpl userService;
    private FriendRepository friendRepository;


    //    @Override
//    public List<FriendsDTO> myFriends() {
//        User currentUser = userService.getCurrentUser();
//        List<Profile> friends = currentUser.getProfile().getFriends();
//        List<Long> listFriendId = friendRepository.friendsRequest(currentUser.getId());
//        List<FriendsDTO> result = new ArrayList<>();
//        for (Profile friend : friends) {
//            listFriendId.forEach(aLong -> {
//                if (friend.getId() == aLong) {
//                    result.add(builderFriendDTO(friend));
//                }
//            });
//        }
//        return result;
//    }
    @Override
    public List<FriendsDTO> myFriends() {
        User currentUser = userService.getCurrentUser();
        List<Long> request = friendRepository.friendsRequest(currentUser.getId());
        List<FriendsDTO> result = new ArrayList<>();
        request.forEach(req -> {
            if (isFriend(req, currentUser))
                result.add(builderFriendDTO(userService.findById(req).getProfile()));
        });
        return result;
    }

    private boolean isFriend(long candidateId, User currentUser) {
        List<Profile> friends = currentUser.getProfile().getFriends();
        Profile candidate = userService.findById(candidateId).getProfile();

        if (friends.contains(candidate) && candidate.getFriends().contains(currentUser.getProfile()))
            return true;
        return false;
    }

    //    @Override
//    public List<FriendsDTO> findFriends() {
//        List<User> all = userService.getAll();
//        User currentUser = userService.getCurrentUser();
//        List<Long> exceptions = new ArrayList<>();
//        exceptions.add(currentUser.getId());
//        List<Profile> friends = currentUser.getProfile().getFriends();
//        for (Profile friend : friends) {
//            exceptions.add(friend.getId());
//        }
//        List<FriendsDTO> result = new ArrayList<>();
//
//        exceptions.forEach(ex -> all.removeIf(user -> user.getId() == ex));
//        all.forEach(user ->
//                result.add(builderFriendDTO(user.getProfile())));
//        return result;
//    }
    @Override
    public List<FriendsDTO> findFriends() {
        List<User> all = userService.getAll();
        User currentUser = userService.getCurrentUser();
        List<FriendsDTO> result = new ArrayList<>();
//        List<FriendsDTO> friendsDTOS = myRequests();
        all.forEach(candidate -> {
            if (
                    !isFriend(candidate.getId(), currentUser)
                            && candidate.getId() != currentUser.getId()
                            && !friendRepository.myRequest(currentUser.getId()).contains(candidate.getId())
            ) {
                result.add(builderFriendDTO(candidate.getProfile()));
            }
        });
        return result;
    }

    @Override
    public List<FriendsDTO> myRequests() {
        User currentUser = userService.getCurrentUser();
        List<Long> request = friendRepository.myRequest(currentUser.getId());
        List<FriendsDTO> result = new ArrayList<>();
        request.forEach(req -> {
            if (!isFriend(req, currentUser))
                result.add(builderFriendDTO(userService.findById(req).getProfile()));
        });
        return result;

    }

    @Override
    public List<FriendsDTO> friendsRequest() {
        User currentUser = userService.getCurrentUser();
        List<Long> request = friendRepository.friendsRequest(currentUser.getId());
        List<FriendsDTO> result = new ArrayList<>();
            request.forEach(req ->{
                if (!isFriend(req, currentUser))
                    result.add(builderFriendDTO(userService.findById(req).getProfile()));
            } );
        return result;
    }

    private FriendsDTO builderFriendDTO(Profile friend) {
        return FriendsDTO.builder()
                .id(friend.getId())
                .avatar(friend.getAvatar() == null ? null : ("/api/friends/avatar/" + friend.getId()))
                .name(friend.getName())
                .surname(friend.getSurname())
                .build();
    }


    @Override
    public Resource getAvatar(long id) {
        String filePath = userService.findById(id).getProfile().getAvatar();
        File file = new File(filePath);
        Resource resource = new FileSystemResource(file);
        return resource;
    }

    @Override
    public void save(long id) {
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
        friends.removeIf(profile -> profile.getId() == id);
        targetFriends.removeIf(profile -> profile.getId() == currentUser.getId());
        currentUser.getProfile().setFriends(friends);
        target.getProfile().setFriends(targetFriends);
        userService.save(currentUser);
        userService.save(target);
    }

}

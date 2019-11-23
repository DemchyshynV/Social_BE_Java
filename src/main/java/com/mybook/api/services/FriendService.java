package com.mybook.api.services;

import com.mybook.api.dto.FriendsDTO;
import com.mybook.api.models.Profile;
import com.mybook.api.models.User;
import org.springframework.core.io.Resource;

import java.util.List;

public interface FriendService {
    List<FriendsDTO> myFriends();
    List<FriendsDTO> findFriends();
    List<FriendsDTO> myRequests();
    List<FriendsDTO> friendsRequest();
    Resource getAvatar(long id);
    void save(long id);
    void del(long id);
}

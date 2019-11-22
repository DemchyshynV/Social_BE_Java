package com.mybook.api.services;

import com.mybook.api.dto.FriendsDTO;
import com.mybook.api.models.Profile;
import com.mybook.api.models.User;

import java.util.List;

public interface FriendService {
    List<FriendsDTO> myFriends();
    List<FriendsDTO> findFriends();
    void saveFriend(long id);
    void del(long id);
}

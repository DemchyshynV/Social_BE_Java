package com.mybook.api.repositories;

import com.mybook.api.models.Friends;
import com.mybook.api.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friends, Long> {
    @Query("select profile_id from Friends where friend_id like :id")
    List<Long> friendsRequest(long id);
    @Query("select friend_id from Friends where profile_id like :id")
    List<Long> myRequest(long id);
}

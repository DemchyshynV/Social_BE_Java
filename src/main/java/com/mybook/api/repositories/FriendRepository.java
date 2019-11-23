package com.mybook.api.repositories;

import com.mybook.api.models.Friends;
import com.mybook.api.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friends, Long> {
    @Query("select profile from Friends where friend like :id")
    List<Long> friendsRequest(long id);
    @Query("select friend from Friends where profile like :id")
    List<Long> myRequest(long id);
}

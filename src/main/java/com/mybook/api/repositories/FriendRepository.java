package com.mybook.api.repositories;

import com.mybook.api.models.Friends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends JpaRepository<Friends, Long> {
}

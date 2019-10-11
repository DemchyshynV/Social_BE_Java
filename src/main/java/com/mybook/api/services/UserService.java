package com.mybook.api.services;

import com.mybook.api.models.User;

import java.util.List;

public interface UserService {
    User register(User user);

    List<User> getAll();

    User findByEmail(String email);

    User findById(long id);

    void delete(long id);

}

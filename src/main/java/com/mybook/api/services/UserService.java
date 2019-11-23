package com.mybook.api.services;

import com.mybook.api.models.User;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public interface UserService {
    User register(User user);
    User getCurrentUser();
    List<User> getAll();

    User findByEmail(String email);

    User findById(long id);


    void save(User user);

    void delete(long id);

}

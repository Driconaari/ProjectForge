package com.example.projectforge.repository;

import com.example.projectforge.model.User;
import java.util.List;

public interface UserRepo {
    User save(User user);
    User findById(Long id);
    List<User> findAll();
    void delete(User user);
    User findByUsername(String username);

    void register(User newUser);

}
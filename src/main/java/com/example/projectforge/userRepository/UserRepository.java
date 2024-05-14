package com.example.projectforge.userRepository;

import com.example.projectforge.model.User;

import java.util.List;

public interface UserRepository {
    User save(User user);

    User findById(String id);

    List<User> findAll();

    void delete(User user);

    User findByUsername(String username);

    User login(String username, String password);

    void register(User user);

    boolean isUsernameTaken(String username);

    int getUserID(int projectId);

    void editUser(User user, long userId);

    User getUserFromId(long userId);
}
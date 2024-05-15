package com.example.ProjectForge.service;

import com.example.ProjectForge.model.User;
import com.example.ProjectForge.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Sign in
    public User login(String username, String password){
        return userRepository.login(username, password);
    }

    //Sign up
    public void register(User user) {
        userRepository.register(user);
    }

    //Is username taken
    public boolean isUsernameTaken(String username) {
        return userRepository.isUsernameTaken(username);
    }

    //Get user_id from project_id
    public int getUserID(int project_id) {
        return userRepository.getUserID(project_id);
    }

    //Edit user
    public void editUser(User user, int user_id) {
        userRepository.editUser(user, user_id);
    }

    //Get user from id
    public User getUserFromId(int user_id) {
        return userRepository.getUserFromId(user_id);
    }

}

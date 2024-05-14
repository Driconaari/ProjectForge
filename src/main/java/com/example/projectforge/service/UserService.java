package com.example.projectforge.service;

import com.example.projectforge.model.User;
import com.example.projectforge.userRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    //Sign in
    public User signIn(String username, String password){
        return userRepository.login(username, password);
    }

    //Sign up
    public void signUp(User user) {
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
    public void editUser(User user, long user_id) {
        userRepository.editUser(user, user_id);
    }

    //Get user from id
    public User getUserFromId(long user_id) {
        return userRepository.getUserFromId(user_id);
    }

    // Add more methods as needed for your application
}
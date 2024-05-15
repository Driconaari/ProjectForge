package com.example.ProjectForge.repository;

import com.example.ProjectForge.model.User;

public interface IUserRepository {

    //Sign in with user
    public User login(String organization_name, String password);

    //Sign up
    public void register(User user);

    //Is username taken
    public boolean isUsernameTaken(String username);

    //Edit user
    public void editUser(User user, int user_id);

    //Get user from id
    public User getUserFromId(int user_id);

    //Get user id from project id
    public int getUserID(int project_id);

}

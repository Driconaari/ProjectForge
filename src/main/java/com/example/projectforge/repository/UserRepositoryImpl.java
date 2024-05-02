package com.example.projectforge.repository;

import com.example.projectforge.model.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepo {
    private Map<Long, User> userMap = new HashMap<>();

    @Autowired
    private EntityManager entityManager;

    @Override
    public User save(User user) {
        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public User findById(Long id) {
        return userMap.get(id);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public void delete(User user) {
        userMap.remove(user.getId());
    }

    @Override
    public User findByUsername(String username) {
        for (User user : userMap.values()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    @Transactional
    public void register(User newUser) {
        entityManager.persist(newUser);
    }

}
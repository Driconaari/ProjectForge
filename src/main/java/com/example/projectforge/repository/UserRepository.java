package com.example.projectforge.repository;

import com.example.projectforge.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface UserRepository {
    Map<Long, User> userMap = new HashMap<>();

    default User save(User user) {
        userMap.put(user.getId(), user);
        return user;
    }

    default User findById(Long id) {
        return userMap.get(id);
    }

    default List<User> findAll() {
        return new ArrayList<>(userMap.values());
    }

    default void delete(User user) {
        userMap.remove(user.getId());
    }

    default User findByUsername(String username) {
        for (User user : userMap.values()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}

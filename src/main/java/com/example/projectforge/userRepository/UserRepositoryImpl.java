package com.example.projectforge.userRepository;

import com.example.projectforge.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private EntityManager entityManager;

   @Override
public User save(User user) {
    // Get the highest user_id in the database
    Long maxUserId = entityManager.createQuery("SELECT MAX(u.user_id) FROM User u", Long.class).getSingleResult();

    // If there are no users in the database, start at 1, otherwise increment the highest user_id
    int newUserId = maxUserId == null ? 1 : maxUserId.intValue() + 1;

    // Set the new user's ID
    user.setUserId(newUserId);

    // Persist the new user
    entityManager.persist(user);

    return user;
}

    @Override
    public User findById(String id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public void delete(User user) {
        entityManager.remove(user);
    }

    @Override
    public User findByUsername(String username) {
        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    @Transactional
    public void register(User newUser) {
        entityManager.persist(newUser);
    }

    @Override
    public User login(String username, String password) {
        return null;
    }


    @Override
    public boolean isUsernameTaken(String username) {
        return false;
    }

    @Override
    public int getUserID(int projectId) {
        return 0;
    }

    @Override
    public void editUser(User user, long userId) {

    }

    @Override
    public User getUserFromId(long userId) {
        return null;
    }

}


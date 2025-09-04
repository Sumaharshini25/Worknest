package com.worknest.service;

import java.util.List;
import com.worknest.entity.User;

public interface UserService {

    void registerUser(User user);

    void updateUser(User user);

    void deleteUser(Long id);

    User getUserById(Long id);

    User getUserByUsername(String username);

    List<User> getAllUsers();

    // Authenticate user by username/password
    User authenticate(String username, String password);
}

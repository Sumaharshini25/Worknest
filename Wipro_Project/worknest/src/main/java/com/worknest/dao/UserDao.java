package com.worknest.dao;

import java.util.List;

import com.worknest.entity.User;

public interface UserDao {

    void saveUser(User user);

    void updateUser(User user);

    void deleteUser(Long id);

    User getUserById(Long id);

    User getUserByUsername(String username);

    List<User> getAllUsers();
}

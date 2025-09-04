package com.worknest.service.Impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.worknest.dao.UserDao;
import com.worknest.entity.User;
import com.worknest.service.UserService;

/**
 * UserServiceImpl - business logic around users.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public void registerUser(User user) {
        userDao.saveUser(user);
    }

    @Override
    public void updateUser(User user) { userDao.updateUser(user); }

    @Override
    public void deleteUser(Long id) { userDao.deleteUser(id); }

    @Override
    public User getUserById(Long id) { return userDao.getUserById(id); }

    @Override
    public User getUserByUsername(String username) { return userDao.getUserByUsername(username); }

    @Override
    public List<User> getAllUsers() { return userDao.getAllUsers(); }

    @Override
    public User authenticate(String username, String password) {
        User u = userDao.getUserByUsername(username);
        if (u != null && u.getPassword().equals(password)) return u;
        return null;
    }
}

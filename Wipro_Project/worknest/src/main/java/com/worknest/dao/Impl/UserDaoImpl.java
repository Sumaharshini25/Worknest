package com.worknest.dao.Impl;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.worknest.dao.UserDao;
import com.worknest.entity.User;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session current() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void saveUser(User user) {
        current().save(user);
    }

    @Override
    public void updateUser(User user) {
        current().update(user);
    }

    @Override
    public void deleteUser(Long id) {
        User u = current().get(User.class, id);
        if (u != null) current().delete(u);
    }

    @Override
    public User getUserById(Long id) {
        return current().get(User.class, id);
    }

    @Override
    public User getUserByUsername(String username) {
        Query<User> q = current().createQuery("from User where username = :u", User.class);
        q.setParameter("u", username);
        return q.uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        return current().createQuery("from User").list();
    }
}

package com.worknest.dao.Impl;

import java.util.List;
import jakarta.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.worknest.dao.TaskDao;
import com.worknest.entity.Task;
import com.worknest.entity.TaskStatus;
import com.worknest.entity.User;

@Repository
public class TaskDaoImpl implements TaskDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void saveTask(Task task) {
        sessionFactory.getCurrentSession().save(task);
    }

    @Override
    public void updateTask(Task task) {
        sessionFactory.getCurrentSession().update(task);
    }

    @Override
    public void deleteTask(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Task task = session.get(Task.class, id);
        if (task != null) {
            session.delete(task);
        }
    }

    @Override
    public Task getTaskById(Long id) {
        return sessionFactory.getCurrentSession().get(Task.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Task> getAllTasks() {
        return sessionFactory.getCurrentSession().createQuery("from Task").list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Task> getTasksByUserId(Long userId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Task where user.id = :userId");
        query.setParameter("userId", userId);
        return query.getResultList();
    }
    @Override
    @SuppressWarnings("unchecked")
    public List<Task> getTasksByUser(User user) {
        Query query = sessionFactory.getCurrentSession()
                                   .createQuery("from Task where user = :user");
        query.setParameter("user", user);
        return query.getResultList();
    }

    @Override
    public void updateTaskStatus(Long taskId, TaskStatus status) {
        Session session = sessionFactory.getCurrentSession();
        Task task = session.get(Task.class, taskId);
        if (task != null) {
            task.setStatus(status);
            session.update(task);
        }
    }

    // ------------------- NEW METHOD -------------------
    @SuppressWarnings("unchecked")
    @Override
    public List<Task> getTasksByStatus(TaskStatus status) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Task where status = :status");
        query.setParameter("status", status);
        return query.getResultList();
    }
}

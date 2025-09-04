package com.worknest.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.worknest.dao.TaskDao;
import com.worknest.entity.Task;
import com.worknest.entity.TaskStatus;
import com.worknest.entity.User;
import com.worknest.service.TaskService;

@Service
@Transactional
public class TaskserviceImpl implements TaskService {

    @Autowired
    private TaskDao taskDao;

    @Override
    public void saveTask(Task task) {
        taskDao.saveTask(task);
    }

    @Override
    public void updateTask(Task task) {
        taskDao.updateTask(task);
    }

    @Override
    public void deleteTask(Long id) {
        taskDao.deleteTask(id);
    }

    @Override
    public Task getTaskById(Long id) {
        return taskDao.getTaskById(id);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskDao.getAllTasks();
    }

    @Override
    public List<Task> getTasksByUserId(Long userId) {
        return taskDao.getTasksByUserId(userId);
    }

    @Override
    public void updateTaskStatus(Long taskId, TaskStatus status) {
        taskDao.updateTaskStatus(taskId, status);
    }
    @Override
    public List<Task> getTasksByUser(User user) {
        return taskDao.getTasksByUser(user);
    }


    // ------------------- NEW METHOD -------------------
    @Override
    public List<Task> getTasksByStatus(TaskStatus status) {
        return taskDao.getTasksByStatus(status);
    }
}

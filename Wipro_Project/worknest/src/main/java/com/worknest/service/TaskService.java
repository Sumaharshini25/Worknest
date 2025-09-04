package com.worknest.service;

import java.util.List;
import com.worknest.entity.Task;
import com.worknest.entity.TaskStatus;
import com.worknest.entity.User;

public interface TaskService {
    void saveTask(Task task);
    void updateTask(Task task);
    void deleteTask(Long id);
    Task getTaskById(Long id);
    List<Task> getAllTasks();
    List<Task> getTasksByUserId(Long userId);
    void updateTaskStatus(Long taskId, TaskStatus status);

    // NEW: Get tasks by status
    List<Task> getTasksByStatus(TaskStatus status);
 // Add this method
    List<Task> getTasksByUser(User user);

}

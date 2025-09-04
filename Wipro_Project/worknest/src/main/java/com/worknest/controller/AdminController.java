package com.worknest.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.worknest.entity.Task;
import com.worknest.entity.User;
import com.worknest.entity.TaskStatus;
import com.worknest.entity.Role;
import com.worknest.service.TaskService;
import com.worknest.service.UserService;
import com.worknest.service.CommentService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private CommentService commentService;

    // ---------------- Dashboard ----------------
    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        User logged = (User) session.getAttribute("loggedInUser");
        if (logged == null || logged.getRole() != Role.ADMIN) {
            return "redirect:/auth/login";
        }

        model.addAttribute("pending", taskService.getTasksByStatus(TaskStatus.PENDING));
        model.addAttribute("inProgress", taskService.getTasksByStatus(TaskStatus.IN_PROGRESS));
        model.addAttribute("completed", taskService.getTasksByStatus(TaskStatus.COMPLETED));
        model.addAttribute("delayed", taskService.getTasksByStatus(TaskStatus.DELAYED));
        model.addAttribute("users", userService.getAllUsers()); 

        return "admin/dashboard"; 
    }

    // ---------------- User Management ----------------
    @GetMapping("/users")
    public String listUsers(Model model, HttpSession session) {
        User logged = (User) session.getAttribute("loggedInUser");
        if (logged == null || logged.getRole() != Role.ADMIN) {
            return "redirect:/auth/login";
        }

        model.addAttribute("users", userService.getAllUsers());
        return "admin/users"; 
    }

    @GetMapping("/users/add")
    public String addUserForm(Model model, HttpSession session) {
        User logged = (User) session.getAttribute("loggedInUser");
        if (logged == null || logged.getRole() != Role.ADMIN) {
            return "redirect:/auth/login";
        }

        model.addAttribute("user", new User());
        return "admin/addUser"; 
    }

    @PostMapping("/users/save")
    public String saveUser(@ModelAttribute("user") User user, HttpSession session) {
        User logged = (User) session.getAttribute("loggedInUser");
        if (logged == null || logged.getRole() != Role.ADMIN) {
            return "redirect:/auth/login";
        }

        if (user.getRole() == null) {
            user.setRole(Role.USER); // default role
        }
        userService.registerUser(user);
        return "redirect:/admin/users";
    }

    // ---------------- Task Allocation ----------------
    @GetMapping("/tasks/assign")
    public String assignTaskForm(Model model, HttpSession session) {
        User logged = (User) session.getAttribute("loggedInUser");
        if (logged == null || logged.getRole() != Role.ADMIN) {
            return "redirect:/auth/login";
        }

        model.addAttribute("task", new Task());
        model.addAttribute("users", userService.getAllUsers());
        return "admin/allocateTask"; 
    }

    @GetMapping("/tasks/edit/{id}")
    public String editTaskForm(@PathVariable("id") Long id, Model model, HttpSession session) {
        User logged = (User) session.getAttribute("loggedInUser");
        if (logged == null || logged.getRole() != Role.ADMIN) {
            return "redirect:/auth/login";
        }

        Task task = taskService.getTaskById(id);
        if (task == null) {
            return "redirect:/admin/dashboard";
        }

        model.addAttribute("task", task);
        model.addAttribute("users", userService.getAllUsers());
        return "admin/allocateTask"; 
    }

    @PostMapping("/tasks/save")
    public String saveTask(@ModelAttribute("task") Task task,
                           @RequestParam("assignedUserId") Long assignedUserId,
                           HttpSession session) {
        User logged = (User) session.getAttribute("loggedInUser");
        if (logged == null || logged.getRole() != Role.ADMIN) {
            return "redirect:/auth/login";
        }

        User assignedUser = userService.getUserById(assignedUserId);
        task.setUser(assignedUser);

        if (task.getStatus() == null) {
            task.setStatus(TaskStatus.PENDING);
        }

        taskService.saveTask(task);
        return "redirect:/admin/dashboard";
    }

    // ---------------- View All Comments ----------------
    @GetMapping("/comments")
    public String viewComments(Model model, HttpSession session) {
        User logged = (User) session.getAttribute("loggedInUser");
        if (logged == null || logged.getRole() != Role.ADMIN) {
            return "redirect:/auth/login";
        }

        model.addAttribute("comments", commentService.getAllComments());
        return "admin/comments"; // FIXED: admin view, not user view
    }
}

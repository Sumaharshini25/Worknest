package com.worknest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.worknest.entity.Comment;
import com.worknest.entity.Task;
import com.worknest.entity.TaskStatus;
import com.worknest.entity.User;
import com.worknest.service.TaskService;
import com.worknest.service.CommentService;

import jakarta.servlet.http.HttpSession;

/**
 * UserController - user flows: view tasks, update status, add comments
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private CommentService commentService;

    // ---------------- View Assigned Tasks ----------------
    @GetMapping("/tasks")
    public String viewTasks(HttpSession session, Model model) {
        User logged = (User) session.getAttribute("loggedInUser");
        if (logged == null) return "redirect:/auth/login";

        List<Task> tasks = taskService.getTasksByUser(logged);
        model.addAttribute("tasks", tasks);
        return "user/tasks";   // ✅ maps to /WEB-INF/views/user/tasks.jsp
    }

    // ---------------- Update Task Status ----------------
    @GetMapping("/tasks/update/{id}/{status}")
    public String updateStatus(@PathVariable("id") Long taskId,
                               @PathVariable("status") String status,
                               HttpSession session) {
        User logged = (User) session.getAttribute("loggedInUser");
        if (logged == null) return "redirect:/auth/login";

        try {
            TaskStatus ts = TaskStatus.valueOf(status);
            taskService.updateTaskStatus(taskId, ts);
        } catch (IllegalArgumentException ex) {
            // invalid status - ignore
        }

        return "redirect:/user/tasks";
    }

    // ---------------- Comment Form ----------------
    @GetMapping("/tasks/comment/{taskId}")
    public String commentForm(@PathVariable Long taskId,
                              Model model,
                              HttpSession session) {
        User logged = (User) session.getAttribute("loggedInUser");
        if (logged == null) return "redirect:/auth/login";

        model.addAttribute("comment", new Comment());
        model.addAttribute("taskId", taskId);
        return "user/comments";   // ✅ maps to /WEB-INF/views/user/comments.jsp
    }

    // ---------------- Save Comment ----------------
    @PostMapping("/tasks/comment/save")
    public String saveComment(@ModelAttribute("comment") Comment comment,
                              @RequestParam("taskId") Long taskId,
                              HttpSession session) {
        User logged = (User) session.getAttribute("loggedInUser");
        if (logged == null) return "redirect:/auth/login";

        // attach user and task to comment
        comment.setUser(logged);
        Task t = taskService.getTaskById(taskId);
        comment.setTask(t);

        commentService.addComment(comment);
        return "redirect:/user/tasks";
    }
}

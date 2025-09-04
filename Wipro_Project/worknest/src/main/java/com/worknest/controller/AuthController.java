package com.worknest.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.worknest.entity.User;
import com.worknest.entity.Role;
import com.worknest.service.UserService;

/**
 * Authentication Controller - handles login, registration and logout
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    // ------------------- Login -------------------
    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/login";   // ✅ /WEB-INF/views/auth/login.jsp
    }

    @PostMapping("/login")
    public String processLogin(@ModelAttribute("user") User user,
                               HttpSession session,
                               Model model) {
        User logged = userService.authenticate(user.getUsername(), user.getPassword());
        if (logged == null) {
            model.addAttribute("error", "Invalid username or password");
            return "auth/login";   // ✅ return back to login.jsp if fail
        }

        // Save session
        session.setAttribute("loggedInUser", logged);

        // Redirect based on role
        if (logged.getRole() == Role.ADMIN) {
            return "redirect:/admin/dashboard";
        } else {
            return "redirect:/user/tasks";
        }
    }

    // ------------------- Registration -------------------
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";   // ✅ /WEB-INF/views/auth/register.jsp
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute("user") User user, Model model) {
        // Assign default role USER
        user.setRole(Role.USER);

        // Register user
        userService.registerUser(user);

        // Add success message
        model.addAttribute("msg", "Registration successful! Please login.");
        return "redirect:/auth/login";   // ✅ redirect to login after registration
    }

    // ------------------- Logout -------------------
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();   // clear session
        return "redirect:/auth/login";   // ✅ back to login.jsp
    }
}

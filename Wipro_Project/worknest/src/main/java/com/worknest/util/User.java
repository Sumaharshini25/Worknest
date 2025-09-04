package com.worknest.util;
import jakarta.persistence.GenerationType;

import jakarta.persistence.*;
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private String username;
    private String password;
    private String role; // ADMIN or USER

    // Getters and Setters
}
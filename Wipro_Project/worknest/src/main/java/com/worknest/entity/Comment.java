package com.worknest.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * Comment entity - stores comments made by users on tasks
 */
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="comment_text", columnDefinition = "TEXT", nullable = false)
    private String commentText;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt = new Date();

    // Author
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Associated task
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    public Comment() {}

    public Comment(String commentText, User user, Task task) {
        this.commentText = commentText;
        this.user = user;
        this.task = task;
    }

    // Getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCommentText() { return commentText; }
    public void setCommentText(String commentText) { this.commentText = commentText; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Task getTask() { return task; }
    public void setTask(Task task) { this.task = task; }

    @Override
    public String toString() {
        return "Comment {id=" + id + ", text='" + (commentText != null ? commentText.substring(0, Math.min(40, commentText.length())) : "") + "', createdAt=" + createdAt + "}";
    }
}

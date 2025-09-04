package com.worknest.dao;

import java.util.List;
import com.worknest.entity.Comment;

public interface CommentDao {

    void saveComment(Comment comment);

    void updateComment(Comment comment);

    void deleteComment(Long id);

    Comment getCommentById(Long id);

    List<Comment> getAllComments();

    List<Comment> getCommentsByTaskId(Long taskId);

    List<Comment> getCommentsByUserId(Long userId);
}

package com.worknest.service.Impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.worknest.dao.CommentDao;
import com.worknest.entity.Comment;
import com.worknest.service.CommentService;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Override
    public void addComment(Comment comment) { commentDao.saveComment(comment); }

    @Override
    public void updateComment(Comment comment) { commentDao.updateComment(comment); }

    @Override
    public void deleteComment(Long id) { commentDao.deleteComment(id); }

    @Override
    public Comment getCommentById(Long id) { return commentDao.getCommentById(id); }

    @Override
    public List<Comment> getAllComments() { return commentDao.getAllComments(); }

    @Override
    public List<Comment> getCommentsByTaskId(Long taskId) { return commentDao.getCommentsByTaskId(taskId); }

    @Override
    public List<Comment> getCommentsByUserId(Long userId) { return commentDao.getCommentsByUserId(userId); }
}

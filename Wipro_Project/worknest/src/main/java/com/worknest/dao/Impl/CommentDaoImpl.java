package com.worknest.dao.Impl;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.worknest.dao.CommentDao;
import com.worknest.entity.Comment;

@Repository
public class CommentDaoImpl implements CommentDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session current() { return sessionFactory.getCurrentSession(); }

    @Override
    public void saveComment(Comment comment) { current().save(comment); }

    @Override
    public void updateComment(Comment comment) { current().update(comment); }

    @Override
    public void deleteComment(Long id) {
        Comment c = current().get(Comment.class, id);
        if (c != null) current().delete(c);
    }

    @Override
    public Comment getCommentById(Long id) { return current().get(Comment.class, id); }

    @Override
    @SuppressWarnings("unchecked")
    public List<Comment> getAllComments() { return current().createQuery("from Comment order by createdAt desc").list(); }

    @Override
    @SuppressWarnings("unchecked")
    public List<Comment> getCommentsByTaskId(Long taskId) {
        Query<Comment> q = current().createQuery("from Comment where task.id = :tid order by createdAt desc", Comment.class);
        q.setParameter("tid", taskId);
        return q.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Comment> getCommentsByUserId(Long userId) {
        Query<Comment> q = current().createQuery("from Comment where user.id = :uid order by createdAt desc", Comment.class);
        q.setParameter("uid", userId);
        return q.list();
    }
}

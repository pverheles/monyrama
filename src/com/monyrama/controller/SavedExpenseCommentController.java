package com.monyrama.controller;

import com.monyrama.entity.PSavedExpenseComment;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

/**
 * Created by petroverheles on 12/5/16.
 */
public class SavedExpenseCommentController {
    private static SavedExpenseCommentController instance;

    private SavedExpenseCommentController() {}

    public static SavedExpenseCommentController instance() {
        if(instance == null) {
            instance = new SavedExpenseCommentController();
        }
        return instance;
    }

    public void save(final String comment) {
        if(comment == null || comment.trim().length() == 0) {
            return;
        }
        HibernateUtil.doInTransaction(new Executable() {
            @Override
            public void execute(Session session) {
                final PSavedExpenseComment commentEntity = new PSavedExpenseComment();
                commentEntity.setComment(comment);
                commentEntity.setSavedDate(new Date());
                HibernateUtil.saveOrUpdate(session, commentEntity);
            }
        });
    }

    public List<String> findComments(final String startText) {
        return HibernateUtil.queryInTransaction(new Resultable<List<String>>() {
            @Override
            public List<String> getResult(Session session) {
                return session.createSelectionQuery(
                        "select c.comment from PSavedExpenseComment c where lower(c.comment) like lower(:prefix) order by lower(c.comment)",
                        String.class)
                        .setParameter("prefix", startText + "%")
                        .setMaxResults(500)
                        .list();
            }
        });
    }
}

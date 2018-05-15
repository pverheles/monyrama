package com.monyrama.controller;

import com.monyrama.entity.PSavedExpenseComment;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
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
                session.saveOrUpdate(commentEntity);
            }
        });
    }

    public List<String> findComments(final String startText) {
        return HibernateUtil.queryInTransaction(new Resultable<List<String>>() {
            @Override
            public List<String> getResult(Session session) {
                Criteria criteria = session.createCriteria(PSavedExpenseComment.class);

                criteria.add(Restrictions.ilike("comment", startText + "%"));
                criteria.setMaxResults(500);
                criteria.addOrder(Order.asc("comment").ignoreCase());

                List<PSavedExpenseComment> list = criteria.list();

                List<String> comments = new ArrayList<String>();
                for(PSavedExpenseComment savedComment : list) {
                    comments.add(savedComment.getComment());
                }

                return comments;
            }
        });
    }
}

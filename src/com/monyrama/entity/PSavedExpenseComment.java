package com.monyrama.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by petroverheles on 12/5/16.
 */
@Entity
public class PSavedExpenseComment {
    private String comment;
    private Date savedDate;

    @Id
    @Column(length = DBConstants.COMMENTS_LENGTH)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Column(nullable = false)
    public Date getSavedDate() {
        return savedDate;
    }

    public void setSavedDate(Date savedDate) {
        this.savedDate = savedDate;
    }
}

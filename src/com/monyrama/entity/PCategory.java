package com.monyrama.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Representation of DB object of a category
 *
 * @author Petro_Verheles
 */
@Entity
public class PCategory extends BaseEntity {
    private Boolean calculateSumPerDay;

    @Column
    public Boolean getCalculateSumPerDay() {
        return calculateSumPerDay == null ? Boolean.FALSE : calculateSumPerDay;
    }

    public void setCalculateSumPerDay(Boolean calculateSumPerDay) {
        this.calculateSumPerDay = calculateSumPerDay;
    }
}

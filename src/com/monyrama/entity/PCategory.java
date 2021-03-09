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
    private Boolean isDefault;
    private String keywords;

    @Column
    public Boolean getCalculateSumPerDay() {
        return calculateSumPerDay == null ? Boolean.FALSE : calculateSumPerDay;
    }

    public void setCalculateSumPerDay(Boolean calculateSumPerDay) {
        this.calculateSumPerDay = calculateSumPerDay;
    }

    @Column
    public Boolean getIsDefault() {
        return isDefault == null ? Boolean.FALSE : isDefault;
    }

    public void setIsDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    @Column
    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}

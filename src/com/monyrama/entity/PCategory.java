package com.monyrama.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import org.apache.commons.lang3.StringUtils;

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

    public Set<String> keywordsSetLower() {
        Set<String> keywordsSet = new HashSet<>();
        if (keywords != null) {
            String[] split = keywords.split("\\r?\\n");
            for (String keyword : split) {
                if (StringUtils.isBlank(keyword)) {
                    continue;
                }

                keywordsSet.add(keyword.trim().toLowerCase());
            }
        }
        return keywordsSet;
    }
}

package com.monyrama.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Representation of DB object of a borrowing
 * 
 * @author Petro_Verheles
 */
@Entity
@DiscriminatorValue(PMoneyMovement.DEBT)
public class PDebt extends PMoneyMovementIn {
    private List<PPayingBack> payingBacks = new ArrayList<PPayingBack>(0);

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "debt")
    public List<PPayingBack> getPayingBacks() {
        return payingBacks;
    }

    public void setPayingBacks(List<PPayingBack> payingBacks) {
        this.payingBacks = payingBacks;
    }
}

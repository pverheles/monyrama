package com.monyrama.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
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

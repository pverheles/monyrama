package com.monyrama.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * Representation of DB object of a lend
 * 
 * @author Petro_Verheles
 */
@Entity
@DiscriminatorValue(PMoneyMovement.LEND)
public class PLend extends PMoneyMovementOut {
    private List<PTakingBack> takingBacks = new ArrayList<PTakingBack>(0);

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "lend")
    public List<PTakingBack> getTakingBacks() {
        return takingBacks;
    }

    public void setTakingBacks(List<PTakingBack> takingBacks) {
        this.takingBacks = takingBacks;
    }

}

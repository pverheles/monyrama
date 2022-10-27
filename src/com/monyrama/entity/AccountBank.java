package com.monyrama.entity;

import com.monyrama.ui.resources.Resources;

public enum AccountBank {
    MONOBANK("bank.name.monobank"),
    PRIVATBANK("bank.name.privatbank");

    private final String displayName;

    AccountBank(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return Resources.getString(displayName);
    }
}

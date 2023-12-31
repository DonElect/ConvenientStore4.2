package com.store.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
public class Cashier extends Staff{
    @Setter
    private String cashierStand;
    @Getter @Setter
    private boolean isHired;
    public Cashier(String firstName, String lastName, int age, String employerID, String email, String address, String cashierStand) {
        super(firstName, lastName, age, employerID, email, address);
        this.cashierStand = cashierStand;
    }
}

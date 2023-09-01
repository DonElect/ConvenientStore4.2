package com.store.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
public class CustomerModel {
    @Setter
    private String fullName;
    @Getter
    private int ID;
    private static int count = 1;


    public CustomerModel(String fullName) {
        this.fullName = fullName;
        this.ID = count++;
    }

}

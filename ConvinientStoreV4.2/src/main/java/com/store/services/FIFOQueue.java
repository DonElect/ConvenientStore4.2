package com.store.services;

import com.store.models.ProductDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

@Getter
@AllArgsConstructor
public class FIFOQueue {
    @Setter
    private int customerID;
    @Getter @Setter
    private Map<String, ProductDetails> cart;

    @Getter
    public static Queue<FIFOQueue> fifoCart = new LinkedList<>();


    public void addToFIFOQueue(FIFOQueue fiFoClass){
        fifoCart.add(new FIFOQueue(customerID, cart));
    }
}
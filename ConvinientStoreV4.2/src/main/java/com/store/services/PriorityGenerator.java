package com.store.services;
import com.store.models.ProductDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

@NoArgsConstructor
public class PriorityGenerator implements Comparable<PriorityGenerator>{
    private int totalQuantity;
    @Getter @Setter
    private int customerID;
    @Getter @Setter
    private Map<String, ProductDetails> cart;
    @Getter
    public static Queue<PriorityGenerator> priorityCart = new PriorityQueue<>();

    public PriorityGenerator(int ID, Map<String, ProductDetails> cart){
        this.customerID = ID;
        this.cart = cart;
        this.totalQuantity = getTotalQuantity();
    }

    public void addToPriorityQueue(PriorityGenerator newPriority){
        priorityCart.add(newPriority);
    }

    private int getTotalQuantity(){
        for (var items : cart.values()){
            totalQuantity += items.getQuantity();
        }
        return totalQuantity;
    }


    @Override
    public int compareTo(PriorityGenerator o) {
        return (o.totalQuantity > this.totalQuantity) ? 1 : ((o.totalQuantity == this.totalQuantity) ? 0 : -1);
    }


}
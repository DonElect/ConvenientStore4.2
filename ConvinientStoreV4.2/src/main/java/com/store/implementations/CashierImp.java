package com.store.implementations;

import com.store.models.Cashier;
import com.store.models.CustomerModel;
import com.store.models.ProductDetails;
import com.store.services.CATEGORY;
import com.store.services.CashierServices;
import com.store.services.PriorityGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;

@NoArgsConstructor
public class CashierImp implements CashierServices {
    Products products = new Products();

    private Cashier cashier;


    public CashierImp(Cashier cashier){
        this.cashier = cashier;
    }


    @Override
    public void sell(Customer customer) {
        if(cashier.isHired()) {
            if(!customer.getCart().isEmpty())
                System.out.println("The following item in your store are been bought.");
        }else System.out.println("You are no longer a staff here. And cannot sell");
    }

    @Override
    public boolean dispenseReceipt() {
        if (cashier.isHired()) {
            boolean isDone = false;
            Queue<PriorityGenerator> queue = PriorityGenerator.getCartQueue();
            while (!queue.isEmpty()) {
                PriorityGenerator newPriority = queue.poll();
                if (!newPriority.getCart().isEmpty()) {
                    int sum = 0;
                    System.out.println("*********************************************************");
                    System.out.println(ManagerImp.getListCustomers().get(newPriority.getCustomerID()).getFullName().toUpperCase()+" PURCHASE RECEIPT");
                    System.out.println("Products                  Price(₦\u200E)              Quantity");
                    for (Map.Entry<String, ProductDetails> items : newPriority.getCart().entrySet()) {
                        System.out.printf("%-25s %-25s %-25s", items.getKey(), items.getValue().getPrice(), items.getValue().getQuantity());
                        System.out.println();
                        sum += items.getValue().getPrice() * (items.getValue().getQuantity());
                    }

                    // Clear the user cart to avoid buy repetition
                    newPriority.getCart().clear();

                    System.out.println("Total price: " + sum + "\nThank you for patronizing us, have a great day!");
                    System.out.println();
                    //If receipt was successfully dispensed
                    isDone = true;
                } else {
                    System.out.println("Your Cart is Empty");
                    System.out.println();
                    isDone = false;
                }
            }
            return isDone;
        }
        else {
            System.out.println("You are no longer a staff here. And so cannot dispense receipt");
            return false;
        }
    }

    @Override
    public boolean addProduct() {
        if(cashier.isHired()) {
            if (products.addProductsToShelve())
            // If products were successfully added
                return true;
            else {
                System.out.println("Products not successfully added.");
                return false;
            }
        }
        else {
            System.out.println("You are no longer a staff here. And so cannot add products to store");
            return false;
        }
    }

    public void view(CATEGORY CAT){
       products.view(CAT);
    }

    @Override
    public boolean updateStoreRecord(){
        if(cashier.isHired()){
            if(products.updateStock()){
                System.out.println("Store Record Updated");
                System.out.println();
                return true;
            }
            else return false;
        }
        else {
            System.out.println("Your are no longer a Cashier here.");
            return false;
        }
    }
}

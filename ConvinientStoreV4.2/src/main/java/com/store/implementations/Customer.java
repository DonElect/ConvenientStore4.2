package com.store.implementations;

import com.store.models.CustomerModel;
import com.store.models.ProductDetails;
import com.store.services.CATEGORY;
import com.store.services.FIFOQueue;
import com.store.services.PriorityGenerator;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;



public class Customer {
    private final Products products = new Products();
    private CustomerModel customer;

    public Customer(CustomerModel customer){
        this.customer = customer;
    }

    public Customer(){
    }
    @Getter
    private Map<String, ProductDetails> cart = new HashMap<>();

    public void addMe(){
        ManagerImp.getListCustomers().put(customer.getID(), customer);
    }

    public String addToCart(String item, int quantity) {
        item = item.toLowerCase();
        Map<String, ProductDetails> itemCategory = productCheck(item);
        if(itemCategory != null) {
            if(adjustQuantity(item, quantity)) {
                if (cart.containsKey(item)) {
                    int currentQuantity = cart.get(item).getQuantity() + quantity;
                    cart.get(item).setQuantity(currentQuantity);
                } else {
                    ProductDetails newProducts = new ProductDetails();
                    newProducts.setNameOfProduct(item);
                    newProducts.setCAT(itemCategory.get(item).getCAT());
                    newProducts.setPrice(itemCategory.get(item).getPrice());
                    newProducts.setQuantity(quantity);
                    cart.put(item, newProducts);
                }
                System.out.println(quantity+ " "+ item+" added to your cart.");
                System.out.println();
                return "DONE";
            }
            else {
                if(itemCategory.get(item).getQuantity() == 0){
                    System.out.println("OUT OF STOCK");
                    System.out.println();
                    return "OUT-OF-STOCK";
                }
                else System.out.println(item.toUpperCase()+"s remaining "+ itemCategory.get(item).getQuantity());
            }
        }
        else
            System.out.println("We dont have "+item.toUpperCase()+" in our store for now.");
        return "NOT-IN-STORE";
    }

    public String removeFromCart(String item, int quantity) {
        item = item.toLowerCase();
        // Convert quantity to negative to be able to reuse the adjustQuantity method
        quantity = -1*quantity;
        if(cart.containsKey(item)){
            if(adjustQuantity(item, quantity)) {
                if (cart.get(item).getQuantity() + quantity > 0) {
                    cart.get(item).setQuantity(cart.get(item).getQuantity() + quantity);
                    System.out.println(-1*quantity + " " + item + " removed from your cart!");
                    System.out.println();
                } else {
                    cart.remove(item);
                }
                return "done";
            }
            else return "not-done";
        }
        else {
            System.out.println("You dont have " + item.toUpperCase() + " in your cart.");
            return "not-in-cart";
        }
    }


    public boolean buy(){
        PriorityGenerator newPriority = new PriorityGenerator(customer.getID(), cart);
        FIFOQueue fiFoClass = new FIFOQueue(customer.getID(), cart);
        fiFoClass.addToFIFOQueue(fiFoClass);
        newPriority.addToPriorityQueue(newPriority);
        return true;
    }

    public void viewMyCart(CATEGORY CAT){
        products.view(CAT);
    }

    private Map<String, ProductDetails> productCheck(String productName){
        return products.getProvisions().containsKey(productName)? products.getProvisions():
                products.getVegetables().containsKey(productName)? products.getVegetables():
                        products.getFruits().containsKey(productName)? products.getFruits():
                                products.getTools().containsKey(productName)? products.getTools():null;
    }

    private boolean adjustQuantity(String item, int quantity){
        if(products.getFruits().containsKey(item)) {
            if((products.getFruits().get(item).getQuantity() - quantity) >= 0) {
                products.getFruits().get(item).setQuantity(products.getFruits().get(item).getQuantity() - quantity);
                return true;
            }
            else
                return false;
        }
        else if(products.getProvisions().containsKey(item)){
            if((products.getProvisions().get(item).getQuantity() - quantity) >= 0) {
                products.getProvisions().get(item).setQuantity(products.getProvisions().get(item).getQuantity() - quantity);
                return true;
            }
            else
                return false;
        }
        else if(products.getTools().containsKey(item)){
            if((products.getTools().get(item).getQuantity() - quantity) >= 0) {
                products.getTools().get(item).setQuantity(products.getTools().get(item).getQuantity() - quantity);
                return true;
            }
            else {
                return false;
            }
        }
        else if(products.getVegetables().containsKey(item)){
            if((products.getVegetables().get(item).getQuantity() - quantity) >= 0) {
                products.getVegetables().get(item).setQuantity(products.getVegetables().get(item).getQuantity() - quantity);
                return true;
            }
            else {

                return false;
            }
        }
        else return false;
    }

    public int myID(){
        return customer.getID();
    }
}

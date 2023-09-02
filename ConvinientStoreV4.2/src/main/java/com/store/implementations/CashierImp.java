package com.store.implementations;

import com.store.models.Cashier;
import com.store.models.ProductDetails;
import com.store.services.CATEGORY;
import com.store.services.CashierServices;
import com.store.services.FIFOQueue;
import com.store.services.PriorityGenerator;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Queue;

@NoArgsConstructor
public class CashierImp implements CashierServices {
    Products products = new Products();
    private Cashier cashier;

    public CashierImp(Cashier cashier) {
        this.cashier = cashier;
    }

    @Override
    public String sellByFIFO() {
        if (cashier.isHired()) {
            String result = "";
            Queue<FIFOQueue> queue = FIFOQueue.getFifoCart();
            while (!queue.isEmpty()) {
                FIFOQueue newFIFO = queue.poll();
                String customer_name = ManagerImp.getListCustomers()
                        .get(newFIFO.getCustomerID())
                        .getFullName();

                if (dispenseReceipt(customer_name, newFIFO.getCart())){
                    System.out.println("Thank You for Shopping with us");
                    System.out.println();
                    result = "SUCCESSFUL";
                }
                else {
                    result = "UNSUCCESSFUL";
                }
            }
            return result;

        } else {
            System.out.println("You are no longer a staff here. And so cannot dispense receipt");
            return "NOT-A-STAFF";
        }
    }

    @Override
    public String sellByPriority() {
        if (cashier.isHired()) {
            String result = "";
            Queue<PriorityGenerator> queue = PriorityGenerator.getPriorityCart();
            while (!queue.isEmpty()) {
                PriorityGenerator newPriority = queue.poll();
                String customer_name = ManagerImp.getListCustomers()
                        .get(newPriority.getCustomerID())
                        .getFullName();

               if (dispenseReceipt(customer_name,newPriority.getCart())){
                   System.out.println("Thank You for Shopping with us");
                   System.out.println();
                   result = "SUCCESSFUL";
               }
               else {
                   result = "UNSUCCESSFUL";
               }
            }
            return result;

        } else {
            System.out.println("You are no longer a staff here. And so cannot dispense receipt");
            return "NOT-A-STAFF";
        }
    }

    private boolean dispenseReceipt(String customer_name, Map<String, ProductDetails> cart) {
        if (!cart.isEmpty()) {
            int sum = 0;
            System.out.println("*********************************************************");
            System.out.println(customer_name + " PURCHASE RECEIPT");
            System.out.println("Products                  Price(₦\u200E)              Quantity");
            for (Map.Entry<String, ProductDetails> items : cart.entrySet()) {
                System.out.printf("%-25s %-25s %-25s", items.getKey(), items.getValue().getPrice(), items.getValue().getQuantity());
                System.out.println();
                sum += items.getValue().getPrice() * (items.getValue().getQuantity());
            }
            // Clear the user cart to avoid buy repetition
            cart.clear();
            //If receipt was successfully dispensed
            return true;
        } else
            return false;
    }

    @Override
    public boolean addProduct() {
        if (cashier.isHired()) {
            if (products.addProductsToShelve())
                // If products were successfully added
                return true;
            else {
                System.out.println("Products not successfully added.");
                return false;
            }
        } else {
            System.out.println("You are no longer a staff here. And so cannot add products to store");
            return false;
        }
    }

    @Override
    public void view(CATEGORY CAT) {
        products.view(CAT);
    }

    @Override
    public boolean updateStoreRecord() {
        if (cashier.isHired()) {
            if (products.updateStock()) {
                System.out.println("Store Record Updated");
                System.out.println();
                return true;
            } else return false;
        } else {
            System.out.println("Your are no longer a Cashier here.");
            return false;
        }
    }
}

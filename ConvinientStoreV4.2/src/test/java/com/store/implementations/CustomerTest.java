package com.store.implementations;

import com.store.models.Cashier;
import com.store.models.Manager;
import com.store.services.CashierServices;
import com.store.services.ManagerServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    ManagerServices manager;
    Cashier cashier;
    CashierServices cashierImp;
    Customer customer1;
    Customer customer2;
    @BeforeEach
    public void runBeforeAnyOtherTest(){
        Manager manager1 = new Manager("Donatus", "Okpala", 29,
                "D55B78", "donatus.okpala@decagon.dev", "Decagon Institute, Ohen");
        manager = new ManagerImp(manager1);
        cashier = new Cashier("Jane", "Mary",
                25, "D0002B02", "jamemary@gmail.com",
                "Ohen", "Stand 1");
        cashierImp = new CashierImp(cashier);
        customer1 = new Customer();
        customer2 = new Customer();
    }
    // Creating the necessary instances


    @Test
    void addToCart() {
        manager.hire(cashier);

        cashierImp.addProduct();

        // Making a valid purchase
        assertSame("DONE", customer1.addToCart("Mango", 25));

        // Total amount of Mango=25. Customer1 already added 25 to his cart. So if customer2 tries to take more than the
        // remain, it won't work.
        assertSame("OUT-OF-STOCK", customer2.addToCart("Mango", 3));

        // Try to buy what is not in store
        assertSame("NOT-IN-STORE", customer1.addToCart("Garri", 3));
    }

    @Test
    void removeFromCart() {
        manager.hire(cashier);

        cashierImp.addProduct();

        customer1.addToCart("Orange", 3);
        customer1.addToCart("Mango", 5);
        customer1.addToCart("Apple", 10);

        // Added 3 oranges to cart and removed all 3 oranges, should return "done"
        assertSame("done", customer1.removeFromCart("Orange", 3));

        // Trying to remove an item that is not in cart should return not-in-cart
        assertSame("not-in-cart", customer1.removeFromCart("PawPaw", 3));
    }

    @Test
    void buy() {
        manager.hire(cashier);

        cashierImp.addProduct();

        customer1.addToCart("Orange", 3);
        customer1.addToCart("Mango", 5);
        customer1.addToCart("Apple", 10);

        assertTrue(customer1.buy());
    }
}
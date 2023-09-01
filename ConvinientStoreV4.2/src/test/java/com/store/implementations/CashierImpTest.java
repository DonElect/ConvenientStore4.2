package com.store.implementations;

import com.store.models.Cashier;
import com.store.models.Manager;
import com.store.services.CashierServices;
import com.store.services.ManagerServices;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CashierImpTest {
    ManagerServices manager;
    Cashier cashier1;
    CashierServices cashierImp1;
    Cashier cashier2;
    CashierServices cashierImp2;
    Customer customer1;
    Customer customer2;
    @BeforeEach
    public void runBeforeAnyOtherTest(){
        Manager manager1 = new Manager("Donatus", "Okpala", 29,
                "D55B78", "donatus.okpala@decagon.dev", "Decagon Institute, Ohen");
        manager = new ManagerImp(manager1);
        cashier1 = new Cashier("Jane", "Mary",
                25, "D0002B02", "jamemary@gmail.com",
                "Ohen", "Stand 1");
        cashierImp1 = new CashierImp(cashier1);
        cashier2 = new Cashier("Mike", "John",
                26, "D0003B02", "mikejohn@gmail.com",
                "GRA", "Stand 2");
        cashierImp2 = new CashierImp(cashier2);
        customer1 = new Customer();
        customer2 = new Customer();
    }

    // A cashier has to be hired before they can add or dispense receipt to customers
    @Test
    public void dispenseReceipt() {
        manager.hire(cashier1);
        manager.hire(cashier2);

        cashierImp1.addProduct();
        customer1.addToCart("Mango", 3);
        customer1.addToCart("Orange", 15);
        customer1.addToCart("Apple", 5);

        // Only print's receipt if there is something in your cart
        assertTrue(cashierImp2.dispenseReceipt());
        // Empty cart test
        assertFalse(cashierImp1.dispenseReceipt());

    }

    @Test
    void addProduct() {
        manager.hire(cashier1);
        assertTrue(cashierImp1.addProduct());
    }
}
package com.store.implementations;

import com.store.models.Cashier;
import com.store.models.CustomerModel;
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

        customer1 = new Customer(new CustomerModel("Mike"));
        customer1.addMe();
        customer2 = new Customer(new CustomerModel("James"));
        customer2.addMe();
    }

    // A cashier has to be hired before they can add or dispense receipt to customers
    @Test
    public void SellByFIFO() {
        manager.hire(cashier1);
        manager.hire(cashier2);

        cashierImp1.addProduct();
        customer1.addToCart("Mango", 3);
        customer1.addToCart("Orange", 15);
        customer1.addToCart("Apple", 5);

        customer2.addToCart("Soldering iron", 8);
        customer2.addToCart("Hammer", 5);
        customer2.addToCart("Screw driver", 5);

        customer1.buy();
        customer2.buy();


        // Only print's receipt if there is something in your cart
        assertEquals("SUCCESSFUL", cashierImp2.sellByFIFO());

        // Empty cart test
        customer1.buy();
        assertEquals("UNSUCCESSFUL", cashierImp2.sellByFIFO());

        // Trying to Sell to a customer when you are not a cashier
        manager.fire(cashier2);
        assertEquals("NOT-A-STAFF", cashierImp2.sellByFIFO());
    }

    @Test
    public void SellByPriority() {
        manager.hire(cashier1);
        manager.hire(cashier2);

        cashierImp1.addProduct();
        customer1.addToCart("Mango", 3);
        customer1.addToCart("Orange", 15);
        customer1.addToCart("Apple", 5);

        customer2.addToCart("Soldering iron", 8);
        customer2.addToCart("Hammer", 5);
        customer2.addToCart("Screw driver", 5);

        customer1.buy();
        customer2.buy();


        // Only print's receipt if there is something in your cart
        assertEquals("SUCCESSFUL", cashierImp2.sellByPriority());

        // Empty cart test
        customer1.buy();
        assertEquals("UNSUCCESSFUL", cashierImp2.sellByPriority());

        // Trying to Sell to a customer when you are not a cashier
        manager.fire(cashier2);
        assertEquals("NOT-A-STAFF", cashierImp2.sellByPriority());
    }

    @Test
    void addProduct() {
        manager.hire(cashier1);
        assertTrue(cashierImp1.addProduct());
    }
}
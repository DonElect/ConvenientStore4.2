package com.store.implementations;

import com.store.models.Cashier;
import com.store.models.Manager;
import com.store.services.CashierServices;
import com.store.services.ManagerServices;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagerImpTest {
    Cashier cashier1 = new Cashier("Jane", "Mary",
            25, "D0002B02", "jamemary@gmail.com",
            "Ohen", "Stand 1");
    CashierServices cashierImp1 = new CashierImp(cashier1);
    Cashier cashier2 = new Cashier("Mike", "John",
            26, "D0003B02", "mikejohn@gmail.com",
            "GRA", "Stand 2");
    CashierImp cashierImp2 = new CashierImp(cashier2);
    Manager manager = new Manager();

    ManagerServices managerImp = new ManagerImp(manager);
    @Test
    void hire() {
        // testing to see if the cashier was hired
        assertTrue(managerImp.hire(cashier1));
        // setting cashier2 as hired and then trying to re-hire
        cashier2.setHired(true);
        assertFalse(managerImp.hire(cashier2));
    }

    @Test
    void fire() {
        // Testing to see if cashier1 can be fired
        managerImp.hire(cashier1);
        assertTrue(managerImp.fire(cashier1));

        // Setting cashier2 to fired and then and then trying to re-fire to get a return false
        cashier2.setHired(false);
        assertFalse(managerImp.fire(cashier2));
    }
}
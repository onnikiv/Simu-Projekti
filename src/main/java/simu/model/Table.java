package simu.model;

import java.util.HashMap;

public class Table {
    private HashMap<Integer, Customer> tables;
    private int tableAmount;

    public Table(int tableAmount) {
        this.tableAmount = tableAmount;
        this.tables = new HashMap<>();
        for (int i = 1; i <= tableAmount; i++) {
            tables.put(i, null);
        }
    }

    public boolean addCustomersToTable(Customer customer) {
        for (int i = 1; i <= tableAmount; i++) {
            if (tables.get(i) == null) {
                tables.put(i, customer);
                return true;
            }
        }
        return false;

    }

    public boolean getFreeTables() {
        for (int i = 1; i <= tableAmount; i++) {
            if (tables.get(i) == null) {
                return true;
            }
        }
        return false;
    }


    public boolean removeCustomerFromTable(Customer customer) {
        for (int i = 1; i <= tableAmount; i++) {
            if (tables.get(i) != null && tables.get(i).equals(customer)) {
                tables.put(i, null);
                return true;
            }
        }
        return false;
    }

}


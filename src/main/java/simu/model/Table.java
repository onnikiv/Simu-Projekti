package simu.model;

import java.util.HashMap;
import java.util.List;

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

    public boolean addCustomersToTable(List<Customer> customers) {
        int freeTables = 0;
        for (int i = 1; i <= tableAmount; i++) {
            if (tables.get(i) == null) {
                freeTables++;
            }
        }
        if (freeTables >= customers.size()) {
            int customerIndex = 0;
            for (int i = 1; i <= tableAmount && customerIndex < customers.size(); i++) {
                if (tables.get(i) == null) {
                    tables.put(i, customers.get(customerIndex));
                    customerIndex++;
                }
            }
            return true;
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


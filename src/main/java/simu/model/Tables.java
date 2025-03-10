package simu.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tables {
    private final HashMap<Integer, List<Customer>> tables;
    private final Map<Customer, Integer> customerTableMap; // Map to track customer seating
    private final int tableAmount;
    private int freeTables;
    private final int seatsPerTable;

    public Tables(int tableAmount, int seatsPerTable) {
        this.tableAmount = tableAmount;
        this.freeTables = tableAmount;
        this.seatsPerTable = seatsPerTable;
        this.tables = new HashMap<>();
        this.customerTableMap = new HashMap<>(); // Initialize the map
        for (int i = 1; i <= tableAmount; i++) {
            tables.put(i, new ArrayList<>());
        }
    }

    public int addCustomersToTable(List<Customer> customers) {
        for (Map.Entry<Integer, List<Customer>> entry : tables.entrySet()) {
            if (entry.getValue().isEmpty()) {
                if (customers.size() <= seatsPerTable) {
                    entry.getValue().addAll(customers);
                    for (Customer customer : customers) {
                        customerTableMap.put(customer, entry.getKey()); // Track the table for each customer
                    }
                    freeTables--;
                    return entry.getKey();
                }
            }
        }
        return -1; // No free table found
    }

    public boolean removeCustomerFromTable(Customer customer) {
        Integer tableNumber = customerTableMap.remove(customer); // Get the table number from the map
        if (tableNumber != null) {
            List<Customer> tableCustomers = tables.get(tableNumber);
            tableCustomers.remove(customer);
            if (tableCustomers.isEmpty()) {
                freeTables++;
                return true;
            }
        }
        return false;
    }

    public int getFreeTables() {
        return freeTables;
    }

    public int getTableAmount() {
        return tableAmount;
    }

    public void freeTables() {

    }
}


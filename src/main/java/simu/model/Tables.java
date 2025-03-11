package simu.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Tables Class that manages the tables in the restaurant.
 * @see Customer Class that is seated at the tables.
 *
 * @version 1.0
 */

public class Tables {
    private final HashMap<Integer, List<Customer>> tables;
    private final Map<Customer, Integer> customerTableMap; // Map to track customer seating
    private final int tableAmount;
    private int freeTables;
    private final int seatsPerTable;

    /**
     * Constructs a Tables object with the specified table amount and seats per table.
     *
     * @param tableAmount the amount of tables
     * @param seatsPerTable the amount of seats per table
     */

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

    /**
     * Adds a list of customers to a table.
     * @param customers the list of customers
     * @return the table number if the customers were seated, -1 otherwise
     */

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

    /**
     * Removes a customer from a table.
     * @param customer the customer to remove
     * @return true if the customer was removed, false otherwise
     */

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

    /**
     * Returns the amount of free tables.
     *
     * @return the amount of free tables
     */

    public int getFreeTables() {
        return freeTables;
    }

    /**
     * Returns the amount of tables.
     *
     * @return the amount of tables
     */

    public int getTableAmount() {
        return tableAmount;
    }

}


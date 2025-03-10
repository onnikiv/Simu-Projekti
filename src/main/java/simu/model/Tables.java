package simu.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tables {
    private final HashMap<Integer, List<Customer>> tables;
    private final int tableAmount;
    private int freeTables;
    private final int seatsPerTable;

    public Tables(int tableAmount, int seatsPerTable) {
        this.tableAmount = tableAmount;
        this.freeTables = tableAmount;
        this.seatsPerTable = seatsPerTable;
        this.tables = new HashMap<>();
        for (int i = 1; i <= tableAmount; i++) {
            tables.put(i, new ArrayList<>());
        }
    }

    public int addCustomersToTable(List<Customer> customers) {  // Add a group of customers to a table (returns int for indexing the table numbers)

        // ehkä void metodiks ja sit joku for.each Customer customer lisätään siihe pöytään 

        for (Map.Entry<Integer, List<Customer>> entry : tables.entrySet()) {    // Iterate through the tables
            if (entry.getValue().isEmpty()) {   // If the table is empty, add a group of customers to it
                if (customers.size() <= seatsPerTable) {    // If the group of customers fits the table
                    entry.getValue().addAll(customers); // Add all customers of a group to the table
                    freeTables--;   // Decrease the amount of free tables
                    return entry.getKey();
                }
            }
        }
        return -1; // No free table found
    }

    public void removeCustomerFromTable(Customer customer) {
        for (Map.Entry<Integer, List<Customer>> entry : tables.entrySet()) {    // Iterate through the tables
            if (entry.getValue().remove(customer)) {    // If the group of customers is found, remove them
                if (entry.getValue().isEmpty()) {   // If the table is empty, increase the amount of free tables
                    freeTables++;
                }
                break;  // Break the loop after the group of customers is found
            }
        }
    }

    public int getFreeTables() {
        return freeTables;
    }

    public int getTableAmount() {
        return tableAmount;
    }
}


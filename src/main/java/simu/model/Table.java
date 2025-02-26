package simu.model;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Table {
    private HashMap<Integer, List<Customer>> tables;
    private int tableAmount;

    public Table(int tableAmount) {
        this.tableAmount = tableAmount;
        this.tables = new HashMap<>();
        tables.put(tableAmount,  new ArrayList<>());
    }

    public void addCustomerToTable(Customer customer) {
        tables.put(customer.getId(), new ArrayList<>());
    }

}


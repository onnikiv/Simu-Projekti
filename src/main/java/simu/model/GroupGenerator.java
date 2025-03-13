package simu.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * GroupGenerator class that generates a group of customers.
 * @see Customer Class that is part of the group.
 *
 * @version 1.0
 */
public class GroupGenerator {
    private int groupId;

    /**
     * Constructs a GroupGenerator object with the specified group ID.
     *
     * @param maxSize Gets the randomly generated group size.
     * @return Returns the generated group of Customers.
     */
    public List<Customer> generateCustomerGroup(int maxSize) {
        int size = groupSize(maxSize);
        List<Customer> group = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            group.add(new Customer());
        }
        groupId++;

        for (Customer customer : group) {
            customer.setGroupId(groupId);
        }

        return group;
    }

    /**
     * Generates a random group size.
     * @param n Parameter for maximum group size.
     * @return Random group size.
     */
    public int groupSize(int n) {
        return new Random().nextInt(n) + 1;
    }
}

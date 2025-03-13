package simu.model;

import eduni.distributions.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The GroupGenerator class is responsible for generating groups of customers
 * with sizes determined by a normal distribution.
 */
public class GroupGenerator {
    private int groupId;
    private Normal normalDist;

    /**
     * Constructs a GroupGenerator with a normal distribution.
     * The mean is set to 2.5 and the standard deviation is set to 1,
     * favoring group sizes of 2 and 3.
     */
    public GroupGenerator() {
        this.normalDist = new Normal(2.5, 1);
    }

    /**
     * Generates a group of customers with a size determined by the normal distribution.
     *
     * @param maxSize the maximum size of the group
     * @return a list of customers in the group
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
     * Determines the size of the group using the normal distribution.
     * Ensures the size is within the valid range (1 to maxSize).
     *
     * @param maxSize the maximum size of the group
     * @return the size of the group
     */
    public int groupSize(int maxSize) {
        int size;
        do {
            size = (int) Math.round(normalDist.sample());
        } while (size < 1 || size > maxSize);
        return size;
    }
}
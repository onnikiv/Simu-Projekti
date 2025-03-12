package simu.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GroupGenerator {
    private int groupId;

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

    public int groupSize(int n) {
        return new Random().nextInt(n) + 1;
    }
}

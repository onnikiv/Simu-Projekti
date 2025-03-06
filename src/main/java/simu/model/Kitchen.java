package simu.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Kitchen {
    private final Map<Customer, Queue<MenuItem>> orderMap = new HashMap<>();

    public void receiveOrder(MenuItem item, Customer customer) {
        orderMap.computeIfAbsent(customer, k -> new LinkedList<>()).add(item);

        getPrepTime(item);
    }

    public Queue<MenuItem> getReadyMeal(Customer customer) {
        return orderMap.getOrDefault(customer, new LinkedList<>());
    }

    public boolean hasReadyMeals(Customer customer) {
        return orderMap.containsKey(customer) && !orderMap.get(customer).isEmpty();
    }

    public boolean customerHasOrders(Customer customer) {
        return orderMap.containsKey(customer) && !orderMap.get(customer).isEmpty();
    }

    public double getPrepTime(MenuItem item) {
        return item.getPrepTimeMinutes();
    }
}

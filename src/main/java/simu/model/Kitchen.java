package simu.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Kitchen {
    private final Queue<MenuItem> orderQueue = new LinkedList<>();
    private final Queue<MenuItem> readyMeals = new LinkedList<>();
    private final Map<MenuItem, Customer> orderMap = new HashMap<>();

    public void receiveOrder(MenuItem item, Customer customer) {
        orderQueue.add(item);
        orderMap.put(item, customer);
        prepareMeal();
    }

    public void prepareMeal() {
        if (!orderQueue.isEmpty()) {
            MenuItem item = orderQueue.poll();
            readyMeals.add(item);
            System.out.println("Meal prepared " + item.getName());
        }
    }

    public MenuItem getReadyMeal() {
        return readyMeals.poll();
    }

    public boolean hasReadyMeals() {
        return !readyMeals.isEmpty();
    }

    public Customer getMatchingMeal(MenuItem item) {
        return orderMap.get(item);
    }
}

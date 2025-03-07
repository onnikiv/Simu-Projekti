package simu.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Kitchen Class that receives orders from the Waiter and prepares the meals.
 * @see Waiter Class that delivers orders to the Kitchen.
 * @see MenuItem Items that are prepared in the Kitchen. Taken from the MenuDao.
 *
 * @version 1.0
 */
public class Kitchen {

    // Map of orders from the Customer. Queue of the Customer's orders so they can be processed in order.
    private final Map<Customer, Queue<MenuItem>> orderMap = new HashMap<>();

    /**
     * A method that receives the order from the Waiter and adds it to the orderMap.
     * @param item MenuItem order delivered by the Waiter.
     * @param customer Customer who the order belongs to.
     */
    public void receiveOrder(MenuItem item, Customer customer) {
        orderMap.computeIfAbsent(customer, k -> new LinkedList<>()).add(item);

        getPrepTime(item);
    }

    /**
     * A method that returns the order to the Waiter to be delivered to the Customer.
     * @param customer Customer that the order belongs to.
     * @return Queue of MenuItems that are ready to be delivered to the Customer.
     */
    public Queue<MenuItem> getReadyMeal(Customer customer) {
        return orderMap.getOrDefault(customer, new LinkedList<>());
    }

    /**
     * A method that checks if the Kitchen has orders ready for the Customer.
     * @param customer Customer that the order belongs to.
     * @return True if the Kitchen has orders ready for the Customer, false otherwise.
     */
    public boolean hasReadyMeals(Customer customer) {
        return orderMap.containsKey(customer) && !orderMap.get(customer).isEmpty();
    }

    /**
     * @param item MenuItem that the Kitchen is preparing.
     * @return Prep time for the MenuItem in minutes. (To be added to the simulation time)
     */
    public double getPrepTime(MenuItem item) {
        return item.getPrepTimeMinutes();
    }
}

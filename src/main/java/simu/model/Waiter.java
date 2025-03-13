package simu.model;

import simu.framework.Clock;
import java.util.*;

/**
 * Waiter class that takes orders from the Customer and delivers them to the Kitchen.
 * @see Kitchen
 *
 * @version 1.0
 */
public class Waiter {
    private final Kitchen kitchen;  // Instance of Kitchen

    /**
     * Constructor for Waiter.
     * @param kitchen Link between Waiter and Kitchen.
     */
    public Waiter(Kitchen kitchen) {
        this.kitchen = kitchen;
    }

    /**
     * Order from the customer to be sent to the Kitchen.
     * @param item MenuItem order from the Customer to be sent to the Kitchen.
     * @param customer Customer who made the order.
     */
    public void takeOrder(MenuItem item, Customer customer) {
        kitchen.receiveOrder(item, customer);
        System.out.println("Order received: " + item.getName());
    }

    /**
     * Delivers the order from the Kitchen to the Customer.
     * Adds the time it took to make the meal for the simulation time.
     * @param customer Customer who needs to be served.
     * @return List of ordered MenuItems. Used if the Customer has multiple orders.
     */
    public List<MenuItem> deliverOrder(Customer customer) {
        List<MenuItem> deliveredItems = new ArrayList<>();  // Creates a new list for delivered items
        if (kitchen.hasReadyMeals(customer)) {  // Check if the Kitchen has orders ready for the Customer
            Queue<MenuItem> order = kitchen.getReadyMeal(customer); // Get the order from the kitchen
            while (!order.isEmpty()) {  // Process all items in the order
                MenuItem item = order.poll();   // Get the first item from the order
                deliveredItems.add(item);   // Add the item to the list of delivered items
                System.out.println("Delivered: " + item.getName());
            }
        } else {
            System.out.println("No orders available for customer: " + customer.getId());
        }
        return deliveredItems;  // Return the list of delivered items
    }


    /**
     * Calculate the preparation time for the group of customers.
     * Favours the groups with fewer customers, because of smaller chance of having to wait for extra orders.
     * @param group List of customers in the group.
     * @return Maximum preparation time for the group.
     */
    public double calculatePrepTime(List<Customer> group) {
        double maxPrepTime = 0;

        for (Customer customer : group) {
            double customerPrepTime = 0;
            if (kitchen.hasReadyMeals(customer)) {
                Queue<MenuItem> order = kitchen.getReadyMeal(customer);
                for (MenuItem item : order) {
                    double prepTime = kitchen.getPrepTime(item);
                    customerPrepTime += prepTime;
                }
            } else {
                System.out.println("No ready meals for customer: " + customer.getId());
            }
            if (customerPrepTime > maxPrepTime) {
                maxPrepTime = customerPrepTime;
            }
        }

        //System.out.println("Max prep time for group: " + maxPrepTime);
        return maxPrepTime;
    }
}

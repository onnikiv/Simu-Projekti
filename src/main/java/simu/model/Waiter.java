package simu.model;

import simu.Dao.MenuDao;
import simu.framework.Clock;

import java.util.*;

public class Waiter {
    private final Kitchen kitchen;  // Instance of Kitchen
    private boolean available;
    private final PriorityQueue<CustomerOrder> orderQueue;

    public Waiter(Kitchen kitchen) {
        this.kitchen = kitchen;
        this.available = true;
        this.orderQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.getCustomer().getId()));
    }

    private void processOrder(MenuItem item, Customer customer) {
        available = false;
        kitchen.receiveOrder(item, customer);
        Clock.getInstance().setTime(Clock.getInstance().getTime() + OwnEngine.randChance(3));
        System.out.println("Order received: " + item.getName());
        available = true;
        serveNextCustomer();
    }

    private void processDelivery(Customer customer, List<MenuItem> deliveredItems) {
        available = false;
        if (kitchen.hasReadyMeals(customer)) {
            Queue<MenuItem> order = kitchen.getReadyMeal(customer);
            while (!order.isEmpty()) {
                MenuItem item = order.poll();
                Clock.getInstance().setTime(Clock.getInstance().getTime() + kitchen.getPrepTime(item));
                deliveredItems.add(item);
                System.out.println("Delivered: " + item.getName());
            }
        } else {
            System.out.println("No orders available for customer: " + customer.getId());
        }
        available = true;
        serveNextCustomer();
    }

    public void takeOrder(MenuItem item, Customer customer) {
        if (available) {
            processOrder(item, customer);
        } else {
            orderQueue.add(new CustomerOrder(customer, item));
            System.out.println("Waiter unavailable");
        }
    }

    public List<MenuItem> deliverOrder(Customer customer) {
        List<MenuItem> deliveredItems = new ArrayList<>();
        if (available) {
            processDelivery(customer, deliveredItems);
        } else {
            orderQueue.add(new CustomerOrder(customer, null));
            System.out.println("Waiter unavailable");
        }

        return deliveredItems;
    }

    public void serveNextCustomer() {
        if (!orderQueue.isEmpty()) {
            CustomerOrder nextOrder = orderQueue.poll();
            if (nextOrder.getOrder() != null) {
                takeOrder(nextOrder.getOrder(), nextOrder.getCustomer());
            } else if (kitchen.customerHasOrders(nextOrder.getCustomer())){
                deliverOrder(nextOrder.getCustomer());
            }
        }
    }

    public void queueForDelivery(Customer customer) {
        orderQueue.add(new CustomerOrder(customer, null));
    }

    public boolean isAvailable() {
        return available;
    }
}

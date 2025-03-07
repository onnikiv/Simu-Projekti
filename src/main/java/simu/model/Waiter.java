package simu.model;

import simu.Dao.MenuDao;
import simu.framework.Clock;

import java.util.*;

public class Waiter {
    private final Kitchen kitchen;  // Instance of Kitchen
    private final PriorityQueue<CustomerOrder> orderQueue;

    public Waiter(Kitchen kitchen) {
        this.kitchen = kitchen;
        this.orderQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.getCustomer().getId()));
    }

    public void takeOrder(MenuItem item, Customer customer) {
        kitchen.receiveOrder(item, customer);
        Clock.getInstance().setTime(Clock.getInstance().getTime() + OwnEngine.randChance(3));
        System.out.println("Order received: " + item.getName());
        serveNextCustomer();
    }

    public List<MenuItem> deliverOrder(Customer customer) {
        List<MenuItem> deliveredItems = new ArrayList<>();
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
        serveNextCustomer();
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
}

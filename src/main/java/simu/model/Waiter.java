package simu.model;

public class Waiter {
    private final Kitchen kitchen;
    private boolean available;

    public Waiter(Kitchen kitchen) {
        this.kitchen = kitchen;
        this.available = true;
    }

    public void takeOrder(MenuItem item, Customer customer) {
        kitchen.receiveOrder(item, customer);
        System.out.println("Order received: " + item.getName());
    }

    public void getOrder() {
        if (available && kitchen.hasReadyMeals()) {
            MenuItem item = kitchen.getReadyMeal();
            Customer customer = kitchen.getMatchingMeal(item);
            deliverOrder(item, customer);
        }
    }

    public void deliverOrder(MenuItem item, Customer customer) {
        available = false;
        System.out.println("Order delivered: " + item.getName() + " to Customer " + customer.getId());
        available = true;
    }

    public boolean isAvailable() {
        return available;
    }
}

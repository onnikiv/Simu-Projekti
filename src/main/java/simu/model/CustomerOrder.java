package simu.model;

public class CustomerOrder implements Comparable<CustomerOrder>{
    private final Customer customer;
    private final MenuItem order;

    public CustomerOrder(Customer customer, MenuItem order) {
        this.customer = customer;
        this.order = order;
    }

    public Customer getCustomer() {
        return customer;
    }

    public MenuItem getOrder() {
        return order;
    }

    @Override
    public int compareTo(CustomerOrder o) {
        return Integer.compare(this.getCustomer().getId(), o.getCustomer().getId());
    }
}

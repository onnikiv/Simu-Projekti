package controller;

/**
 * Interface for the controller that provides methods for the simulation engine.
 */

public interface IControllerForM {

    // Rajapinta, joka tarjotaan moottorille:

    /**
     * Shows the end time of the simulation.
     * @param time the end time of the simulation
     */

    public void showEndTime(double time);

    /**
     * Visualizes the specified customer.
     * @param customer the customer to visualize
     */

    public void visualizeCustomer(int customer);;

    /**
     * Removes the specified customer from the visualization.
     * @param customer the customer to remove
     */

    public void visualizeRemoveCustomers(int customer);

    /**
     * Updates the service point sums with the specified values.
     * @param c0 the sum of customers at service point 0
     * @param c1 the sum of customers at service point 1
     * @param c2 the sum of customers at service point 2
     * @param c3 the sum of customers at service point 3
     * @param c4 the sum of customers at service point 4
     * @param c5 the sum of customers at service point 5
     */

    public void updateServicePointSums(int c0, int c1, int c2, int c3,int c4,int c5);
}
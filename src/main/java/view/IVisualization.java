package view;


/**
 * The IVisualization interface provides methods to visualize the simulation.
 */

public interface IVisualization {

	/**
	 * Clears the screen.
	 */

	public void clearScreen();

	/**
	 * Adds a new customer to the visualization.
	 * @param customer the customer to add
	 */

	public void newCustomer(int customer);

	/**
	 * Removes a customer from the visualization.
	 * @param customer the customer to remove
	 */

	public void removeCustomer(int customer);

}
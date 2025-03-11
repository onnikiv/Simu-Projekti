package view;

/**
 * The ISimulatorUI interface provides methods for the simulator user interface.
 */

public interface ISimulatorUI {

	// Kontrolleri tarvitsee syötteitä, jotka se välittää Moottorille

	/**
	 * Returns the current simulation time.
	 */

	public double getTime();

	/**
	 * Returns the delay between simulation steps.
	 */
	public long getDelay();

	//Kontrolleri antaa käyttöliittymälle tuloksia, joita Moottori tuottaa

	/**
	 * Sets the simulation end time.
	 *
	 * @param time the simulation end time
	 */

	public void setEndTime(double time);

	// Kontrolleri tarvitsee

	/**
	 * Returns the visualization of the simulation.
	 *
	 */

	public IVisualization getVisualization();
	
}
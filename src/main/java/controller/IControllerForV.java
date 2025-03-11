package controller;

/**
 * Interface for the controller that provides methods for the user interface.
 */

public interface IControllerForV {

    // Rajapinta, joka tarjotaan  käyttöliittymälle:

    /**
     * Starts the simulation.
     */

    public void startSimulation();

    /**
     * Speeds up the simulation.
     */
    public void speedUp();

    /**
     * Slows down the simulation.
     */

    public void slowDown();
}

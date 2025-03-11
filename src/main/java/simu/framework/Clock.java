package simu.framework;

/**
 * The Clock class is a singleton that keeps track of the simulation time.
 * It provides methods to set and get the current time.
 */

public class Clock {

	private double time;
	private static Clock instance;

	/**
	 * Private constructor to prevent instantiation.
	 * Initializes the time to 0.
	 */

	private Clock(){
		time = 0;
	}

	/**
	 * Returns the singleton instance of the Clock.
	 * If the instance does not exist, it creates one.
	 *
	 * @return the singleton instance of the Clock
	 */

	public static Clock getInstance(){
		if (instance == null){
			instance = new Clock();
		}
		return instance;
	}

	/**
	 * Sets the current time of the Clock.
	 *
	 * @param time the new time to set
	 */

	public void setTime(double time){
		this.time = time;
	}

	/**
	 * Returns the current time of the Clock.
	 *
	 * @return the current time
	 */

	public double getTime(){
		return time;
	}
}
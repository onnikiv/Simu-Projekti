package simu.framework;

/**
 * The Event class represents an event in the simulation.
 * It contains the event type and the time at which the event occurs.
 */

public class Event implements Comparable<Event> {

	private IEventType type;
	private double time;

	/**
	 * Constructs an Event with the specified event type and time.
	 *
	 * @param type the type of the event
	 * @param time the time at which the event occurs
	 */

	public Event(IEventType type, double time) {
		this.type = type;
		this.time = time;
	}

	/**
	 * Sets the type of the event.
	 *
	 * @param type the type of the event
	 */

	public void setType(IEventType type) {
		this.type = type;
	}

	/**
	 * Returns the type of the event.
	 *
	 * @return the type of the event
	 */

	public IEventType getType() {
		return type;
	}

	/**
	 * Sets the time at which the event occurs.
	 *
	 * @param time the time at which the event occurs
	 */

	public void setTime(double time) {
		this.time = time;
	}

	/**
	 * Returns the time at which the event occurs.
	 *
	 * @return the time at which the event occurs
	 */

	public double getTime() {
		return time;
	}

	/**
	 * Compares this event with the specified event based on the time.
	 *
	 * @param arg the event to compare with
	 * @return -1 if this event occurs before the specified event, 1 if it occurs after, and 0 if they occur at the same time
	 */

	@Override
	public int compareTo(Event arg) {
		if (this.time < arg.time) return -1;
		else if (this.time > arg.time) return 1;
		return 0;
	}

}
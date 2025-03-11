package simu.framework;

import java.util.PriorityQueue;

/**
 * The EventList class represents a list of events in the simulation.
 * It contains a priority queue of events.
 */

public class EventList {
    private PriorityQueue<Event> list = new PriorityQueue<Event>();

    /**
     * Constructs an empty EventList.
     */

    public EventList(){

    }

    /**
     * Removes and returns the next event in the list.
     *
     * @return the next event in the list
     */

    public Event remove(){
        return list.remove();
    }

    /**
     * Adds the specified event to the list.
     *
     * @param t the event to add
     */

    public void add(Event t){
        list.add(t);
    }

    /**
     * Gets the time of the next event in the list.
     *
     * @return the time of the next event
     */

    public double getNextTime(){
        return list.peek().getTime();
    }


}
package simu.framework;

import eduni.distributions.ContinuousGenerator;

/**
 * The ArrivalProcess class is responsible for generating events based on a continuous distribution.
 * It uses a generator to sample the time for the next event and adds it to the event list.
 */

public class ArrivalProcess {

    private ContinuousGenerator generator;
    private EventList eventList;
    private IEventType type;

    /**
     * Constructs an ArrivalProcess with the specified generator, event list, and event type.
     *
     * @param g the continuous generator used to sample the time for the next event
     * @param tl the event list to which the generated events will be added
     * @param type the type of events to be generated
     */

    public ArrivalProcess(ContinuousGenerator g, EventList tl, IEventType type){
        this.generator = g;
        this.eventList = tl;
        this.type = type;
    }

    /**
     * Generates the next event based on the continuous generator and adds it to the event list.
     * The event time is calculated as the current time plus a sampled value from the generator.
     */

    public void generateNext(){
        Event t = new Event(type, Clock.getInstance().getTime() + generator.sample());
        eventList.add(t);
    }
}
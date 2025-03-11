package simu.model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import eduni.distributions.ContinuousGenerator;
import simu.framework.Clock;
import simu.framework.Event;
import simu.framework.EventList;
import simu.framework.Trace;


// TODO:
// Palvelupistekohtaiset toiminnallisuudet, laskutoimitukset (+ tarvittavat muuttujat) ja raportointi koodattava

/**
 * The ServicePoint class represents a service point in the simulation.
 * It contains a queue of customers and a generator for service times.
 */

public class ServicePoint {

	private final LinkedList<List<Customer>> queue = new LinkedList<>(); // Tietorakennetoteutus jonolle (FIFO) (sisältää listan asiakkaita)
	private  ContinuousGenerator generator;
	private final EventList eventList;
	private final EventType scheduledEventType;

	//JonoStartegia strategia; //optio: asiakkaiden järjestys

	private boolean reserved = false;

	/**
	 * Constructs a ServicePoint with the specified generator and event list.
	 *
	 * @param generator the continuous generator used to sample the service time
	 * @param eventList the event list to which the service events will be added
	 */

	public ServicePoint(ContinuousGenerator generator, EventList eventList, EventType type){
		this.eventList = eventList;
		this.generator = generator;
		this.scheduledEventType = type;

	}

	/**
	 * Adds the specified customer to the queue.
	 *
	 * @param a the customer to add
	 */

	public void addToQueue(List<Customer> a){   // Jonon 1. asiakas aina palvelussa
		queue.add(a);
	}


	/**
	 * Removes and returns the next customer from the queue.
	 *
	 * @return the next customer in the queue
	 */

	public List<Customer> fetchFromQueue(){  // Poistetaan palvelussa ollut
		reserved = false;
		return queue.poll();
	}

	/**
	 * Returns the size of the queue.
	 *
	 * @return the size of the queue
	 */

	public int getQueueSize() {
		return queue.size();
	}

	/**
	 * Begins a new service, with the customer waiting in the queue during the service.
	 *
	 */

	public void beginService(){  //Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana

		Trace.out(Trace.Level.INFO, "ALOITETAAN UUSI SERVICE," + queue.getClass() + " --> " + scheduledEventType); // lisätty mihin eventtiin asiakas siirtyy

		reserved = true;
		double serviceTime = generator.sample();
		Trace.out(Trace.Level.INFO, "Service time: " + serviceTime + " for event: " + scheduledEventType);
		eventList.add(new Event(scheduledEventType,Clock.getInstance().getTime()+serviceTime));
	}

	/**
	 * Returns true if the service point is reserved.
	 *
	 * @return true if the service point is reserved
	 */

	public boolean isReserved(){
		return reserved;
	}

	/**
	 * Returns true if the queue is not empty.
	 *
	 * @return true if the queue is not empty
	 */

	public boolean isInQueue(){
		return !queue.isEmpty();
	}

	/**
	 * Updates the service time distribution.
	 *
	 * @param newGenerator the new continuous generator for service times
	 */

	public void updateDistribution(ContinuousGenerator newGenerator) {
		this.generator = newGenerator;
	}

	/**
	 * Returns the queue.
	 * @return queue
	 */

    public Collection<List<Customer>> getQueue() {
		return queue;
    }
}
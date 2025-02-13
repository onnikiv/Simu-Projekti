
package simu.model;

import simu.framework.*;
import java.util.LinkedList;
import eduni.distributions.ContinuousGenerator;

// TODO:
// Palvelupistekohtaiset toiminnallisuudet, laskutoimitukset (+ tarvittavat muuttujat) ja raportointi koodattava
public class ServicePoint {

	private final LinkedList<Customer> queue = new LinkedList<>(); // Tietorakennetoteutus
	private final ContinuousGenerator generator;
	private final EventList eventList;
	private final EventType scheduledEventType;

	//JonoStartegia strategia; //optio: asiakkaiden järjestys

	private boolean reserved = false;


	public ServicePoint(ContinuousGenerator generator, EventList eventList, EventType type){
		this.eventList = eventList;
		this.generator = generator;
		this.scheduledEventType = type;

	}


	public void addToQueue(Customer a){   // Jonon 1. asiakas aina palvelussa
		queue.add(a);

	}


	public Customer fetchFromQueue(){  // Poistetaan palvelussa ollut
		reserved = false;
		return queue.poll();
	}


	public void beginService(){  //Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana

		Trace.out(Trace.Level.INFO, "Starting new service for customer  " + queue.peek().getId());

		reserved = true;
		double serviceTime = generator.sample();
		eventList.add(new Event(scheduledEventType,Clock.getInstance().getTime()+serviceTime));
	}



	public boolean isReserved(){
		return reserved;
	}



	public boolean isInQueue(){
		return !queue.isEmpty();
	}

	public void serveCustomer(Customer a) {		// Placeholder for testing purposes
		if (EventType.MAINCOURSE == EventType.valueOf("MAINCOURSE")) {
			System.out.println("Serving Customer" + a.getId() + " kitchen.getOrder()");
		} else if (EventType.APPETIZER == EventType.valueOf("APPETIZER")) {
			System.out.println("Serving Customer" + a.getId() + " kitchen.getOrder()");
		} else if (EventType.DESSERT == EventType.valueOf("DESERT")) {
			System.out.println("Serving Customer" + a.getId() + " kitchen.getOrder()");
		}
	}

}

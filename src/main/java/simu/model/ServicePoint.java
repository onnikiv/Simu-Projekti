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
public class ServicePoint {

	private final LinkedList<List<Customer>> queue = new LinkedList<>(); // Tietorakennetoteutus jonolle (FIFO) (sisältää listan asiakkaita)
	private  ContinuousGenerator generator;
	private final EventList eventList;
	private final EventType scheduledEventType;

	//JonoStartegia strategia; //optio: asiakkaiden järjestys

	private boolean reserved = false;


	public ServicePoint(ContinuousGenerator generator, EventList eventList, EventType type){
		this.eventList = eventList;
		this.generator = generator;
		this.scheduledEventType = type;

	}


	public void addToQueue(List<Customer> a){   // Jonon 1. asiakas aina palvelussa
		queue.add(a);
	}




	public List<Customer> fetchFromQueue(){  // Poistetaan palvelussa ollut
		reserved = false;
		return queue.poll();
	}

	public int getQueueSize() {
		return queue.size();
	}


	public void beginService(){  //Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana

		Trace.out(Trace.Level.INFO, "ALOITETAAN UUSI SERVICE, ASIAKAS: " + queue.peek().size() + " --> " + scheduledEventType); // lisätty mihin eventtiin asiakas siirtyy

		reserved = true;
		double serviceTime = generator.sample();
		Trace.out(Trace.Level.INFO, "Service time: " + serviceTime + " for event: " + scheduledEventType);
		eventList.add(new Event(scheduledEventType,Clock.getInstance().getTime()+serviceTime));
	}



	public boolean isReserved(){
		return reserved;
	}



	public boolean isInQueue(){
		return !queue.isEmpty();
	}

	public void updateDistribution(ContinuousGenerator newGenerator) {
		this.generator = newGenerator;
	}

    public Collection<List<Customer>> getQueue() {
		return queue;
    }
}
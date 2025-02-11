package simu.framework;

import java.util.PriorityQueue;

public class EventList {
	private PriorityQueue<Event> list = new PriorityQueue<Event>();

	public EventList(){

	}

	public Event delete(){
		Trace.out(Trace.Level.INFO,"Deletion from event list " + list.peek().getType() + " " + list.peek().getTime() );
		return list.remove();
	}

	public void add(Event t){
		Trace.out(Trace.Level.INFO,"Adding this to event list " + t.getType() + " " + t.getTime());
		list.add(t);
	}

	public double getNextTime(){
		return list.peek().getTime();
	}


}
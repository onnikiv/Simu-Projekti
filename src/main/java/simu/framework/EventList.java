package simu.framework;

import java.util.PriorityQueue;

public class EventList {
    private PriorityQueue<Event> list = new PriorityQueue<Event>();

    public EventList(){

    }

    public Event remove(){
        return list.remove();
    }

    public void add(Event t){
        list.add(t);
    }

    public double getNextTime(){
        return list.peek().getTime();
    }


}
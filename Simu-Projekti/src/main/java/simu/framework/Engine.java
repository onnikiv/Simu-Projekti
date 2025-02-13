package simu.framework;

public abstract class Engine {

	private double simulationTime = 0;

	private Clock clock;

	protected EventList eventList;

	public Engine(){

		clock = Clock.getInstance(); // Otetaan clock muuttujaan yksinkertaistamaan koodia

		eventList = new EventList();

		// Palvelupisteet luodaan simu.model-pakkauksessa Moottorin aliluokassa


	}

	public void setSimulationTime(double time) {
		simulationTime = time;
	}


	public void drive(){
		init(); // luodaan mm. ensimmäinen tapahtuma
		while (simulating()){

			Trace.out(Trace.Level.INFO, "\nA-phase: time is " + currentTime());
			clock.setTime(currentTime());

			Trace.out(Trace.Level.INFO, "\nB-phase:" );
			runBEvents();

			Trace.out(Trace.Level.INFO, "\nC-phase:" );
			attemptCEvents();

		}
		results();

	}

	private void runBEvents(){
		while (eventList.getNextTime() == clock.getTime()){
			processEvent(eventList.delete());
		}
	}

	private double currentTime(){
		return eventList.getNextTime();
	}

	private boolean simulating(){
		return clock.getTime() < simulationTime;
	}

	protected abstract void processEvent(Event t);  // Määritellään simu.model-pakkauksessa Moottorin aliluokassa
	protected abstract void attemptCEvents();	// Määritellään simu.model-pakkauksessa Moottorin aliluokassa

	protected abstract void init(); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa

	protected abstract void results(); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa

}
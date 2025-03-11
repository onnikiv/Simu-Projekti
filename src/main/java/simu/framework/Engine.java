package simu.framework;


import controller.IControllerForM;

/**
 * The Engine class is an abstract class that represents the core of the simulation engine.
 * It extends the Thread class and implements the IEngine interface.
 * The engine manages the simulation time, event list, and the execution of events.
 */

public abstract class Engine extends Thread implements IEngine {  // UUDET MÄÄRITYKSET

    private double simulationTime = 0;
    private long delay = 0;

    private Clock clock;

    protected EventList eventList;

    protected IControllerForM controller; // UUSI

    /**
     * Constructs an Engine with the specified controller.
     *
     * @param controller the controller used to manage the simulation
     */

    public Engine(IControllerForM controller){  // UUSITTU

        this.controller = controller;  //UUSI

        clock = Clock.getInstance(); // Otetaan kello muuttujaan yksinkertaistamaan koodia

        eventList = new EventList();

        // Palvelupisteet luodaan simu.model-pakkauksessa Moottorin aliluokassa


    }

    /**
     * Sets the simulation time of the engine.
     *
     * @param time the new simulation time
     */

    @Override
    public void setSimulationTime(double time) {
        simulationTime = time;
    }

    /**
     * Sets the delay between simulation steps.
     *
     * @param delay the delay in milliseconds
     */

    @Override // UUSI
    public void setDelay(long delay) {
        this.delay = delay;
    }

    /**
     * Returns the delay between simulation steps.
     *
     * @return the delay in milliseconds
     */

    @Override // UUSI
    public long getDelay() {
        return delay;
    }

    /**
     * Runs the simulation engine.
     * The engine initializes the simulation, runs the B-phase events, and attempts the C-phase events.
     * The engine continues to run until the simulation time is reached.
     * The engine then displays the simulation results.
     * The engine also includes a delay between simulation steps.
     * The engine is implemented as a thread to allow for concurrent execution.
     *
     */

    @Override
    public void run(){ // Entinen aja()
        init(); // luodaan mm. ensimmäinen tapahtuma
        while (simulating()){
            delay(); // UUSI

            Trace.out(Trace.Level.INFO, "\nA-phase: time is " + currentTime());
            clock.setTime(currentTime());

            Trace.out(Trace.Level.INFO, "\nB-phase:" );
            runBEvents();

            Trace.out(Trace.Level.INFO, "\nC-phase:" );
            attemptCEvents();
        }
        results();

    }

    /**
     * Runs the B-phase events.
     * The engine runs the events in the event list that have the same time as the current time.
     * The engine removes the events from the event list and runs the events.
     *
     */

    private void runBEvents(){
        while (eventList.getNextTime() == clock.getTime()){
            runEvent(eventList.remove());
        }
    }

    /**
     * Attempts the C-phase events.
     * The engine attempts to run the C-phase events.
     * The engine is implemented in the subclasses of the engine.
     *
     */

    protected abstract void attemptCEvents();

    /**
     * Returns the current time of the engine.
     *
     * @return the current time
     */

    private double currentTime(){
        return eventList.getNextTime();
    }

    /**
     * Checks if the simulation is still running.
     * The engine checks if the current time is less than the simulation time.
     *
     * @return true if the simulation is still running, false otherwise
     */

    private boolean simulating(){
        //Trace.out(Trace.Level.INFO, "Kello on: " + clock.getTime());
        return clock.getTime() < simulationTime;
    }

    /**
     * Delays the simulation by the specified delay.
     *
     */

    private void delay() { // UUSI
        Trace.out(Trace.Level.INFO, "\nViive " + delay);
        try {
            sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the simulation.
     * The engine is implemented in the subclasses of the engine.
     *
     */

    protected abstract void init(); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa

    /**
     * Runs the specified event.
     * The engine is implemented in the subclasses of the engine.
     *
     * @param t the event to run
     */

    protected abstract void runEvent(Event t);  // Määritellään simu.model-pakkauksessa Moottorin aliluokassa

    /**
     * Displays the simulation results.
     * The engine is implemented in the subclasses of the engine.
     *
     */

    protected abstract void results(); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa

}
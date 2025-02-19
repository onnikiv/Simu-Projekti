package simu.framework;


import controller.IControllerForM;

public abstract class Engine extends Thread implements IEngine {  // UUDET MÄÄRITYKSET

    private double simulationTime = 0;
    private long delay = 0;

    private Clock clock;

    protected EventList eventList;

    protected IControllerForM controller; // UUSI


    public Engine(IControllerForM controller){  // UUSITTU

        this.controller = controller;  //UUSI

        clock = Clock.getInstance(); // Otetaan kello muuttujaan yksinkertaistamaan koodia

        eventList = new EventList();

        // Palvelupisteet luodaan simu.model-pakkauksessa Moottorin aliluokassa


    }

    @Override
    public void setSimulationTime(double time) {
        simulationTime = time;
    }

    @Override // UUSI
    public void setDelay(long delay) {
        this.delay = delay;
    }

    @Override // UUSI
    public long getDelay() {
        return delay;
    }

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

    private void runBEvents(){
        while (eventList.getNextTime() == clock.getTime()){
            runEvent(eventList.remove());
        }
    }

    protected abstract void attemptCEvents();


    private double currentTime(){
        return eventList.getNextTime();
    }

    private boolean simulating(){
        //Trace.out(Trace.Level.INFO, "Kello on: " + clock.getTime());
        return clock.getTime() < simulationTime;
    }


    private void delay() { // UUSI
        Trace.out(Trace.Level.INFO, "\nViive " + delay);
        try {
            sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected abstract void init(); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa

    protected abstract void runEvent(Event t);  // Määritellään simu.model-pakkauksessa Moottorin aliluokassa

    protected abstract void results(); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa

}
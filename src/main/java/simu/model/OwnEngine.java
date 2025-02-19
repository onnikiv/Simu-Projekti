package simu.model;

import simu.framework.*;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import controller.IControllerForM;

public class OwnEngine extends Engine {

    private ArrivalProcess arrivalProcess;

    private ServicePoint[] servicePoints;

    public OwnEngine(IControllerForM controller) {

        super(controller);

        servicePoints = new ServicePoint[3];

        servicePoints[0] = new ServicePoint(new Normal(10, 6), eventList, EventType.DEP1);
        servicePoints[1] = new ServicePoint(new Normal(10, 10), eventList, EventType.DEP2);
        servicePoints[2] = new ServicePoint(new Normal(5, 3), eventList, EventType.DEP3);

        arrivalProcess = new ArrivalProcess(new Negexp(15, 5), eventList, EventType.ARR1);

    }


    @Override
    protected void init() {
        arrivalProcess.generateNext(); // Ensimmäinen saapuminen järjestelmään
    }

    @Override
    protected void runEvent(Event t) {  // B-vaiheen tapahtumat

        Customer a;
        switch ((EventType) t.getType()) {

            case ARR1:
                servicePoints[0].addToQueue(new Customer());
                arrivalProcess.generateNext();
                controller.visualizeCustomer();

                break;
            case DEP1:
                a = (Customer) servicePoints[0].fetchFromQueue();
                servicePoints[1].addToQueue(a);
                break;
            case DEP2:
                a = (Customer) servicePoints[1].fetchFromQueue();
                servicePoints[2].addToQueue(a);
                break;
            case DEP3:
                a = (Customer) servicePoints[2].fetchFromQueue();
                a.setDepartTime(Clock.getInstance().getTime());
                a.report();
        }
    }

    @Override
    protected void attemptCEvents() {
        for (ServicePoint p : servicePoints) {
            if (!p.isReserved() && p.isInQueue()) {
                p.beginService();
            }
        }
    }

    @Override
    protected void results() {
        System.out.println("Simulation ended at " + Clock.getInstance().getTime());
        System.out.println("Results ... are not yet here");

        controller.showEndTime(Clock.getInstance().getTime());
    }


}
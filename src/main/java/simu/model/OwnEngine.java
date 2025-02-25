package simu.model;

import controller.ControllerForFxml;
import controller.IControllerForM;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import simu.framework.ArrivalProcess;
import simu.framework.Clock;
import simu.framework.Engine;
import simu.framework.Event;

public class OwnEngine extends Engine {

    private ArrivalProcess arrivalProcess;

    private ServicePoint[] servicePoints;
    private ControllerForFxml controllerFxml;


    public OwnEngine(IControllerForM controller,  ControllerForFxml controllerFxml) {

        super(controller);
        this.controllerFxml = controllerFxml;


        servicePoints = new ServicePoint[4];

        servicePoints[0] = new ServicePoint(new Normal(10, 10), eventList, EventType.PÖYTIINOHJAUS);
        servicePoints[1] = new ServicePoint(new Normal(5, 3), eventList, EventType.TILAAMINEN);
        servicePoints[2] = new ServicePoint(new Normal(10, 6), eventList, EventType.TARJOILU);
        servicePoints[3] = new ServicePoint(new Normal(10, 6), eventList, EventType.POISTUMINEN);

        arrivalProcess = new ArrivalProcess(new Negexp(15, 5), eventList, EventType.SAAPUMINEN);
        
        // SAAPUMINEN, PÖYTIINOHJAUS, TILAAMINEN, TARJOILU, POISTUMINEN;
    }


    @Override
    protected void init() {
        arrivalProcess.generateNext(); // Ensimmäinen saapuminen järjestelmään
    }

    @Override
    protected void runEvent(Event t) {  // B-vaiheen tapahtumat

        Customer a;
        switch ((EventType) t.getType()) {

            case SAAPUMINEN:
                servicePoints[0].addToQueue(new Customer());
                arrivalProcess.generateNext();
                controller.visualizeCustomer();
                break;

            case PÖYTIINOHJAUS:
                a = (Customer) servicePoints[0].fetchFromQueue();
                servicePoints[1].addToQueue(a); 
                System.out.print("ASIAKAS: " + a.getId() + " -> OHJATAAN PÖYTÄÄN\n"); // ONNIN DEBUG
                controllerFxml.updateTextArea("ASIAKAS: " + a.getId() + " -> OHJATAAN PÖYTÄÄN\n");
                break;

            case TILAAMINEN:
                a = (Customer) servicePoints[1].fetchFromQueue();
                servicePoints[2].addToQueue(a);
                System.out.print("ASIAKAS: " + a.getId() + " -> TILAA RUOKAA\n"); // ONNIN DEBUG
                controllerFxml.updateTextArea("ASIAKAS: " + a.getId() + " -> TILAA RUOKAA\n");
                break;

            case TARJOILU:
                a = (Customer) servicePoints[2].fetchFromQueue();
                servicePoints[3].addToQueue(a);
                System.out.print("ASIAKAS: " + a.getId() + " -> TARJOILLAAN PIHVI\n"); // ONNIN DEBUG
                controllerFxml.updateTextArea("ASIAKAS: " + a.getId() + " -> TARJOILLAAN PIHVI\n");
                break;
            
            case POISTUMINEN:
                a = (Customer) servicePoints[3].fetchFromQueue();
                a.setDepartTime(Clock.getInstance().getTime());
                a.report(this.controllerFxml);
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
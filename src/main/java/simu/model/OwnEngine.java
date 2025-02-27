package simu.model;

import simu.Dao.MenuDao;
import simu.datasource.MariaDbConnection;
import simu.model.MenuItem;

import controller.ControllerForFxml;
import controller.IControllerForM;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import simu.framework.ArrivalProcess;
import simu.framework.Clock;
import simu.framework.Engine;
import simu.framework.Event;
import simu.model.Table;

public class OwnEngine extends Engine {

    private ArrivalProcess arrivalProcess;

    private ServicePoint[] servicePoints;
    private ControllerForFxml controllerFxml;
    private Table table;


    public OwnEngine(IControllerForM controller, ControllerForFxml controllerFxml) {

        super(controller);
        this.controllerFxml = controllerFxml;
        table = new Table(10);


        servicePoints = new ServicePoint[5];

        servicePoints[0] = new ServicePoint(new Normal(5, 2), eventList, EventType.PÖYTIINOHJAUS);
        servicePoints[1] = new ServicePoint(new Normal(15, 5), eventList, EventType.TILAAMINEN);
        servicePoints[2] = new ServicePoint(new Normal(25, 10), eventList, EventType.TARJOILU);
        servicePoints[3] = new ServicePoint(new Normal(45, 15), eventList, EventType.SAFKAAMINEN);
        servicePoints[4] = new ServicePoint(new Normal(5, 2), eventList, EventType.POISTUMINEN);

        arrivalProcess = new ArrivalProcess(new Negexp(30, 10), eventList, EventType.SAAPUMINEN);

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
                controller.visualizeCustomer(0);
                break;

            case PÖYTIINOHJAUS:
                a = (Customer) servicePoints[0].fetchFromQueue();
                if (table.getFreeTables() == true) { // Tsekkaa onko vapaita pöytiä tarjolla
                    table.addCustomersToTable(a);
                    servicePoints[1].addToQueue(a);
                    controller.visualizeRemoveCustomers(0);
                    System.out.print("ASIAKAS: " + a.getId() + " -> OHJATAAN PÖYTÄÄN\n"); // ONNIN DEBUG
                    controllerFxml.updateTextArea("ASIAKAS: " + a.getId() + " -> OHJATAAN PÖYTÄÄN\n");
                    controller.visualizeCustomer(1);
                }else {
                    servicePoints[0].addToQueue(a);
                    System.out.print("Asiakas: " + a.getId() + " -> EI VAPAITA PÖYTIÄ\n");
                    controllerFxml.updateTextArea("Asiakas: " + a.getId() + " -> EI VAPAITA PÖYTIÄ\n");

                }
                break;

            case TILAAMINEN:
                a = (Customer) servicePoints[1].fetchFromQueue();
                controller.visualizeRemoveCustomers(1);
                servicePoints[2].addToQueue(a);
                System.out.print("ASIAKAS: " + a.getId() + " -> TILAA RUOKAA\n"); // ONNIN DEBUG
                controllerFxml.updateTextArea("ASIAKAS: " + a.getId() + " -> TILAA RUOKAA\n");
                controller.visualizeCustomer(2);
                break;

            case TARJOILU:
                a = (Customer) servicePoints[2].fetchFromQueue();
                controller.visualizeRemoveCustomers(2);
                servicePoints[3].addToQueue(a);
                System.out.print("ASIAKAS: " + a.getId() + " -> TARJOILLAAN PIHVI\n"); // ONNIN DEBUG
                controllerFxml.updateTextArea("ASIAKAS: " + a.getId() + " -> TARJOILLAAN PIHVI\n");
                controller.visualizeCustomer(3);
                break;

            case SAFKAAMINEN:
                a = (Customer) servicePoints[3].fetchFromQueue();
                controller.visualizeRemoveCustomers(3);
                servicePoints[4].addToQueue(a);
                System.out.print("Asiakas: " + a.getId() + " -> SYÖ PIHVIN\n");
                controllerFxml.updateTextArea("Asiakas: " + a.getId() + " -> SYÖ PIHVIN\n");
                controller.visualizeCustomer(4);
                break;

            case POISTUMINEN:
                controller.visualizeRemoveCustomers(4);
                a = (Customer) servicePoints[4].fetchFromQueue();
                table.removeCustomerFromTable(a);
                System.out.print("Asiakas: " + a.getId() + " -> POISTUU PÖYDÄSTÄ\n");
                table.getFreeTables();
                controller.visualizeCustomer(5);
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
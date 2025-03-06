package simu.model;

import controller.ControllerForFxml;
import controller.SettingsController;
import controller.IControllerForM;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import simu.Dao.MenuDao;
import simu.framework.ArrivalProcess;
import simu.framework.Clock;
import simu.framework.Engine;
import simu.framework.Event;

import java.util.ArrayList;
import java.util.List;

public class OwnEngine extends Engine {

    private ArrivalProcess arrivalProcess;

    private final ServicePoint[] servicePoints;
    private final ControllerForFxml controllerFxml;
    private SettingsController settingsController;
    private final Table table;
    private final MenuDao dao = new MenuDao();
    private final Kitchen kitchen = new Kitchen();
    private final Waiter waiter = new Waiter(kitchen);


    private boolean paused = false;
    private final Object pauseLock = new Object();

    //Default arvot Distributions Normal meanille
    private double meanArrival = 30;
    private double meanSeating = 5;
    private double meanOrdering = 15;
    private double meanService = 25;
    private double meanEating = 45;
    private double meanExiting = 5;


    public OwnEngine(IControllerForM controller, ControllerForFxml controllerFxml, SettingsController settingsController) {

        super(controller);
        this.controllerFxml = controllerFxml;
        this.settingsController = settingsController;
        table = new Table(10);


        servicePoints = new ServicePoint[5];

        servicePoints[0] = new ServicePoint(new Normal(meanSeating, 2), eventList, EventType.PÖYTIINOHJAUS);
        servicePoints[1] = new ServicePoint(new Normal(meanOrdering, 5), eventList, EventType.TILAAMINEN);
        servicePoints[2] = new ServicePoint(new Normal(meanService, 10), eventList, EventType.TARJOILU);
        servicePoints[3] = new ServicePoint(new Normal(meanEating, 15), eventList, EventType.SAFKAAMINEN);
        servicePoints[4] = new ServicePoint(new Normal(meanExiting, 2), eventList, EventType.POISTUMINEN);

        arrivalProcess = new ArrivalProcess(new Negexp(meanArrival, 10), eventList, EventType.SAAPUMINEN);

        // SAAPUMINEN, PÖYTIINOHJAUS, TILAAMINEN, TARJOILU, POISTUMINEN;
    }


    public void pauseSimulation() {
        synchronized (pauseLock) {
            paused = true;
        }
    }

    public void resumeSimulation() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll();
        }
    }


    //Service  Pointtien meanien vaihto (Vielä ei vaihda mitään mutta  printtaa  consoliin tuloksia)
    public void changeArrivalMean(double mean) {
        this.meanArrival = mean;
        this.arrivalProcess = new ArrivalProcess(new Negexp(mean, 10), eventList, EventType.SAAPUMINEN);
        System.out.println("Arrival mean changed  to: " + mean);
    }

    public void changeSeatingMean(double mean) {
        this.meanSeating = mean;
        servicePoints[0].updateDistribution(new Normal(mean, 2));
        System.out.println("Seating  mean change  to: " + mean);
    }

    public void changeOrderingMean(double mean) {
        this.meanOrdering = mean;
        servicePoints[1].updateDistribution(new Normal(mean, 5));
        System.out.println("Ordering  mean change  to: " + mean);
    }

    public void changeServiceMean(double mean) {
        this.meanService = mean;
        servicePoints[2].updateDistribution(new Normal(mean, 10));
        System.out.println("Service mean change  to: " + mean);
    }

    public void changeEatingMean(double mean) {
        this.meanEating = mean;
        servicePoints[3].updateDistribution(new Normal(mean, 15));
        System.out.println("Eating mean changed  to: " + mean);
    }

    public void changeExitingMean(double mean) {
        this.meanExiting = mean;
        servicePoints[4].updateDistribution(new Normal(mean, 2));
        System.out.println("Exiting  mean change  to: " + mean);
    }



    @Override
    protected void init() {
        arrivalProcess.generateNext(); // Ensimmäinen saapuminen järjestelmään
    }

    private List<Customer> generateCustomerGroup() {
        int groupSize = 1 + (int) (Math.random() * 4); // Generate a group size between 1 and 4
        List<Customer> group = new ArrayList<>();
        for (int i = 0; i < groupSize; i++) {
            group.add(new Customer());
        }
        return group;
    }

    @Override
    protected void runEvent(Event t) {  // B-vaiheen tapahtumat
        synchronized (pauseLock) {
            while (paused) {
                try {
                    pauseLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        Customer a;
        switch ((EventType) t.getType()) {

            case SAAPUMINEN -> {
                List<Customer> group = generateCustomerGroup();
                for (Customer customer : group) {
                    servicePoints[0].addToQueue(customer);
                }
                arrivalProcess.generateNext();
                controller.visualizeCustomer(0);
            }

            case PÖYTIINOHJAUS -> {
                List<Customer> group = new ArrayList<>();
                int maxGroupSize = 4;
                for (int i = 0; i < maxGroupSize && i < servicePoints[0].getQueueSize(); i++) {
                    group.add((Customer) servicePoints[0].fetchFromQueue());
                }
                System.out.println(table.getFreeTables());
                if (table.getFreeTables()) {
                    table.addCustomersToTable(group);
                    for (Customer customer : group) {
                        servicePoints[1].addToQueue(customer);
                        controller.visualizeRemoveCustomers(0);
                        System.out.print("ASIAKAS: " + customer.getId() + " -> OHJATAAN PÖYTÄÄN\n");
                        controllerFxml.updateTextArea("ASIAKAS: " + customer.getId() + " -> OHJATAAN PÖYTÄÄN\n");
                        controller.visualizeCustomer(1);
                    }
                } else {
                    for (Customer customer : group) {
                        servicePoints[0].addToQueue(customer);
                    }
                    System.out.print("Asiakasryhmä -> EI VAPAITA PÖYTIÄ\n");
                    controllerFxml.updateTextArea("Asiakasryhmä -> EI VAPAITA PÖYTIÄ\n");
                }
            }

            case TILAAMINEN -> {
                a = (Customer) servicePoints[1].fetchFromQueue();
                controller.visualizeRemoveCustomers(1);
                servicePoints[2].addToQueue(a);
                MenuItem item = dao.getRandomStarter();
                a.order(waiter, item);
                System.out.print("ASIAKAS: " + a.getId() + " -> ´Tilaus: " + item.getName() + "\n"); // ONNIN DEBUG
                controllerFxml.updateTextArea("ASIAKAS: " + a.getId() + " -> Tilaus: " + item.getName() + "\n");
                controller.visualizeCustomer(2);
            }

            case TARJOILU -> {
                a = (Customer) servicePoints[2].fetchFromQueue();
                controller.visualizeRemoveCustomers(2);
                servicePoints[3].addToQueue(a);
                MenuItem readyMeal = kitchen.getReadyMeal();
                if (readyMeal != null) {
                    controllerFxml.updateTextArea("ASIAKKAALLE: " + a.getId() + " -> TARJOILLAAN: " + readyMeal.getName() + "\n");
                    System.out.print("ASIAKKAALLE: " + a.getId() + " -> TARJOILLAAN: " + readyMeal.getName() + "\n"); // ONNIN DEBUG
                    waiter.getOrder();
                } else {
                    System.out.println("No ready meals");
                }
                controller.visualizeCustomer(3);
            }

            case SAFKAAMINEN -> {
                a = (Customer) servicePoints[3].fetchFromQueue();
                controller.visualizeRemoveCustomers(3);
                servicePoints[4].addToQueue(a);
                System.out.print("Asiakas: " + a.getId() + " -> SYÖ PIHVIN\n");
                controllerFxml.updateTextArea("Asiakas: " + a.getId() + " -> SYÖ PIHVIN\n");
                controller.visualizeCustomer(4);
            }

            case POISTUMINEN -> {
                controller.visualizeRemoveCustomers(4);
                a = (Customer) servicePoints[4].fetchFromQueue();
                table.removeCustomerFromTable(a);  //  Poistaa asiakkaan pöytä listasta
                System.out.print("Asiakas: " + a.getId() + " -> POISTUU PÖYDÄSTÄ\n");
                table.getFreeTables();
                controller.visualizeCustomer(5);
                a.setDepartTime(Clock.getInstance().getTime());
                a.report(this.controllerFxml);
            }
        }
    }

    @Override
    protected void attemptCEvents() {
        synchronized (pauseLock) {
            while (paused) {
                try {
                    pauseLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

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
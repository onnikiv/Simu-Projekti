package simu.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import controller.ControllerForFxml;
import controller.IControllerForM;
import controller.SettingsController;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import simu.Dao.MenuDao;
import simu.framework.ArrivalProcess;
import simu.framework.Clock;
import simu.framework.Engine;
import simu.framework.Event;

public class OwnEngine extends Engine {

    private ArrivalProcess arrivalProcess;

    private final ServicePoint[] servicePoints;
    private final ControllerForFxml controllerFxml;
    private SettingsController settingsController;
    private final Tables table;
    private final OrderService orderService;
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
        table = new Tables(10, 4);


        servicePoints = new ServicePoint[5];

        servicePoints[0] = new ServicePoint(new Normal(meanSeating, 2), eventList, EventType.PÖYTIINOHJAUS);
        servicePoints[1] = new ServicePoint(new Normal(meanOrdering, 5), eventList, EventType.TILAAMINEN);
        servicePoints[2] = new ServicePoint(new Normal(meanService, 10), eventList, EventType.TARJOILU);
        servicePoints[3] = new ServicePoint(new Normal(meanEating, 15), eventList, EventType.SAFKAAMINEN);
        servicePoints[4] = new ServicePoint(new Normal(meanExiting, 2), eventList, EventType.POISTUMINEN);

        arrivalProcess = new ArrivalProcess(new Negexp(meanArrival, 10), eventList, EventType.SAAPUMINEN);

        this.orderService = new OrderService(new MenuDao());

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

    public static int randChance(int chance) {
        return new Random().nextInt(chance);
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

        List<Customer> a;
        switch ((EventType) t.getType()) {

            case SAAPUMINEN -> {
                a = generateCustomerGroup();
                servicePoints[0].addToQueue(a);
                controller.visualizeCustomer(0);
                arrivalProcess.generateNext();
            }

            case PÖYTIINOHJAUS -> {
                a = servicePoints[0].fetchFromQueue();
                if (table.getFreeTables() > 0) {
                    int tableNumber = table.addCustomersToTable(a);
                    for (Customer customer : a) {
                        servicePoints[1].addToQueue(a);
                        controller.visualizeRemoveCustomers(0);
                        if (tableNumber > 0) {
                            System.out.print("ASIAKAS: " + customer.getId() + " -> OHJATAAN PÖYTÄÄN " + tableNumber + "\n");
                            controllerFxml.updateTextArea("ASIAKAS: " + customer.getId() + " -> OHJATAAN PÖYTÄÄN " + tableNumber + "\n");
                            controller.visualizeCustomer(1);
                        }
                    }
                } else {
                    servicePoints[0].addToQueue(a);
                    System.out.print("Asiakasryhmä -> EI VAPAITA PÖYTIÄ\n");
                    controllerFxml.updateTextArea("Asiakasryhmä -> EI VAPAITA PÖYTIÄ\n");

                }
            }

            case TILAAMINEN -> {
                a = servicePoints[1].fetchFromQueue();
                controller.visualizeRemoveCustomers(1);
                servicePoints[2].addToQueue(a);
                for (Customer customer : a) {
                    if (!customer.hasOrdered()) {
                        if (randChance(100) >= 33) {
                            MenuItem starter = orderService.getRandomStarter();
                            customer.order(waiter, starter);
                            System.out.print("ASIAKAS: " + customer.getId() + " -> Tilaus: " + starter.getName() + "\n");
                            controllerFxml.updateTextArea("ASIAKAS: " + customer.getId() + " -> Tilaus: " + starter.getName() + "\n");
                        }
                        MenuItem mainMeal = orderService.getRandomMainMeal();
                        customer.order(waiter, mainMeal);
                        System.out.print("ASIAKAS: " + customer.getId() + " -> Tilaus: " + mainMeal.getName() + "\n");
                        controllerFxml.updateTextArea("ASIAKAS: " + customer.getId() + " -> Tilaus: " + mainMeal.getName() + "\n");
                        if (randChance(100) >= 66) {
                            MenuItem dessert = orderService.getRandomDessert();
                            customer.order(waiter, dessert);
                            System.out.print("ASIAKAS: " + customer.getId() + " -> Tilaus: " + dessert.getName() + "\n");
                            controllerFxml.updateTextArea("ASIAKAS: " + customer.getId() + " -> Tilaus: " + dessert.getName() + "\n");
                        }
                        customer.setHasOrdered(true);
                    }
                }
                controller.visualizeCustomer(2);
            }

            case TARJOILU -> {
                a = servicePoints[2].fetchFromQueue();
                controller.visualizeRemoveCustomers(2);
                servicePoints[3].addToQueue(a);
                for (Customer customer : a) {
                    List<MenuItem> order = waiter.deliverOrder(customer);
                    for (MenuItem item : order) {
                        System.out.print("ASIAKAS: " + customer.getId() + " -> RUOKA: " + item.getName() + "\n");
                        controllerFxml.updateTextArea("ASIAKAS: " + customer.getId() + " -> RUOKA: " + item.getName() + "\n");
                    }
                }
                controller.visualizeCustomer(3);
            }

            case SAFKAAMINEN -> {
                a = servicePoints[3].fetchFromQueue();
                controller.visualizeRemoveCustomers(3);
                servicePoints[4].addToQueue(a);
                controller.visualizeCustomer(4);
            }

            case POISTUMINEN -> {
                controller.visualizeRemoveCustomers(4);
                a = servicePoints[4].fetchFromQueue();
                for (Customer customer : a) {
                    if (!customer.isLeaving()) {
                        table.removeCustomerFromTable(customer);
                        System.out.print("Asiakas: " + customer.getId() + " -> POISTUU PÖYDÄSTÄ\n");
                        System.out.println(table.getFreeTables());
                        controller.visualizeCustomer(5);
                        customer.setDepartTime(Clock.getInstance().getTime());
                        customer.report(this.controllerFxml);
                        customer.setLeaving(true);
                    }
                }
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
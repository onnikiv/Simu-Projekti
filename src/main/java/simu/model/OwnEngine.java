package simu.model;

import java.util.ArrayList;
import java.util.HashMap;
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

/**
 * The OwnEngine class represents the simulation engine for the restaurant simulation.
 * It extends the Engine class and contains the main logic for the simulation.
 * The engine manages the simulation time, event list, and the execution of events.
 * The engine also contains the service points and the arrival process for the simulation.
 * The engine is responsible for generating events and running the simulation.
 * The engine also calculates performance metrics for the simulation.
 * The engine is used by the controller to manage the simulation.
 *
 */

public class OwnEngine extends Engine {

    private ArrivalProcess arrivalProcess;

    private final ServicePoint[] servicePoints;
    private final ControllerForFxml controllerFxml;
    private SettingsController settingsController;
    private final Tables table;
    private final OrderService orderService;
    private final Kitchen kitchen = new Kitchen();
    private final Waiter waiter = new Waiter(kitchen);
    private int groupId = 0;
    private final HashMap<Integer, Double> groupPrepTimes = new HashMap<>();

    private boolean paused = false;
    private final Object pauseLock = new Object();

    //Default arvot Distributions Normal meanille
    private double meanArrival = 30;
    private double meanSeating = 5;
    private double meanOrdering = 15;
    private double meanService = 5;
    private double meanEating = 15;
    private double meanExiting = 5;

    private int c0 = 0;
    private int c1 = 0;
    private int c2 = 0;
    private int c3 = 0;
    private int c4 = 0;
    private int c5 = 0;

    private double timeInSystem = 0;
    private double totalQueueTime = 0;
    private double totalServiceTime = 0;
    private int totalCustomers = 0;
    private double maxQueueTime = Double.MIN_VALUE;
    private double minQueueTime = Double.MAX_VALUE;
    private double maxServiceTime = Double.MIN_VALUE;
    private double minServiceTime = Double.MAX_VALUE;


    /**
     * Constructs an OwnEngine with the specified controller, controller for FXML, and settings controller.
     * The engine also creates the service points and the arrival process for the simulation.
     *
     * @param controller the controller used to manage the simulation
     * @param controllerFxml the controller for FXML used to manage the simulation
     * @param settingsController the settings controller used to manage the simulation settings
     */

    public OwnEngine(IControllerForM controller, ControllerForFxml controllerFxml, SettingsController settingsController) {

        super(controller);
        this.controllerFxml = controllerFxml;
        this.settingsController = settingsController;
        table = new Tables(20, 4);


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

    /**
     * Pauses the simulation.
     */

    public void pauseSimulation() {
        synchronized (pauseLock) {
            paused = true;
        }
    }

    /**
     * Resumes the simulation.
     */

    public void resumeSimulation() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll();
        }
    }

    /**
     * Returns random number between 0 and chance.
     *
     * @param chance
     * @return random number
     */

    public int randChance(int chance) {
        return new Random().nextInt(chance);
    }

    /**
     * Changes the mean of the arrival process.
     *
     * @param mean
     */

    //Service  Pointtien meanien vaihto (Vielä ei vaihda mitään mutta  printtaa  consoliin tuloksia)
    public void changeArrivalMean(double mean) {
        this.meanArrival = mean;
        this.arrivalProcess = new ArrivalProcess(new Negexp(mean, 10), eventList, EventType.SAAPUMINEN);
        System.out.println("Arrival mean changed  to: " + mean);
    }

    /**
     * Changes the mean of the seating process.
     *
     * @param mean
     */

    public void changeSeatingMean(double mean) {
        this.meanSeating = mean;
        servicePoints[0].updateDistribution(new Normal(mean, 2));
        System.out.println("Seating  mean change  to: " + mean);
    }

    /**
     * Changes the mean of the ordering process.
     *
     * @param mean
     */

    public void changeOrderingMean(double mean) {
        this.meanOrdering = mean;
        servicePoints[1].updateDistribution(new Normal(mean, 5));
        System.out.println("Ordering  mean change  to: " + mean);
    }

    /**
     * Changes the mean of the service process.
     *
     * @param mean
     */

    public void changeServiceMean(double mean) {
        this.meanService = mean;
        servicePoints[2].updateDistribution(new Normal(mean, 10));
        System.out.println("Service mean change  to: " + mean);
    }

    /**
     * Changes the mean of the eating process.
     *
     * @param mean
     */

    public void changeEatingMean(double mean) {
        this.meanEating = mean;
        servicePoints[3].updateDistribution(new Normal(mean, 15));
        System.out.println("Eating mean changed  to: " + mean);
    }

    /**
     * Changes the mean of the exiting process.
     *
     * @param mean
     */

    public void changeExitingMean(double mean) {
        this.meanExiting = mean;
        servicePoints[4].updateDistribution(new Normal(mean, 2));
        System.out.println("Exiting  mean change  to: " + mean);
    }

    /**
     * Initializes the simulation.
     * Generates the first arrival in the system.
     */


    @Override
    protected void init() {
        arrivalProcess.generateNext(); // Ensimmäinen saapuminen järjestelmään
    }

    /**
     * Generates a group of customers.
     *
     * @return the group of customers
     */

    private List<Customer> generateCustomerGroup() {
        int groupSize = 1 + (int) (Math.random() * 4); // Generate a group size between 1 and 4
        List<Customer> group = new ArrayList<>();
        for (int i = 0; i < groupSize; i++) {
            group.add(new Customer());
        }
        groupId++;

        for (Customer customer : group) {
            customer.setGroupId(groupId);
        }

        return group;
    }


    /**
     * Runs the event.
     *
     * @param t the event to run
     */

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

        // lähettää kontrollerille arvot
        controller.updateServicePointSums(c0, c1, c2, c3, c4, c5);

        List<Customer> a;
        double currentTime = Clock.getInstance().getTime();

        switch ((EventType) t.getType()) {

            case SAAPUMINEN -> {
                a = generateCustomerGroup();
                for (Customer customer : a) {
                    customer.setArrivalTime(currentTime);
                }
                servicePoints[0].addToQueue(a);
                controller.visualizeCustomer(0);

                c0++;

                arrivalProcess.generateNext();

            }

            case PÖYTIINOHJAUS -> {
                if (table.getFreeTables() > 0) {
                    try {
                        a = servicePoints[0].fetchFromQueue();
                        for (Customer customer : a) {
                            double queueTime = currentTime - customer.getArrivalTime();
                            totalQueueTime += currentTime - customer.getArrivalTime();
                            totalCustomers++;
                            if (queueTime > maxQueueTime) maxQueueTime = queueTime;
                            if (queueTime < minQueueTime) minQueueTime = queueTime;
                        }
                        int tableNumber = table.addCustomersToTable(a);
                        if (tableNumber > 0) {
                            controller.visualizeRemoveCustomers(0);
                            int id = a.get(0).getGroupId();
                            System.out.print("GROUP: " + id + " (" + a.size() + " Customers)" + " -> OHJATAAN PÖYTÄÄN " + tableNumber + "\n");
                            controllerFxml.updateTextArea("GROUP: " + id + " (" + a.size() + " Customers)" + " -> OHJATAAN PÖYTÄÄN " + tableNumber + "\n");
                            for (Customer customer : a) {
                                customer.setSeated(true);
                            }
                            controller.visualizeCustomer(1);
                            servicePoints[1].addToQueue(a);
                            c1++;
                        } else {
                            servicePoints[0].addToQueue(a);
                        }
                    } catch (Exception e) {
                        System.out.println("Error in PÖYTIINOHJAUS");
                    }
                }
            }

            case TILAAMINEN -> {
                a = servicePoints[1].fetchFromQueue();
                controller.visualizeRemoveCustomers(1);
                for (Customer customer : a) {
                    if (!customer.hasOrdered()) {
                        if (randChance(100) < 20) {
                            MenuItem starter = orderService.getRandomMeal(1);
                            customer.order(waiter, starter);
                            System.out.print("ASIAKAS: " + customer.getId() + " -> Tilaus: " + starter.getName() + "\n");
                            controllerFxml.updateTextArea("ASIAKAS: " + customer.getId() + " -> Tilaus: " + starter.getName() + "\n");
                        }
                        MenuItem mainMeal = orderService.getRandomMeal(2);
                        customer.order(waiter, mainMeal);
                        System.out.print("ASIAKAS: " + customer.getId() + " -> Tilaus: " + mainMeal.getName() + "\n");
                        controllerFxml.updateTextArea("ASIAKAS: " + customer.getId() + " -> Tilaus: " + mainMeal.getName() + "\n");
                        if (randChance(100) < 50) {
                            MenuItem dessert = orderService.getRandomMeal(3);
                            customer.order(waiter, dessert);
                            System.out.print("ASIAKAS: " + customer.getId() + " -> Tilaus: " + dessert.getName() + "\n");
                            controllerFxml.updateTextArea("ASIAKAS: " + customer.getId() + " -> Tilaus: " + dessert.getName() + "\n");
                        }
                        customer.setHasOrdered(true);
                    }
                }
                servicePoints[2].addToQueue(a);
                c2++;
                controller.visualizeCustomer(2);
            }

            case TARJOILU -> {
                a = servicePoints[2].fetchFromQueue();
                controller.visualizeRemoveCustomers(2);
                double prepTime = waiter.calculatePrepTime(a);
                for (Customer customer : a) {
                    double serviceTime = currentTime - customer.getArrivalTime() + prepTime;
                    totalServiceTime += serviceTime;
                    if (serviceTime > maxServiceTime) maxServiceTime = serviceTime;
                    if (serviceTime < minServiceTime) minServiceTime = serviceTime;
                }
                //System.out.println("Group prep time: " + prepTime);
                int id = a.get(0).getGroupId();
                groupPrepTimes.put(id, prepTime);
                for (Customer customer : a) {
                    List<MenuItem> order = waiter.deliverOrder(customer);
                    for (MenuItem item : order) {
                        System.out.print("ASIAKAS: " + customer.getId() + " -> RUOKA: " + item.getName() + "\n");
                        controllerFxml.updateTextArea("ASIAKAS: " + customer.getId() + " -> RUOKA: " + item.getName() + "\n");
                    }
                }
                servicePoints[3].addToQueue(a);
                c3++;
                controller.visualizeCustomer(3);

            }

            case SAFKAAMINEN -> {
                a = servicePoints[3].fetchFromQueue();
                for (Customer customer : a) {
                    System.out.print("ASIAKAS: " + customer.getId() + " -> SYÖ\n");
                    //controllerFxml.updateTextArea("ASIAKAS: " + customer.getId() + " -> SYÖ\n");
                }
                controller.visualizeRemoveCustomers(3);
                servicePoints[4].addToQueue(a);
                controller.visualizeCustomer(4);
                c4++;
            }

            case POISTUMINEN -> {
                a = servicePoints[4].fetchFromQueue();
                controller.visualizeRemoveCustomers(4);
                boolean tableFreed = false;
                int id = a.get(0).getGroupId();
                Double prepTime = groupPrepTimes.get(id);
                if (prepTime == null) {
                    prepTime = 0.0;
                }
                System.out.println("Group: " + id + " -> Prep time: " + prepTime);
                for (Customer customer : a) {
                    if (!customer.isLeaving()) {
                        tableFreed = table.removeCustomerFromTable(customer);
                        System.out.print("Asiakas: " + customer.getId() + " -> POISTUU PÖYDÄSTÄ\n");
                        customer.setDepartTime(Clock.getInstance().getTime() + prepTime);
                        customer.report(this.controllerFxml);
                        customer.setLeaving(true);
                        customer.setSeated(false);
                        c5++;
                    }
                }
                for (Customer customer : a) {
                    double CustomerTime = (customer.getDepartTime() + prepTime) - customer.getArrivalTime();
                    timeInSystem += CustomerTime;
                }
                if (tableFreed) {
                    System.out.println("Table freed up by group: " + id);
                    System.out.println("Free tables: " + table.getFreeTables());
                }
                controller.visualizeCustomer(5);
            }
        }
    }

    /**
     * Attempts to run the C events.
     * If the service point is not reserved and the customer is in the queue, the customer begins service.
     *
     */

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

    /**
     * Results of the simulation.
     */

    @Override
    protected void results() {
        System.out.println("Simulation ended at " + Clock.getInstance().getTime());
        System.out.println("Results ... are not yet here\n");

        orderService.getAllMealResults();

        controller.showEndTime(Clock.getInstance().getTime());

        calculatePerformanceMetrics();
    }

    /**
     * Calculates the performance metrics of the simulation.
     */

    public void calculatePerformanceMetrics() {
        if (totalCustomers > 0) {
            double averageQueueTime = totalQueueTime / totalCustomers;
            double averageServiceTime = totalServiceTime / totalCustomers;

            System.out.println("\nAverage Time in System: " + timeInSystem / totalCustomers);
            System.out.println("\nAverage Queue Time: " + averageQueueTime);
            System.out.println("Maximum Queue Time: " + maxQueueTime);
            System.out.println("Minimum Queue Time: " + minQueueTime);
            System.out.println("\nAverage Service Time: " + averageServiceTime);
            System.out.println("Maximum Service Time: " + maxServiceTime);
            System.out.println("Minimum Service Time: " + minServiceTime);
        } else {
            System.out.println("No customers processed.");
        }
    }

}
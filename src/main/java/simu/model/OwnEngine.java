package simu.model;

import java.util.ArrayList;
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
    private double meanService = 5;
    private double meanEating = 5;
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
        servicePoints[3] = new ServicePoint(new Normal(meanEating, 2), eventList, EventType.SAFKAAMINEN);
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

        List<Customer> group;
        switch ((EventType) t.getType()) {

            // Homma kusee tässä kun pitäs jotenki ylläpitää ryhmiä ja yksittäisiä customereja
            

            case SAAPUMINEN -> {
                // luodaan group olio , joka sisältää 1-4 customer oliota

                group = generateCustomerGroup();
                System.out.println(">>DEBUG --------- RYHMÄKOKO:" + group.size());
                
                // iteroidaan kaikki 
                for (Customer c : group) {
                    servicePoints[0].addToQueue(c);
                    controller.visualizeCustomer(0);
                    arrivalProcess.generateNext();
                }
            }


            case PÖYTIINOHJAUS -> {
                // Tääkään ei ihan paras idea et nappaa sen määrän paljon queassa on 
                
                int customersQueue = servicePoints[0].getQueueSize();

                List<Customer> customerGroup = new ArrayList<>();

                for (int i = 0; i < customersQueue; i++) {

                    Customer c = servicePoints[0].fetchFromQueue();
                    servicePoints[1].addToQueue(c);

                    customerGroup.add(c);
                    controller.visualizeRemoveCustomers(0);
                    controller.visualizeCustomer(1);
                }

                if (table.getFreeTables() > 0) {

                    int tableNumber = table.addCustomersToTable(customerGroup);

                    if (tableNumber > 0) {
                        System.out.print("ASIAKASJOUKKO: " + customerGroup + " -> OHJATAAN PÖYTÄÄN " + tableNumber + "\n");
                        controllerFxml.updateTextArea("ASIAKAS: " + customerGroup + " -> OHJATAAN PÖYTÄÄN " + tableNumber + "\n");
                        }

                } else {
            
                TODO:   // Keksiä tapa miten järkevästi ylläpidetään jonoa, jos vapaita paikkoja tarpeeksi -> ohjataan pöytään
                        // Tai laittaa tän checkin tonne SAAPUMINEN case 
                        // Tää atm vaa jatkaa asiakkaiden tunkemista pöytiin

                    // servicePoints[0].addToQueue(a); EI LISÄTÄ TAKAS JONOON JOS ON JO PÖYTIINOHJAUS KOHDASSA!
                    System.out.print("Asiakasryhmä -> EI VAPAITA PÖYTIÄ\n");
                    controllerFxml.updateTextArea("Asiakasryhmä -> EI VAPAITA PÖYTIÄ\n");
                    
                }

                    customerGroup.clear();
                }
                
                
                
                case TILAAMINEN -> {
                
                int customersQueue = servicePoints[1].getQueueSize();


                
                for (int i = 0; i < customersQueue; i++) {

                    Customer c = servicePoints[1].fetchFromQueue();

                    
                    controller.visualizeRemoveCustomers(1);
                    controller.visualizeCustomer(2);
                    
                    System.out.println(" PERKELE PERKELE -------------- = > " + c.hasOrdered());
                    // TÄÄKI PERKELE, JOS EI OLE TILANNUT NIIN TILATAAN 

                    if (randChance(100) >= 33) {
                        MenuItem starter = orderService.getRandomStarter();
                        c.order(waiter, starter);
                        System.out.print("ASIAKAS: " + c.getId() + " -> Tilaus: " + starter.getName() + "\n");
                        controllerFxml.updateTextArea("ASIAKAS: " + c.getId() + " -> Tilaus: " + starter.getName() + "\n");
                    }

                    MenuItem mainMeal = orderService.getRandomMainMeal();
                    c.order(waiter, mainMeal);
                    System.out.print("ASIAKAS: " + c.getId() + " -> Tilaus: " + mainMeal.getName() + "\n");
                    controllerFxml.updateTextArea("ASIAKAS: " + c.getId() + " -> Tilaus: " + mainMeal.getName() + "\n");
                    
                    if (randChance(100) >= 66) {
                        MenuItem dessert = orderService.getRandomDessert();
                        c.order(waiter, dessert);
                        System.out.print("ASIAKAS: " + c.getId() + " -> Tilaus: " + dessert.getName() + "\n");
                        controllerFxml.updateTextArea("ASIAKAS: " + c.getId() + " -> Tilaus: " + dessert.getName() + "\n");
                    }
                        
                        c.setHasOrdered(true);
                        servicePoints[2].addToQueue(c);
                    }

                    // DEBUGGASIN 2H MIKS EI TOIMI JA TÄÄ OLI TUOL IF SISÄL SUATANA
            }

            case TARJOILU -> {


                int customersQueue = servicePoints[2].getQueueSize();
            
                for (int i = 0; i < customersQueue; i++) {

                    Customer c = servicePoints[2].fetchFromQueue();
                    
                    
                    List<MenuItem> order = waiter.deliverOrder(c);
                    for (MenuItem item : order) {
                        System.out.print("ASIAKAS: " + c.getId() + " -> RUOKA: " + item.getName() + "\n");
                        controllerFxml.updateTextArea("ASIAKAS: " + c.getId() + " -> RUOKA: " + item.getName() + "\n");
                    }
                    controller.visualizeRemoveCustomers(2);
                    controller.visualizeCustomer(3);
                    servicePoints[3].addToQueue(c);
                }
            }

            case SAFKAAMINEN -> {
                int customersQueue = servicePoints[3].getQueueSize();
                System.out.println("SAFKAAMINEN: ----> " + customersQueue + " koko");
            
                for (int i = 0; i < customersQueue; i++) {
                    Customer c = servicePoints[3].fetchFromQueue();
                    servicePoints[4].addToQueue(c);
            
                    controller.visualizeRemoveCustomers(3); // Poistetaan asiakas SAFKAAMINEN jonosta
                    controller.visualizeCustomer(4); // Visualisoidaan asiakas SAFKAAMINEN vaiheessa
                }
            }

            case POISTUMINEN -> {

                int customersQueue = servicePoints[4].getQueueSize();
                List<Customer> customerGroup = new ArrayList<>();

                for (int i = 0; i < customersQueue; i++) {
                    Customer c = servicePoints[4].fetchFromQueue();
            
                    controller.visualizeRemoveCustomers(4); // Poistetaan asiakas POISTUMINEN jonosta
                    controller.visualizeCustomer(5); // Visualisoidaan asiakas POISTUMINEN vaiheessa
            
                    table.removeCustomerFromTable(c);
                    System.out.println("PÖYTÄMÄÄRÄ: " + table.getTableAmount() + ", VAPAITA PÖYTIÄ: " + table.getFreeTables());
            
                    System.out.print("Asiakas: " + c.getId() + " -> POISTUU PÖYDÄSTÄ\n");
                    System.out.println(table.getFreeTables());
            
                    c.setDepartTime(Clock.getInstance().getTime());
                    c.report(this.controllerFxml);
                    c.setLeaving(true);
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
                System.out.println(p.getClass());
                System.out.println("VOIKO C-TAPAHTUMA? -> " + (!p.isReserved() && p.isInQueue()));
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
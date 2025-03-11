package simu.model;

import controller.ControllerForFxml;
import simu.framework.Clock;
import simu.framework.Trace;

/**
 * The Customer class represents a customer in the simulation.
 * It contains the arrival time, departure time, and the customer id.
 * It also contains methods to order food and report the results.
 *
 */

// TODO:
// Asiakas koodataan simulointimallin edellyttämällä tavalla (data!)
public class Customer {

    private double arrivalTime;
    private double departTime;
    private final int id;
    private static int i = 1;
    private static long sum = 0;
    private int groupId;
    private boolean hasOrdered = false;
    private boolean isLeaving = false;
    private boolean isSeated = false;

    /**
     * Constructs a Customer with the specified id.
     */

    public Customer() {
        id = i++;

        arrivalTime = Clock.getInstance().getTime();
        Trace.out(Trace.Level.INFO, "ASIAKAS: " + id + ", SAAPUMINEN, AIKA: (" + arrivalTime + ")");
    }

    /**
     * Returns the departure time of the customer.
     *
     * @return the departure time
     */

    public double getDepartTime() {
        return departTime;
    }

    /**
     * Sets the departure time of the customer.
     *
     * @param departTime the departure time
     */

    public void setDepartTime(double departTime) {
        this.departTime = departTime;
    }

    /**
     * Returns the arrival time of the customer.
     *
     * @return the arrival time
     */

    public double getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Sets the arrival time of the customer.
     *
     * @param arrivalTime the arrival time
     */

    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * Orders the specified menu item from the waiter.
     *
     * @param waiter the waiter to order from
     * @param item the menu item to order
     */

    public void order(Waiter waiter, MenuItem item) {
        waiter.takeOrder(item, this);
    }

    /**
     * Returns the id of the customer.
     *
     * @return the id
     */

    public int getId() {
        return id;
    }

    /**
     * Reports the results of the simulation.
     *
     * @param controllerForFxml the controller used to manage the simulation
     */

    public void report(ControllerForFxml controllerForFxml) {
        Trace.out(Trace.Level.INFO, "\nCustomer " + id + " done! ");
        Trace.out(Trace.Level.INFO, "Customer " + id + " arrived at: " + arrivalTime);
        Trace.out(Trace.Level.INFO, "Customer " + id + " departed at: " + departTime);
        Trace.out(Trace.Level.INFO, "Customer " + id + " stayed for: " + (departTime - arrivalTime));
        sum += (departTime - arrivalTime);
        double average = sum / id;
        System.out.println("Average time customers spent in the service:  " + average);
        //  Print the results to the text area
        controllerForFxml.updateTextArea("\nCustomer " + id + " done! ");
        controllerForFxml.updateTextArea("\nCustomer " + id + " arrived at: " + arrivalTime);
        controllerForFxml.updateTextArea("\nCustomer " + id + " departed at: " + departTime );
        controllerForFxml.updateTextArea("\nCustomer " + id + " stayed for: " + (departTime - arrivalTime));
        controllerForFxml.updateTextArea("\nAverage time customers spent in the service:  " + average);
        controllerForFxml.updateTextArea("\n");

    }

    /**
     * Returns whether the customer has ordered.
     *
     * @return true if the customer has ordered, false otherwise
     */

    public boolean hasOrdered() {
        return hasOrdered;
    }

    /**
     * Sets whether the customer has ordered.
     *
     * @param hasOrdered true if the customer has ordered, false otherwise
     */

    public void setHasOrdered(boolean hasOrdered) {
        this.hasOrdered = hasOrdered;
    }

    /**
     * Returns whether the customer is leaving.
     *
     * @return true if the customer is leaving, false otherwise
     */

    public boolean isLeaving() {
        return isLeaving;
    }

    /**
     * Sets whether the customer is leaving.
     *
     * @param leaving true if the customer is leaving, false otherwise
     */

    public void setLeaving(boolean leaving) {
        isLeaving = leaving;
    }

    /**
     * Returns whether the customer is seated.
     *
     * @return true if the customer is seated, false otherwise
     */

    public boolean isSeated() {
        return isSeated;
    }

    /**
     * Sets whether the customer is seated.
     *
     * @param seated true if the customer is seated, false otherwise
     */

    public void setSeated(boolean seated) {
        isSeated = seated;
    }

    /**
     * Returns the group id of the customer.
     *
     * @return the group id
     */

    public int getGroupId() {
        return groupId;
    }

    /**
     * Sets the group id of the customer.
     *
     * @param groupId the group id
     */

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
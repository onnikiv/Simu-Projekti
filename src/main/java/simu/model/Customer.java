package simu.model;

import controller.ControllerForFxml;
import simu.framework.Clock;
import simu.framework.Trace;

// TODO:
// Asiakas koodataan simulointimallin edellyttämällä tavalla (data!)
public class Customer {
    private double arrivalTime;
    private double departTime;
    private final int id;
    private static int i = 1;
    private static long sum = 0;
    private boolean hasOrdered = false;
    private boolean isLeaving = false;

    public Customer() {
        id = i++;

        arrivalTime = Clock.getInstance().getTime();
        Trace.out(Trace.Level.INFO, "ASIAKAS: " + id + ", SAAPUMINEN, AIKA: (" + arrivalTime + ")");
    }

    public double getDepartTime() {
        return departTime;
    }

    public void setDepartTime(double departTime) {
        this.departTime = departTime;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void order(Waiter waiter, MenuItem item) {
        waiter.takeOrder(item, this);
    }


    public int getId() {
        return id;
    }

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

    public boolean hasOrdered() {
        return hasOrdered;
    }

    public void setHasOrdered(boolean hasOrdered) {
        this.hasOrdered = hasOrdered;
    }

    public boolean isLeaving() {
        return isLeaving;
    }

    public void setLeaving(boolean leaving) {
        isLeaving = leaving;
    }
}
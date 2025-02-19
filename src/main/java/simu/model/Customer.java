package simu.model;

import simu.framework.*;

// TODO:
// Asiakas koodataan simulointimallin edellyttämällä tavalla (data!)
public class Customer {
    private double arrivalTime;
    private double departTime;
    private int id;
    private static int i = 1;
    private static long sum = 0;

    public Customer() {
        id = i++;

        arrivalTime = Clock.getInstance().getTime();
        Trace.out(Trace.Level.INFO, "New customer number " + id + " arrived at  " + arrivalTime);
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


    public int getId() {
        return id;
    }

    public void report() {
        Trace.out(Trace.Level.INFO, "\nCustomer " + id + " done! ");
        Trace.out(Trace.Level.INFO, "Customer " + id + " arrived at: " + arrivalTime);
        Trace.out(Trace.Level.INFO, "Customer " + id + " departed at: " + departTime);
        Trace.out(Trace.Level.INFO, "Customer " + id + " stayed for: " + (departTime - arrivalTime));
        sum += (departTime - arrivalTime);
        double average = sum / id;
        System.out.println("Average time customers spent in the service:  " + average);
    }

}
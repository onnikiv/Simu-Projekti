package controller;

public interface IControllerForM {

    // Rajapinta, joka tarjotaan moottorille:

    public void showEndTime(double time);
    public void visualizeCustomer(int customer);;

    public void visualizeRemoveCustomers(int customer);
    
    public void updateServicePointSums(int c0, int c1, int c2, int c3,int c4,int c5);
}
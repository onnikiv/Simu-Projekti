package view;


public interface IVisualization {

	public void clearScreen();

	public void newCustomer(int customer);

	// jotta saadaan controlleriin luvut, mitkä controller sit lisää käyttöliittymään
	public int getCustomerAmount();
	public int getCustomerAmountServed();

	public void removeCustomer(int customer);

}
package view;

public interface ISimulatorUI {

	// Kontrolleri tarvitsee syötteitä, jotka se välittää Moottorille
	public double getTime();
	public long getDelay();

	//Kontrolleri antaa käyttöliittymälle tuloksia, joita Moottori tuottaa
	public void setEndTime(double time);

	// Kontrolleri tarvitsee
	public IVisualization getVisualization();
	
}
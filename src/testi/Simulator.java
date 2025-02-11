package testi;

import simu.framework.Engine;
import simu.framework.Trace;
import simu.framework.Trace.Level;
import simu.model.OwnEngine;

public class Simulator { //Tekstipohjainen

	public static void main(String[] args) {

		Trace.setTraceLevel(Level.INFO);
		Engine m = new OwnEngine();
		m.setSimulationTime(10);
		m.drive();
		///
	}
}
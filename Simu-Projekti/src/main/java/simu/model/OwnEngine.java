
package simu.model;

import simu.framework.*;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;

public class OwnEngine extends simu.framework.Engine {

	private ArrivalProcess arrivalProcess;

	private ServicePoint[] servicePoints;

	public OwnEngine() {

		servicePoints = new ServicePoint[8];

		servicePoints[0] = new ServicePoint(new Normal(10, 6), eventList, EventType.TABLEASSIGNMENT);
		servicePoints[1] = new ServicePoint(new Normal(10, 10), eventList, EventType.ORDER);
		servicePoints[2] = new ServicePoint(new Normal(2, 3), eventList, EventType.APPETIZER);
		servicePoints[3] = new ServicePoint(new Normal(2, 3), eventList, EventType.SERVE);
		servicePoints[4] = new ServicePoint(new Normal(5, 3), eventList, EventType.MAINCOURSE);
		servicePoints[5] = new ServicePoint(new Normal(2, 3), eventList, EventType.DESSERT);
		servicePoints[6] = new ServicePoint(new Normal(1, 3), eventList, EventType.PAYMENT);
		servicePoints[7] = new ServicePoint(new Normal(2, 3), eventList, EventType.EXIT);

		arrivalProcess = new ArrivalProcess(new Negexp(10, 5), eventList, EventType.ARRIVAL);

	}


	@Override
	protected void init() {
		arrivalProcess.generateNext(); // Ensimmäinen saapuminen järjestelmään
	}

	@Override
	protected void processEvent(Event t) {  // B-vaiheen tapahtumat

		Customer a;
		switch ((EventType) t.getType()) {

			case ARRIVAL:
				servicePoints[0].addToQueue(new Customer());
				arrivalProcess.generateNext();
				break;
			case TABLEASSIGNMENT:
				a = (Customer) servicePoints[0].fetchFromQueue();
				servicePoints[1].addToQueue(a);
				break;
			case ORDER:
				a = (Customer) servicePoints[1].fetchFromQueue();
				servicePoints[2].addToQueue(a);
				break;
			case APPETIZER:
				a = (Customer) servicePoints[2].fetchFromQueue();
				servicePoints[2].serveCustomer(a);
				servicePoints[3].addToQueue(a);
				break;
			case SERVE:
				a = (Customer) servicePoints[3].fetchFromQueue();
				servicePoints[4].addToQueue(a);
				break;
			case MAINCOURSE:
				a = (Customer) servicePoints[4].fetchFromQueue();
				servicePoints[5].addToQueue(a);
				break;
			case DESSERT:
				a = (Customer) servicePoints[5].fetchFromQueue();
				servicePoints[6].addToQueue(a);
				break;
			case PAYMENT:
				a = (Customer) servicePoints[6].fetchFromQueue();
				servicePoints[7].addToQueue(a);
				break;
			case EXIT:
				a = (Customer) servicePoints[7].fetchFromQueue();
				a.setDepartTime(Clock.getInstance().getTime());
				a.report();
		}
	}

	@Override
	protected void attemptCEvents() {
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
	}


}

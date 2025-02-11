
package simu.model;

import simu.framework.*;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;

public class OwnEngine extends simu.framework.Engine {

	private ArrivalProcess arrivalProcess;

	private ServicePoint[] servicePoints;

	public OwnEngine() {

		servicePoints = new ServicePoint[8];

		servicePoints[0] = new ServicePoint(new Normal(10, 6), eventList, EventType.tableAssignment);
		servicePoints[1] = new ServicePoint(new Normal(10, 10), eventList, EventType.ordering);
		servicePoints[2] = new ServicePoint(new Normal(5, 3), eventList, EventType.appetizer);
		servicePoints[3] = new ServicePoint(new Normal(5, 3), eventList, EventType.serving);
		servicePoints[4] = new ServicePoint(new Normal(5, 3), eventList, EventType.mainCourse);
		servicePoints[5] = new ServicePoint(new Normal(5, 3), eventList, EventType.dessert);
		servicePoints[6] = new ServicePoint(new Normal(5, 3), eventList, EventType.payment);
		servicePoints[7] = new ServicePoint(new Normal(5, 3), eventList, EventType.leaving);

		arrivalProcess = new ArrivalProcess(new Negexp(15, 5), eventList, EventType.arrival);

	}


	@Override
	protected void init() {
		arrivalProcess.generateNext(); // Ensimmäinen saapuminen järjestelmään
	}

	@Override
	protected void processEvent(Event t) {  // B-vaiheen tapahtumat

		Customer a;
		switch ((EventType) t.getType()) {

			case arrival:
				servicePoints[0].addToQueue(new Customer());
				arrivalProcess.generateNext();
				break;
			case tableAssignment:
				a = (Customer) servicePoints[0].fetchFromQueue();
				servicePoints[1].addToQueue(a);
				break;
			case ordering:
				a = (Customer) servicePoints[1].fetchFromQueue();
				servicePoints[2].addToQueue(a);
				break;
			case appetizer:
				a = (Customer) servicePoints[2].fetchFromQueue();
				servicePoints[3].addToQueue(a);
				break;
			case serving:
				a = (Customer) servicePoints[3].fetchFromQueue();
				servicePoints[4].addToQueue(a);
				break;
			case mainCourse:
				a = (Customer) servicePoints[4].fetchFromQueue();
				servicePoints[5].addToQueue(a);
				break;
			case dessert:
				a = (Customer) servicePoints[5].fetchFromQueue();
				servicePoints[6].addToQueue(a);
				break;
			case payment:
				a = (Customer) servicePoints[6].fetchFromQueue();
				servicePoints[7].addToQueue(a);
				break;
			case leaving:
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

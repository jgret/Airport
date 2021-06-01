package main;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.ProcessQueue;
import java.util.ArrayList;

public class Airport extends Model {
	
	public static final int DEST_CAR_RENT   = 0;
	public static final int DEST_TERMINAL_1 = 1;
	public static final int DEST_TERMINAL_2 = 2;

	/**
	 * Queue for people on first Terminal waiting for a Bus to the car rent
	 */

	protected ProcessQueue<Person> terminalQueue1;

	/**
	 * Queue for people on second Terminal waiting for a Bus to the car rent
	 */

	protected ProcessQueue<Person> terminalQueue2;

	/**
	 * Queue for people driving in the Bus
	 */

	protected ProcessQueue<Person> busQueue;

	/**
	 * people waiting at the bus
	 */

	protected ProcessQueue<Person> peopleWaitForBus;

	/**
	 * people waiting at the car rent for a car
	 */

	protected ProcessQueue<Person> peopleWaitForCar;

	/**
	 * cars waiting at the car rent for people
	 */

	protected ProcessQueue<Car> carWaitForPeople;

	protected Bus bus; // ?????

	protected int busSize = 20;

	protected int busMaxWait = 5;

	public Airport(Model owner, String name, boolean showInReport, boolean showInTrace) {
		super(owner, name, showInReport, showInTrace);
	}

	@Override
	public String description() {
		return null;
	}

	@Override
	public void doInitialSchedules() {

	}

	@Override
	public void init() {
		
		terminalQueue1 = new ProcessQueue<>(this, "Queue Terminal 1", true, true);
		terminalQueue2 = new ProcessQueue<>(this, "Queue Terminal 1", true, true);
		peopleWaitForBus = new ProcessQueue<>(this, "People wait for Bus", true, true);
		peopleWaitForCar = new ProcessQueue<>(this, "People wait for Car", true, true);
		carWaitForPeople = new ProcessQueue<>(this, "Car queue", true, true);
		busQueue = new ProcessQueue<>(this, "People in Bus", true, true);
		
		bus = new Bus(this, "bus", true, true);
		bus.activate();
	}

}

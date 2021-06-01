package main;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.ProcessQueue;
import java.util.ArrayList;

public class Airport extends Model {
	
	/**
	 * Queue for people on first Terminal waiting for a Bus to the car rent
	 */

	protected ProcessQueue<Human> terminalQueue1;

	/**
	 * Queue for people on second Terminal waiting for a Bus to the car rent
	 */
	
	protected ProcessQueue<Human> terminalQueue2;

	/**
	 * Queue for people driving in the Bus
	 */
	
	protected ArrayList<Human> busQueue;
	
	/**
	 * people waiting at the car rent for a car
	 */

	protected ProcessQueue<Human> carRentQueue;
	
	/**
	 * cars waiting at the car rent for people
	 */

	protected ProcessQueue<Car> carWaitQueue;
	
	protected Bus bus;
	
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

	}

}

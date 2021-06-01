package main;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeSpan;

public class Car extends SimProcess {

	private Airport airport;
	
	public Car(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		this.airport = (Airport) owner;
	}

	@Override
	public void lifeCycle() throws SuspendExecution {
		
		while (true) {
			
			if (!airport.peopleWaitForCar.isEmpty()) {
				
				Person person = airport.peopleWaitForCar.first();
				airport.peopleWaitForCar.remove(person);
				
				airport.carWaitForPeople.remove(this);
				hold(new TimeSpan(10000));
				airport.carWaitForPeople.insert(this);

				airport.peopleWaitForBus.insert(person);

			}
			
			passivate();
			
		}
		
	}

}

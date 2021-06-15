package main;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.ProcessQueue;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeSpan;

import java.util.Random;

public class Car extends SimProcess {

	private Airport airport;
	private Random rnd;
	private ProcessQueue<Person> passangers;
	
	public Car(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		this.airport = (Airport) owner;
		passangers = new ProcessQueue<Person>(airport, "People in Car", true, true);
		rnd = new Random();
	}

	@Override
	public void lifeCycle() throws SuspendExecution {
		
		while (true) {
			int passanger = 0 ;
			if (!airport.peopleWaitForCar.isEmpty()) {
				do{
					passanger = rnd.nextInt(3)+1;
				}while(airport.peopleWaitForCar.size()>= passanger);

				for(int i = 0 ; i < passanger; i++){
					Person p = airport.peopleWaitForCar.first();
					airport.peopleWaitForCar.remove(p);
					passangers.insert(p);
				}
				airport.carwithpeople.insert(this);
				// random gemacht werden
				hold(new TimeSpan(10000));

				passangers.removeAll();
				airport.carwithpeople.remove(this);

			}
			
			passivate();
			
		}
		
	}

}

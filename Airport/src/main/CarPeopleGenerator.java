package main;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.ExternalEvent;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.ProcessQueue;
import desmoj.core.simulator.TimeSpan;

public class CarPeopleGenerator extends ExternalEvent {

	private Airport airport;
	private ProcessQueue<Person> destinantion;
	
	public CarPeopleGenerator(Model owner, String name, boolean showInTrace, ProcessQueue<Person> dest) {
		super(owner, name, showInTrace);
		this.airport = (Airport) owner;
		this.destinantion = dest;
	}

	@Override
	public void eventRoutine() throws SuspendExecution {
		Person p1 = new Person(airport, "Mensch T1", true, Airport.DEST_TERMINAL_1);
		Person p2 = new Person(airport, "Mensch T2", true, Airport.DEST_TERMINAL_2);
		p1.arrive(Airport.DEST_CAR_RENT);
		destinantion.insert(p1);
		p2.arrive(Airport.DEST_CAR_RENT);
		destinantion.insert(p2);
		airport.dataPeopleCarRent.update(airport.peopleWaitForBus.size());
		this.schedule(new TimeSpan(1000));
	}

}

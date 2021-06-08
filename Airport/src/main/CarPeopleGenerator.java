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
		
		destinantion.insert(new Person(airport, "Mensch", true, Airport.DEST_TERMINAL_1));
		destinantion.insert(new Person(airport, "Mensch", true, Airport.DEST_TERMINAL_2));
		
		airport.dataPeopleTerminal1.update(50);
		
		this.schedule(new TimeSpan(60));
	}

}

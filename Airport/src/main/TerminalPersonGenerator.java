package main;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.ExternalEvent;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.ProcessQueue;
import desmoj.core.simulator.TimeSpan;

public class TerminalPersonGenerator extends ExternalEvent {

	private Airport airport;
	private ProcessQueue<Person> destinantion;
	
	public TerminalPersonGenerator(Model owner, String name, boolean showInTrace, ProcessQueue<Person> dest) {
		super(owner, name, showInTrace);
		this.airport = (Airport) owner;
		this.destinantion = dest;
	}

	@Override
	public void eventRoutine() throws SuspendExecution {
		
		for (int i = 0; i < airport.plainSize; i++) {
			destinantion.insert(new Person(airport, "Mensch", true, Airport.DEST_CAR_RENT));
		}
		
		this.schedule(new TimeSpan(2 * 60 * 60));
		
	}

}

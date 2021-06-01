package main;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;

public class Person extends SimProcess{
	
	private int destination;

	public Person(Model owner, String name, boolean showInTrace, int destination) {
		super(owner, name, showInTrace);
	}
	
	@Override
	public void lifeCycle() throws SuspendExecution {
		
	}

	public int getDestination() {
		return destination;
	}
	
}

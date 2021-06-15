package main;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeInstant;

public class Person extends SimProcess{
	
	private int destination;
	
	private TimeInstant[] entrance;

	private TimeInstant[] exit;
	
	public Person(Model owner, String name, boolean showInTrace, int destination) {
		super(owner, name, showInTrace);
	}
	
	@Override
	public void lifeCycle() throws SuspendExecution {
		
	}

	public int getDestination() {
		return destination;
	}
	
	public TimeInstant arrive(int destination) {
		
	}
	
	public TimeInstant exit(int destination) {
		
	}
	
	public TimeInstant getEntrance(int destination) {
		
	}
	
	public TimeInstant getExit(int destination) {
		
	}
	
	public plotData() {
		
	}
	
}

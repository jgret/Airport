package main;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;

public class Human extends SimProcess{

	public Human(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
	}
	
	@Override
	public void lifeCycle() throws SuspendExecution {
		
	}

}

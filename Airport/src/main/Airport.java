package main;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.ProcessQueue;

public class Airport extends Model {
	
	protected ProcessQueue<Human> terminalQueue1;
	protected ProcessQueue<Human> terminalQueue2;
	protected ProcessQueue<Car> carWaitQueue;
	

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

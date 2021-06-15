package main;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeInstant;

import static main.Airport.*;


public class Person extends SimProcess{

	private Airport airport;
	
	private int destination;
	
	private TimeInstant[] entrance;

	private TimeInstant[] exit;
	
	private boolean dataPlottet;
	
	public Person(Model owner, String name, boolean showInTrace, int destination) {
		super(owner, name, showInTrace);
		this.airport = (Airport) owner;
		entrance = new TimeInstant[3];
		exit = new TimeInstant[3];
		this.dataPlottet = false;
	}
	
	@Override
	public void lifeCycle() throws SuspendExecution {
		
	}

	public int getDestination() {
		return destination;
	}
	
	public void arrive(int destination) {
		if (destination >= 0 && destination <= 2) {
			entrance[destination] = presentTime();
		}
	}
	
	public void exit(int destination) {
		if (destination >= 0 && destination <= 2) {
			exit[destination] = presentTime();
		}
	}
	
	public TimeInstant getEntrance(int destination) {
		return entrance[destination];
	}
	
	public TimeInstant getExit(int destination) {
		return exit[destination];
	}
	
	public boolean plotData() {
		if (!dataPlottet) {
			if (entrance[DEST_CAR_RENT] != null && exit[DEST_CAR_RENT] != null) {
				double time = exit[DEST_CAR_RENT].getTimeAsDouble() - entrance[DEST_CAR_RENT].getTimeAsDouble();
				airport.dataWaitTimesCarRent.update(time);
			}
			if (entrance[DEST_TERMINAL_1] != null && exit[DEST_TERMINAL_1] != null) {
				double time = exit[DEST_TERMINAL_1].getTimeAsDouble() - entrance[DEST_TERMINAL_1].getTimeAsDouble();
				airport.dataWaitTimesT1.update(time);
			}
			if (entrance[DEST_TERMINAL_2] != null && exit[DEST_TERMINAL_2] != null) {
				double time = exit[DEST_TERMINAL_2].getTimeAsDouble() - entrance[DEST_TERMINAL_2].getTimeAsDouble();
				airport.dataWaitTimesT2.update(time);
			}
			return (dataPlottet = true);
		}
		return false;
	}
	
}

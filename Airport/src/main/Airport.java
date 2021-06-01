package main;

import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.ProcessQueue;
import desmoj.core.simulator.TimeInstant;

public class Airport extends Model {
	
	public static final int DEST_CAR_RENT   = 0;
	public static final int DEST_TERMINAL_1 = 1;
	public static final int DEST_TERMINAL_2 = 2;

	/**
	 * Queue for people on first Terminal waiting for a Bus to the car rent
	 */

	protected ProcessQueue<Person> terminalQueue1;

	/**
	 * Queue for people on second Terminal waiting for a Bus to the car rent
	 */

	protected ProcessQueue<Person> terminalQueue2;

	/**
	 * Queue for people driving in the Bus
	 */

	protected ProcessQueue<Person> busQueue;

	/**
	 * people waiting at the bus
	 */

	protected ProcessQueue<Person> peopleWaitForBus;

	/**
	 * people waiting at the car rent for a car
	 */

	protected ProcessQueue<Person> peopleWaitForCar;

	/**
	 * cars waiting at the car rent for people
	 */

	protected ProcessQueue<Car> carWaitForPeople;

	protected Bus bus; // ?????

	protected int busSize = 20;

	protected int busMaxWait = 5;
	
	protected int plainSize = 80;
	
	protected TerminalPersonGenerator terminalPersonGenerator1;
	
	protected TerminalPersonGenerator terminalPersonGenerator2;
	
	public Airport(Model owner, String name, boolean showInReport, boolean showInTrace) {
		super(owner, name, showInReport, showInTrace);
	}

	@Override
	public String description() {
		return null;
	}

	@Override
	public void doInitialSchedules() {
		terminalPersonGenerator1 = new TerminalPersonGenerator(this, "gen1", true, terminalQueue1);
		terminalPersonGenerator1.schedule();
		terminalPersonGenerator2 = new TerminalPersonGenerator(this, "gen2", true, terminalQueue2);
		terminalPersonGenerator2.schedule();
	}

	@Override
	public void init() {
		
		terminalQueue1 = new ProcessQueue<>(this, "Queue Terminal 1", true, true);
		terminalQueue2 = new ProcessQueue<>(this, "Queue Terminal 1", true, true);
		peopleWaitForBus = new ProcessQueue<>(this, "People wait for Bus", true, true);
		peopleWaitForCar = new ProcessQueue<>(this, "People wait for Car", true, true);
		carWaitForPeople = new ProcessQueue<>(this, "Car queue", true, true);
		busQueue = new ProcessQueue<>(this, "People in Bus", true, true);
		
		bus = new Bus(this, "bus", true, true);
		bus.activate();
	}
	
	public static void main(java.lang.String[] args) {

		// make a new experiment
		// Use as experiment name a OS filename compatible string!!
		// Otherwise your simulation will crash!!
        Experiment.setEpsilon(java.util.concurrent.TimeUnit.MILLISECONDS);
        Experiment.setReferenceUnit(java.util.concurrent.TimeUnit.SECONDS);
        Experiment experiment = new Experiment("Airport Model");

		// make a new model
		// null as first parameter because it is the main model and has no mastermodel
		Airport vc_1st_p_Model =
			new Airport(
				null,
				"Airport Model",
				true,
				false);

		// connect Experiment and Model
		vc_1st_p_Model.connectToExperiment(experiment);

        // set trace
		experiment.tracePeriod(new TimeInstant(0), new TimeInstant(100));

		// now set the time this simulation should stop at 
		// let him work 1500 Minutes
		experiment.stop(new TimeInstant(1500));
		experiment.setShowProgressBar(false);

		
		// start the Experiment with start time 0.0
		experiment.start();

		// --> now the simulation is running until it reaches its ending criteria
		// ...
		// ...
		// <-- after reaching ending criteria, the main thread returns here

		
		// print the report about the already existing reporters into the report file
		experiment.report();

		// stop all threads still alive and close all output files
		experiment.finish();
		
	}

}

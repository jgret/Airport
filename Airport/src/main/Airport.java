package main;

import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.ProcessQueue;
import desmoj.core.simulator.TimeInstant;
import desmoj.core.statistic.Histogram;
import desmoj.core.statistic.TimeSeries;
import desmoj.extensions.experimentation.util.ExperimentRunner;

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
	
	protected CarPeopleGenerator carRentPersonGenerator;
	
	protected int totalTerminal1 = 0;
	
	protected TimeSeries dataPeopleTerminal1;
	
	protected int totalTerminal2 = 0;
	
	protected TimeSeries dataPeopleTerminal2;
	
	protected int totalCarRent = 0;

	protected TimeSeries dataPeopleCarRent;
	
	protected TimeSeries dataPeopleInBus;
	
	protected int maxWaitTimeT1;

	protected int maxWaitTimeT2;
	
	protected int maxWaitTimeCarRent;
	
	protected Histogram dataWaitTimesT1;
	
	protected Histogram dataWaitTimesT2;
	
	protected Histogram dataWaitTimesCarRent;
	
	protected Histogram dataWaitTimesTotal;
	
	public Airport() {
		this(null, "Airport", true, true);
	}
	
	public Airport(Model owner, String name, boolean showInReport, boolean showInTrace) {
		super(owner, name, showInReport, showInTrace);
	}

	@Override
	public String description() {
		return null;
	}

	@Override
	public void doInitialSchedules() {
		terminalPersonGenerator1 = new TerminalPersonGenerator(this, "gen1", true, terminalQueue1,Airport.DEST_TERMINAL_1);
		terminalPersonGenerator1.schedule();
		terminalPersonGenerator2 = new TerminalPersonGenerator(this, "gen2", true, terminalQueue2, Airport.DEST_TERMINAL_2);
		terminalPersonGenerator2.schedule();
		carRentPersonGenerator = new CarPeopleGenerator(this,"gen3", true, peopleWaitForBus);
		carRentPersonGenerator.schedule();
	}

	@Override
	public void init() {
		
		terminalQueue1 = new ProcessQueue<>(this, "Queue Terminal 1", true, true);
		terminalQueue2 = new ProcessQueue<>(this, "Queue Terminal 1", true, true);
		peopleWaitForBus = new ProcessQueue<>(this, "People wait for Bus", true, true);
		peopleWaitForCar = new ProcessQueue<>(this, "People wait for Car", true, true);
		carWaitForPeople = new ProcessQueue<>(this, "Car queue", true, true);
		busQueue = new ProcessQueue<>(this, "People in Bus", true, true);
		
		
		double simStart = (double) this.getParameterManager().getParameterValue(ExperimentRunner.EXP_START_TIME);
		double simStop = (double) this.getParameterManager().getParameterValue(ExperimentRunner.EXP_STOP_TIME);
		dataPeopleTerminal1 = new TimeSeries(this, "Terminal 1", new TimeInstant(simStart), new TimeInstant(simStop), true, true);
		dataPeopleTerminal2 = new TimeSeries(this, "Terminal 2", new TimeInstant(simStart), new TimeInstant(simStop), true, true);
		dataPeopleCarRent = new TimeSeries(this, "Car Rent", new TimeInstant(simStart), new TimeInstant(simStop), true, true);
		dataPeopleInBus = new TimeSeries(this, "People in Bus", new TimeInstant(simStart), new TimeInstant(simStop), true, true);
		
		dataWaitTimesCarRent = new Histogram(this, "Car Rent Wait Times", 0, 10000, 10, false, false);
		dataWaitTimesT1 = new Histogram(this, "Terminal 1 Wait Times", 0, 10000, 10, false, false);
		dataWaitTimesT2 = new Histogram(this, "Terminal 2 Wait Times", 0, 10000, 10, false, false);
		dataWaitTimesTotal = new Histogram(this, "Total Wait Times", 0, 10000, 10, false, false);

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

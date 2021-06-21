package main;

import java.util.Map;
import java.util.TreeMap;

import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.ProcessQueue;
import desmoj.core.simulator.TimeInstant;
import desmoj.core.statistic.Histogram;
import desmoj.core.statistic.TimeSeries;
import desmoj.core.util.AccessPoint;
import desmoj.core.util.Parameterizable;
import desmoj.extensions.experimentation.reflect.MutableFieldAccessPoint;

public class Airport extends Model implements Parameterizable {

	public static final int DEST_CAR_RENT = 0;
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

	protected ProcessQueue<Car> carwithpeople;

	protected Bus bus; // ?????

	protected int busSize;

	protected int busMaxWait = 5;

	protected int rentcaresize = 200;
	
	protected int plainSize = 80;

	protected TerminalPersonGenerator terminalPersonGenerator1;

	protected TerminalPersonGenerator terminalPersonGenerator2;

	protected CarPeopleGenerator carRentPersonGenerator;

	protected CarTerminal carTerminal;
	
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
		this.busSize = 20;
	}

	@Override
	public String description() {
		return null;
	}

	@Override
	public void doInitialSchedules() {
		terminalPersonGenerator1 = new TerminalPersonGenerator(this, "gen1", true, terminalQueue1,
				Airport.DEST_TERMINAL_1);
		terminalPersonGenerator1.schedule();
		terminalPersonGenerator2 = new TerminalPersonGenerator(this, "gen2", true, terminalQueue2,
				Airport.DEST_TERMINAL_2);
		terminalPersonGenerator2.schedule();
		carRentPersonGenerator = new CarPeopleGenerator(this, "gen3", true, peopleWaitForBus);
		carRentPersonGenerator.schedule();
		carTerminal = new CarTerminal(this,"Car mannager",false);
		carTerminal.schedule();

	}

	@Override
	public void init() {

		terminalQueue1 = new ProcessQueue<>(this, "Queue Terminal 1", true, true);
		terminalQueue2 = new ProcessQueue<>(this, "Queue Terminal 1", true, true);
		peopleWaitForBus = new ProcessQueue<>(this, "People wait for Bus", true, true);
		peopleWaitForCar = new ProcessQueue<>(this, "People wait for Car", true, true);
		carwithpeople = new ProcessQueue<>(this, "Car queue", true, true);
		busQueue = new ProcessQueue<>(this, "People in Bus", true, true);

//		System.out.println(this.getParameterManager().getParameters());
		double simStart = 0;
//				.getParameterValue("startTime");
		double simStop = 86400;
//		System.out.println(simStart);
//		System.out.println(simStop);
		dataPeopleTerminal1 = new TimeSeries(this, "Terminal 1", new TimeInstant(simStart), new TimeInstant(simStop),
				true, true);
		dataPeopleTerminal2 = new TimeSeries(this, "Terminal 2", new TimeInstant(simStart), new TimeInstant(simStop),
				true, true);
		dataPeopleCarRent = new TimeSeries(this, "Car Rent", new TimeInstant(simStart), new TimeInstant(simStop), true,
				true);
		dataPeopleInBus = new TimeSeries(this, "People in Bus", new TimeInstant(simStart), new TimeInstant(simStop),
				true, true);

		dataWaitTimesCarRent = new Histogram(this, "Car Rent Wait Times", 0, 10000, 10, false, false);
		dataWaitTimesT1 = new Histogram(this, "Terminal 1 Wait Times", 0, 10000, 10, false, false);
		dataWaitTimesT2 = new Histogram(this, "Terminal 2 Wait Times", 0, 10000, 10, false, false);
		dataWaitTimesTotal = new Histogram(this, "Total Wait Times", 0, 10000, 10, false, false);

		System.out.println(this.getParameterManager().getParameters());

		bus = new Bus(this, "bus", true, true);
		bus.activate();
	}

//	public static void main(java.lang.String[] args) {
//
//		// make a new experiment
//		// Use as experiment name a OS filename compatible string!!
//		// Otherwise your simulation will crash!!
//		Experiment.setEpsilon(java.util.concurrent.TimeUnit.MILLISECONDS);
//		Experiment.setReferenceUnit(java.util.concurrent.TimeUnit.SECONDS);
//		Experiment experiment = new Experiment("Airport Model");
//
//		// make a new model
//		// null as first parameter because it is the main model and has no mastermodel
//		Airport vc_1st_p_Model = new Airport(null, "Airport Model", true, false);
//
//		// connect Experiment and Model
//		vc_1st_p_Model.connectToExperiment(experiment);
//
//		// set trace
//		experiment.tracePeriod(new TimeInstant(0), new TimeInstant(100));
//
//		// now set the time this simulation should stop at
//		// let him work 1500 Minutes
//		experiment.stop(new TimeInstant(1500));
//		experiment.setShowProgressBar(false);
//
//		// start the Experiment with start time 0.0
//		experiment.start();
//
//		// --> now the simulation is running until it reaches its ending criteria
//		// ...
//		// ...
//		// <-- after reaching ending criteria, the main thread returns here
//
//		// print the report about the already existing reporters into the report file
//		experiment.report();
//
//		// stop all threads still alive and close all output files
//		experiment.finish();
//
//	}

	@Override
	public Map<String, AccessPoint> createParameters() {
		Map<String, AccessPoint> pm = new TreeMap<String, AccessPoint>();
		pm.put("Bus Size", new MutableFieldAccessPoint("busSize", this));
		return pm;
	}

}

package main;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.ExternalEvent;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.ProcessQueue;
import desmoj.core.simulator.TimeSpan;

import java.util.Random;

public class TerminalPersonGenerator extends ExternalEvent {

	private Airport airport;
	private ProcessQueue<Person> destinantion;
	private Random rnd;
	public static final int AIRPLANE_MAX_SIZE = 120;
	public static final int AIRPLANE_MINI_SIZE = 20;
	
	public TerminalPersonGenerator(Model owner, String name, boolean showInTrace, ProcessQueue<Person> dest) {
		super(owner, name, showInTrace);
		this.airport = (Airport) owner;
		this.destinantion = dest;
		this.rnd = new Random();
	}

	@Override
	public void eventRoutine() throws SuspendExecution {
		int airplanesize;
		int waittime;

		do{
			waittime = rnd.nextInt(5);
		}while (waittime > 0);

		do{
			airplanesize = rnd.nextInt(120);
		}while(!(airplanesize <= AIRPLANE_MINI_SIZE && airplanesize >= AIRPLANE_MAX_SIZE));

		for (int i = airplanesize; i < airport.plainSize; i++) {
			destinantion.insert(new Person(airport, "Mensch", true, Airport.DEST_CAR_RENT));
		}
		
		this.schedule(new TimeSpan(waittime * 60 * 60));
		
	}

}

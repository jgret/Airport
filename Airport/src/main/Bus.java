package main;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;

import desmoj.core.simulator.TimeInstant;

import desmoj.core.simulator.TimeSpan;

public class Bus extends SimProcess {

	private Airport airport;

	public Bus(Model owner, String name, boolean repeating, boolean showInTrace) {
		super(owner, name, repeating, showInTrace);
		this.airport = (Airport) owner;
	}

	@Override
	public void lifeCycle() throws SuspendExecution {

		while (true) {
			
			TimeInstant start = presentTime();
			TimeInstant ende = presentTime();

			while (airport.busQueue.size() <= 0 && (ende.getTimeRounded() - start.getTimeRounded()) <= 300) {

				if (!airport.peopleWaitForBus.isEmpty()) {

					while (!airport.peopleWaitForBus.isEmpty() && airport.busQueue.size() < airport.busSize) {
						Person person = airport.peopleWaitForBus.first();
						airport.peopleWaitForBus.remove(person);

						hold(new TimeSpan(2));

						airport.busQueue.insert(person);
					}
				}
				hold(new TimeSpan(1));
				ende = presentTime();
			}
			airport.dataPeopleInBus.update(airport.busQueue.size());
			
			hold(new TimeSpan(200));
			
			// terminal 1

			for (Person p : airport.busQueue) {
				if (p.getDestination() == Airport.DEST_TERMINAL_1) {
					airport.busQueue.remove(p);
					airport.dataPeopleTerminal1.update(++airport.totalTerminal1);
					airport.dataPeopleInBus.update(airport.busQueue.size());
					hold(new TimeSpan(2));
				}
			}

			while (!airport.terminalQueue1.isEmpty() && airport.busQueue.size() < airport.busSize) {
				Person person = airport.terminalQueue1.first();
				airport.terminalQueue1.remove(person);
				hold(new TimeSpan(2));

				airport.busQueue.insert(person);
				airport.dataPeopleInBus.update(airport.busQueue.size());
			}

			// terminal 2

			hold(new TimeSpan(20));

			for (Person p : airport.busQueue) {
				if (p.getDestination() == Airport.DEST_TERMINAL_2) {
					airport.busQueue.remove(p);
					airport.dataPeopleTerminal2.update(++airport.totalTerminal2);
					airport.dataPeopleInBus.update(airport.busQueue.size());
					hold(new TimeSpan(2));
				}
			}

			while (!airport.terminalQueue2.isEmpty() && airport.busQueue.size() < airport.busSize) {
				Person person = airport.terminalQueue2.first();
				airport.terminalQueue2.remove(person);
				hold(new TimeSpan(2));

				airport.busQueue.insert(person);
				airport.dataPeopleInBus.update(airport.busQueue.size());
			}

			hold(new TimeSpan(200));

			// car rent

			for (Person p : airport.busQueue) {
//				airport.peopleWaitForBus.insert(p);
				airport.dataPeopleCarRent.update(++airport.totalCarRent);
				hold(new TimeSpan(2));
			}
			airport.busQueue.removeAll();
			airport.dataPeopleInBus.update(airport.busQueue.size());
		}

	}

}

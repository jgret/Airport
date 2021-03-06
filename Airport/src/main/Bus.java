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
		boolean changesize = false;
		TimeInstant start;
		TimeInstant ende;
		while (true) {

			start = presentTime();
			ende = presentTime();

			while (!changesize && (ende.getTimeRounded() - start.getTimeRounded()) <= 300) {

				if (!airport.peopleWaitForBus.isEmpty()) {

					while (!airport.peopleWaitForBus.isEmpty() && airport.busQueue.size() < airport.busSize) {
						Person person = airport.getNextPersonFromCarRent();
						hold(new TimeSpan(2));
						person.exit(Airport.DEST_CAR_RENT);
						airport.busQueue.insert(person);
					}
					changesize = true;
				}
				hold(new TimeSpan(1));
				ende = presentTime();
			}

			airport.dataUpdateBus();
			hold(new TimeSpan(200));

			// terminal 1
			changesize = false;
			start = presentTime();
			ende = presentTime();

			while (!changesize && (ende.getTimeRounded() - start.getTimeRounded()) <= 300) {

				for (Person p : airport.busQueue) {
					if (p.getDestination() == Airport.DEST_TERMINAL_1) {
						p.arrive(Airport.DEST_TERMINAL_1);
						p.plotData();
						airport.busQueue.remove(p);
						airport.dataUpdateBus();
						hold(new TimeSpan(2));
						changesize = true;
					}
				}

				while (!airport.terminalQueue1.isEmpty() && airport.busQueue.size() < airport.busSize) {
					Person person = airport.getNextPersonFromT1();
					person.exit(Airport.DEST_TERMINAL_1);
					hold(new TimeSpan(2));
					airport.busQueue.insert(person);
					airport.dataUpdateBus();
					changesize = true;
				}
				hold(new TimeSpan(2));
				ende = presentTime();
				airport.dataPeopleTerminal1.update(airport.terminalQueue1.size());
			}
			// terminal 2
			changesize = false;
			start = presentTime();
			ende = presentTime();
			
			hold(new TimeSpan(20));

			while (!changesize && (ende.getTimeRounded() - start.getTimeRounded()) <= 300) {

				for (Person p : airport.busQueue) {
					if (p.getDestination() == Airport.DEST_TERMINAL_2) {
						p.arrive(Airport.DEST_TERMINAL_2);
						airport.busQueue.remove(p);
						airport.dataUpdateBus();
						p.plotData();
						changesize = true;
						hold(new TimeSpan(2));
					}
				}

				while (!airport.terminalQueue2.isEmpty() && airport.busQueue.size() < airport.busSize) {
					Person person = airport.getNextPersonFromT2();
					hold(new TimeSpan(2));
					person.exit(Airport.DEST_TERMINAL_2);
					airport.busQueue.insert(person);
					airport.dataUpdateBus();
					changesize = true;
				}
				hold(new TimeSpan(2));
				ende = presentTime();
				airport.dataPeopleTerminal2.update(airport.terminalQueue2.size());
			}

			airport.dataUpdateBus();
			hold(new TimeSpan(200));

			// car rent
			changesize = false;
			if (airport.busQueue.size() > 0) {
				for (Person p : airport.busQueue) {
//				airport.peopleWaitForBus.insert(p);

                    airport.peopleWaitForCar.insert(p);
                    airport.totalCarRent++;
                    hold(new TimeSpan(2));
                    p.arrive(Airport.DEST_CAR_RENT);
                    p.plotData();
                }
                airport.busQueue.removeAll();
                airport.dataPeopleInBus.update(airport.busQueue.size());
                changesize = true;
            }
        }
    }

}

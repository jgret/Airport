package main;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.ExternalEvent;
import desmoj.core.simulator.Model;

public class CarTerminal extends ExternalEvent {
    private Airport airport;

    public CarTerminal(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
        this.airport = (Airport) owner;

    }
    @Override
    public void eventRoutine() throws SuspendExecution {
        if(airport.peopleWaitForCar.size() > 0 && airport.carwithpeople.size() > airport.totalCarRent){
            Car car = new Car(airport, "Mensch Car", true);
            airport.carwithpeople.insert(car);
        }
    }
}

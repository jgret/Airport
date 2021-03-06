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
    private String name;
    private int generatornumber;
    public static final int AIRPLANE_MAX_SIZE = 120;
    public static final int AIRPLANE_MINI_SIZE = 20;

    public TerminalPersonGenerator(Model owner, String name, boolean showInTrace, ProcessQueue<Person> dest, int generatornubmer) {
        super(owner, name, showInTrace);
        this.airport = (Airport) owner;
        this.name = name;
        this.destinantion = dest;
        this.rnd = new Random();
        this.generatornumber = generatornubmer;
    }

    @Override
    public void eventRoutine() throws SuspendExecution {
        int airplanesize;
        int waittime;

        waittime = rnd.nextInt(3) + 1;
        airplanesize = rnd.nextInt(AIRPLANE_MAX_SIZE - AIRPLANE_MINI_SIZE) + AIRPLANE_MINI_SIZE;
        for (int i = airplanesize; i < airport.plainSize; i++) {
            Person p = new Person(airport, "Mensch Car", true, Airport.DEST_CAR_RENT);
            p.arrive(generatornumber);
            destinantion.insert(p);
            
        }

        switch (generatornumber){
            case 1:
                airport.dataPeopleTerminal1.update(airport.terminalQueue1.size());
            case 2:
                airport.dataPeopleTerminal2.update(airport.terminalQueue2.size());
        }


        this.schedule(new TimeSpan(waittime*60*60)); // in hours

    }

}

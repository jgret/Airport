package main;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;

public class Bus extends SimProcess {

    private int size;
    private int time;


    public Bus(Model owner, String name, boolean repeating, boolean showInTrace) {
        super(owner, name, repeating, showInTrace);
    }

    public Bus(Model owner, String name, boolean showInTrace,int size,int waitingtime) {
        super(owner, name, showInTrace);
    }

    @Override
    public void lifeCycle() throws SuspendExecution {



    }
}

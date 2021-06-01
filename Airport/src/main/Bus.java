package main;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeSpan;

public class Bus extends SimProcess {

    private int size;
    private int time;


    public Bus(Model owner, String name, boolean repeating, boolean showInTrace) {
        super(owner, name, repeating, showInTrace);
    }

    public Bus(Model owner, String name, boolean showInTrace,int size,int waitingtime) {
        super(owner, name, showInTrace);
        this.size = size;
        this.time = waitingtime;
    }

    @Override
    public void lifeCycle() throws SuspendExecution {
            //the servicer is always on duty and will never stop working
            while (true) {




            }
    }


    private void process(){


    }



}

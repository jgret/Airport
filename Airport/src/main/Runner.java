package main;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import desmoj.core.util.AccessPoint;
import desmoj.core.util.SimRunListener;
import desmoj.extensions.experimentation.ui.ExperimentStarterApplication;
import desmoj.extensions.experimentation.ui.GraphicalObserverContext;
import desmoj.extensions.experimentation.ui.HistogramPlotter;
import desmoj.extensions.experimentation.ui.TimeSeriesPlotter;
import desmoj.extensions.experimentation.util.AccessUtil;
import desmoj.extensions.experimentation.util.ExperimentRunner;


/**
 * @author Nicolas Knaak
 * @author Philip Joschko
 *
 * A demo experiment runner
 */
public class Runner extends ExperimentRunner {
	
	public Runner() {
		super(); 
	}
	
	public Runner(Airport m) {
		super(m);
	}
	
	@Override
	public SimRunListener[] createSimRunListeners(GraphicalObserverContext c) {
		Airport model = (Airport)getModel();
//		TimeSeriesPlotter tp1 = new TimeSeriesPlotter("Trucks",c, model.trucksArrived, 360,360);
//		tp1.addTimeSeries(model.trucksServiced);
//		HistogramPlotter hp = new HistogramPlotter("Truck Wait Times", c, model.waitTimeHistogram,"h", 360,360, 365,0);
		return new SimRunListener[] {};
	}
	
	@Override
	public Map<String,AccessPoint> createParameters() {
		Map<String,AccessPoint> pm = super.createParameters();
		AccessUtil.setValue(pm, EXP_STOP_TIME, 1500.0);
		AccessUtil.setValue(pm, EXP_TRACE_STOP, 100.0);
		AccessUtil.setValue(pm, EXP_REF_UNIT, TimeUnit.MILLISECONDS);
		return pm;
	}
	
	public static void main(String[] args) throws Exception {
		new ExperimentStarterApplication(Airport.class, Runner.class).setVisible(true);
	}
}

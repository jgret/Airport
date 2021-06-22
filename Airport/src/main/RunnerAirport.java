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
public class RunnerAirport extends ExperimentRunner {
	
	public RunnerAirport() {
		super(); 
	}
	
	public RunnerAirport(Airport m) {
		super(m);
	}
	
	@Override
	public SimRunListener[] createSimRunListeners(GraphicalObserverContext c) {
		Airport model = (Airport)getModel();
		TimeSeriesPlotter tp1 = new TimeSeriesPlotter("Car Rent", c, model.dataPeopleInBus, 360, 360, 0, 0);
		TimeSeriesPlotter tp2 = new TimeSeriesPlotter("Terminal 1", c, model.dataPeopleInBus, 360,360, 360, 0);
		TimeSeriesPlotter tp3 = new TimeSeriesPlotter("Terminal 2", c, model.dataPeopleInBus, 360, 360, 720, 0);
		TimeSeriesPlotter tp4 = new TimeSeriesPlotter("Bus", c, model.dataPeopleInBus, 360, 360, 1080, 0);
		tp1.addTimeSeries(model.dataPeopleCarRent);
		tp2.addTimeSeries(model.dataPeopleTerminal1);
		tp3.addTimeSeries(model.dataPeopleTerminal2);
		tp4.addTimeSeries(model.dataPeopleInBus);

		HistogramPlotter hp1 = new HistogramPlotter("Car Rent Wait Times", c, model.dataWaitTimesCarRent,"h", 360,360, 0, 360);
		HistogramPlotter hp2 = new HistogramPlotter("Terminal 1 Wait Times", c, model.dataWaitTimesT1,"h", 360,360, 360, 360);
		HistogramPlotter hp3 = new HistogramPlotter("Terminal 2 Wait Times", c, model.dataWaitTimesT2,"h", 360,360, 720, 360);
		return new SimRunListener[] {tp1, tp2, tp3, tp4, hp1, hp2, hp3};
	}
	
	@Override
	public Map<String,AccessPoint> createParameters() {
		Map<String,AccessPoint> pm = super.createParameters();
		AccessUtil.setValue(pm, EXP_STOP_TIME, 86400.0);
		AccessUtil.setValue(pm, EXP_TRACE_STOP, 1500.0);
		AccessUtil.setValue(pm, EXP_REF_UNIT, TimeUnit.SECONDS);
		AccessUtil.setValue(pm, EXP_EPSILON, TimeUnit.MILLISECONDS);
		return pm;
	}
	
	public static void main(String[] args) throws Exception {
		new ExperimentStarterApplication(Airport.class, RunnerAirport.class).setVisible(true);
	}
}

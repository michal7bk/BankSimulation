

import dissimlab.random.SimGenerator;
import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimParameters.SimDateField;

public class StartNiecierpliwienia extends BasicSimEvent<Klient, Object> {
	private SimGenerator generator;
	private Klient parent;

	public StartNiecierpliwienia(Klient parent, double delay) throws SimControlException {
		super(parent, delay);
		generator = new SimGenerator();
		this.parent = parent;
	}

	public StartNiecierpliwienia(Klient parent) throws SimControlException {
		super(parent);
		generator = new SimGenerator();
		this.parent = parent;
	}

	@Override
	protected void onInterruption() throws SimControlException {
	}

	@Override
	protected void onTermination() throws SimControlException {
	}

	@Override
	protected void stateChange() throws SimControlException {
		System.out.println(simTime() + ": Początek niecierpliwości klienta nr: " + parent.getTenNr());
		double odstep = generator.normal(15.0, 1.0);
		parent.koniecNiecierpliwosci = new KoniecNiecierpliwienia(parent, odstep);
	}

	@Override
	public Object getEventParams() {
		return null;
	}
}
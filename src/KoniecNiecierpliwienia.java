

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimParameters.SimDateField;

public class KoniecNiecierpliwienia extends BasicSimEvent<Klient, Object> {
	private Klient parent;
	public double lutraconych;

	public KoniecNiecierpliwienia(Klient parent, double delay) throws SimControlException {
		super(parent, delay);
		this.parent = parent;
	}

	public KoniecNiecierpliwienia(Klient parent) throws SimControlException {
		super(parent);
		this.parent = parent;
	}

	@Override
	protected void onInterruption() throws SimControlException {
		System.out.println(simTime() + ": Przerwanie niecierpliwości klienta nr: " + parent.getTenNr());
	}

	@Override
	protected void onTermination() throws SimControlException {
	}

	@Override
	protected void stateChange() throws SimControlException {
		System.out.println(simTime() + ": Koniec niecierpliwości klienta nr: " + parent.getTenNr());
		Kolejka.usunWskazany(parent, simTime());
		System.out.println(simTime() + ": Usunięto z kolejki klienta nr: " + parent.getTenNr());
		lutraconych = Main.MVutraconeZgl.getValue();
		Main.MVutraconeZgl.setValue(++lutraconych);
}

	@Override
	public Object getEventParams() {
		return null;
	}
}
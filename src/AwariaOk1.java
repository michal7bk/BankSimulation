import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

public class AwariaOk1 extends BasicSimEvent<Okienko, Klient> {

	private Okienko okParent;

	public AwariaOk1(Okienko parent, double delay) throws SimControlException {
		super(parent, delay);
		this.okParent = parent;
	}

	@Override
	protected void onInterruption() throws SimControlException {
	}

	@Override
	protected void onTermination() throws SimControlException {
	}

	@Override
	protected void stateChange() throws SimControlException {
		okParent.setWolne(true);
		System.out.println(simTime() + ": Koniec Awarii okienka 1");
		if (Kolejka.kolejka.size() > 0) {
			okParent.rozpocznijObsluge = new RozpocznijObsluge(okParent);
		}
	}

	@Override
	public Object getEventParams() {
		return null;
	}
}

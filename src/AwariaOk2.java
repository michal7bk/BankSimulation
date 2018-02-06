import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

public class AwariaOk2 extends BasicSimEvent<Okienko2, Klient> {

	private Okienko2 okParentBis;

	public AwariaOk2(Okienko2 parent, double delay) throws SimControlException {
		super(parent, delay);
		this.okParentBis = parent;
	}

	@Override
	protected void onInterruption() throws SimControlException {

	}

	@Override
	protected void onTermination() throws SimControlException {

	}

	@Override
	protected void stateChange() throws SimControlException {
		okParentBis.setWolne(true);
		System.out.println(simTime() + ": Koniec Awarii okienka 2");
		if (Kolejka.kolejka.size() > 0) {
			okParentBis.rozpocznijObsluge = new RozpocznijObsluge2(okParentBis);
		}
	}

	@Override
	public Object getEventParams() {
		return null;
	}
}

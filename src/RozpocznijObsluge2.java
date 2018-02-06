
import dissimlab.random.SimGenerator;
import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

public class RozpocznijObsluge2 extends BasicSimEvent<Okienko2, Klient> {
	private Okienko2 okParentBis;
	private SimGenerator generator;
	public boolean flag;

	public RozpocznijObsluge2(Okienko2 parent, double delay) throws SimControlException {
		super(parent, delay);
		generator = new SimGenerator();
		this.okParentBis = parent;
	}

	public RozpocznijObsluge2(Okienko2 parent) throws SimControlException {
		super(parent);
		generator = new SimGenerator();
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
		if (Kolejka.kolejka.size() > 0) {
			okParentBis.setWolne(false);
			Klient k = okParentBis.pobierz();
			k.koniecNiecierpliwosci.interrupt();
			double czasObslugi = generator.normal(36.0, 1.0); 
			okParentBis.MVczasy_oczekiwania.setValue(simTime() - k.getczasWejscia(), simTime());
			System.out.println(simTime() + ": Okienko 2 - Początek obsługi klienta nr: " + k.getTenNr());
			k.setCzasRozpoczecia(simTime());
			okParentBis.zakonczObsluge = new ZakonczObsluge2(okParentBis, czasObslugi, k);
		}

	}

	@Override
	public Object getEventParams() {
		return null;
	}
}
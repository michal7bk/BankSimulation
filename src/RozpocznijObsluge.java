
import dissimlab.random.SimGenerator;
import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimParameters.SimDateField;

public class RozpocznijObsluge extends BasicSimEvent<Okienko, Klient> {
	private Okienko okParent;
	private SimGenerator generator;


	public RozpocznijObsluge(Okienko parent, double delay) throws SimControlException {
		super(parent, delay);
		generator = new SimGenerator();
		this.okParent = parent;

	}

	public RozpocznijObsluge(Okienko parent) throws SimControlException {
		super(parent);
		generator = new SimGenerator();
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

		if (Kolejka.kolejka.size() > 0) {
			okParent.setWolne(false);
			Klient k = okParent.pobierz();
			k.koniecNiecierpliwosci.interrupt();
			double czasObslugi = generator.normal(42.0, 1.0);
			okParent.MVczasy_oczekiwania.setValue(simTime() - k.getczasWejscia(), simTime());
			System.out.println(simTime() + ": Okienko 1 - Początek obsługi klienta nr: " + k.getTenNr());
			k.setCzasRozpoczecia(simTime());
			okParent.zakonczObsluge = new ZakonczObsluge(okParent, czasObslugi, k);
		}

	}

	@Override
	public Object getEventParams() {
		return null;
	}
}
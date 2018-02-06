
import dissimlab.random.SimGenerator;
import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;



public class ZakonczObsluge extends BasicSimEvent<Okienko, Klient> {
	private Okienko okParent;
	private SimGenerator generator;
	public boolean powrot;
	public boolean awaria;

	public ZakonczObsluge(Okienko ok, double delay, Klient k) throws SimControlException {
		super(ok, delay, k);
		this.okParent = ok;
		generator = new SimGenerator();
	}


	@Override
	protected void onInterruption() throws SimControlException {
	}

	@Override
	protected void onTermination() throws SimControlException {
	}

	@Override
	protected void stateChange() throws SimControlException {
		awaria = generator.probability(Main.pbienstwo1Awarii);
		if (!awaria) {
			okParent.setWolne(true);
			powrot = generator.probability(Main.pbienstwoPowrotu);
			if (powrot) {
				Kolejka.dodaj(transitionParams, simTime());
			} else {
				System.out.println(simTime() + ": Przy Okienku 1 skończono obsługe klienta nr: " + transitionParams.getTenNr());
				Main.MVczas_w_banku.setValue(simTime() - transitionParams.getczasWejscia(), simTime());
				okParent.MVczasy_obslugi.setValue(simTime() - transitionParams.getCzasRozpoczecia(), simTime());
			}
			if (Kolejka.kolejka.size() > 0) {
				okParent.rozpocznijObsluge = new RozpocznijObsluge(okParent);
			}
		} else {
			System.out.println(simTime() + ":przy okienku 1 nastąpiła awaria komputera - klient nr: " + transitionParams.getTenNr()
					+ " wraca do kolejki z najwyższym priorytetem");
			transitionParams.setPrio(10);
			Kolejka.dodaj(transitionParams, simTime());
			double czasAwarii = generator.normal(9.0, 1.5);
			okParent.AwariaOk1 = new AwariaOk1(okParent, czasAwarii);
		}
	}

	@Override
	public Object getEventParams() {
		return (Klient) transitionParams;
	}
}
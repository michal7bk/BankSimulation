
import dissimlab.random.SimGenerator;
import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

public class ZakonczObsluge2 extends BasicSimEvent<Okienko2, Klient> {
	private Okienko2 okParent2;
	private SimGenerator generator;
	public boolean powrot;
	public boolean awaria;

	public ZakonczObsluge2(Okienko2 ok2, double delay, Klient k) throws SimControlException {
		super(ok2, delay, k);
		this.okParent2 = ok2;
		generator = new SimGenerator();
	}

	@Override
	protected void onInterruption() throws SimControlException {
		System.out.println(simTime() + ": !Przerwanie obsługi przy klienta nr: " + transitionParams.getTenNr());
	}

	@Override
	protected void onTermination() throws SimControlException {

	}

	@Override
	protected void stateChange() throws SimControlException {
		awaria = generator.probability(Main.pbienstwo2Awarii);
		if (!awaria) {
			okParent2.setWolne(true);
			powrot = generator.probability(Main.pbienstwoPowrotu);
			if (powrot) {
				Kolejka.dodaj(transitionParams, simTime());
			} else {
				System.out.println(simTime() + ": Przy Okienku 2 skończono obsługe klienta nr: " + transitionParams.getTenNr());
				Main.MVczas_w_banku.setValue(simTime() - transitionParams.getczasWejscia());
				okParent2.MVczasy_obslugi.setValue(simTime() - transitionParams.getCzasRozpoczecia(), simTime());
			}
			if (Kolejka.kolejka.size() > 0) {
				okParent2.rozpocznijObsluge = new RozpocznijObsluge2(okParent2);
			}
		} else {
			System.out.println(simTime() + ": przy okienku 2 nastąpiła awaria komputera - klient nr: " + transitionParams.getTenNr()
					+ " wraca do kolejki z najwyższym priorytetem");
			transitionParams.setPrio(10);
			Kolejka.dodaj(transitionParams, simTime());
			double czasAwarii = generator.normal(10.0, 1.0);
			okParent2.AwariaOk2 = new AwariaOk2(okParent2, czasAwarii);
		}
	}

	@Override
	public Object getEventParams() {
		return (Klient) transitionParams;
	}
}
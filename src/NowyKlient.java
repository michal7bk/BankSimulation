
import dissimlab.random.SimGenerator;
import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

public class NowyKlient extends BasicSimEvent<Bank, Object> {
	private SimGenerator generator;
	private Bank parent;
	public static double ilKlientow;
	public boolean awaria;
	public boolean awaria2;
	public boolean awaria3;

	public NowyKlient(Bank parent, double delay) throws SimControlException {
		super(parent, delay);
		generator = new SimGenerator();
	}

	public NowyKlient(Bank parent) throws SimControlException {
		super(parent);
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
		awaria2 = generator.probability(Main.pbienstwo2Awarii);
		double opoznienie = generator.normal(4.0, 1.0);
		parent = getSimObj();
		double czasAwarii = generator.normal(9.0, 1.5);
		Klient k = new Klient(generator.uniformInt(1, 10), simTime());
		ilKlientow = k.getTenNr();
		Kolejka.dodaj(k, simTime());
		System.out.println(simTime() + ": Przybył nowy klient do Banku i otrzymał nr: " + k.getTenNr());
		if (!awaria) {
			if (!awaria2) {
					if (Kolejka.kolejka.size() == 1 && parent.ok.isWolne()) {
						parent.ok.rozpocznijObsluge = new RozpocznijObsluge(parent.ok);
					} else if (Kolejka.kolejka.size() == 1 && parent.ok2.isWolne()) {
						parent.ok2.rozpocznijObsluge = new RozpocznijObsluge2(parent.ok2);
					}
					parent.nk = new NowyKlient(parent, opoznienie);
				} else {
					System.out.println(simTime() + ": przy okienku 2 nastąpiła awaria komputera - ");
					parent.ok2.AwariaOk2 = new AwariaOk2(parent.ok2, czasAwarii);
					parent.nk = new NowyKlient(parent, opoznienie);
				}
			} else {
				System.out.println(simTime() + ": przy okienku 1 nastąpiła awaria komputera - ");
				parent.ok.AwariaOk1 = new AwariaOk1(parent.ok, czasAwarii);
				parent.nk = new NowyKlient(parent, opoznienie);
			}
		}

	@Override
	public Object getEventParams() {
		return null;
	}
}

import dissimlab.broker.INotificationEvent;
import dissimlab.broker.IPublisher;
import dissimlab.monitors.MonitoredVar;
import dissimlab.simcore.BasicSimObj;
import dissimlab.simcore.SimControlException;

public class Okienko extends BasicSimObj {
	private boolean wolne = true;
	public RozpocznijObsluge rozpocznijObsluge;
	public ZakonczObsluge zakonczObsluge;
	public AwariaOk1 AwariaOk1;
	public MonitoredVar MVczasy_obslugi;
	public MonitoredVar MVczasy_oczekiwania;

	public Okienko() throws SimControlException {
		MVczasy_oczekiwania = new MonitoredVar();
		MVczasy_obslugi = new MonitoredVar();
	}

	public Klient pobierz() {
		Klient k = (Klient) Kolejka.pobierz();
		Main.MVdlKolejki.setValue(Kolejka.kolejka.size(), simTime());
		return k;
	}

	public boolean isWolne() {
		return wolne;
	}

	public void setWolne(boolean wolne) {
		this.wolne = wolne;
	}

	@Override
	public void reflect(IPublisher publisher, INotificationEvent event) {

	}

	@Override
	public boolean filter(IPublisher publisher, INotificationEvent event) {
		return false;
	}
}
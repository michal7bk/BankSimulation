
import dissimlab.broker.INotificationEvent;
import dissimlab.broker.IPublisher;
import dissimlab.simcore.BasicSimObj;
import dissimlab.simcore.SimControlException;

public class Klient extends BasicSimObj {
	double czasWejscia;
	double czasRozpoczecia;
	private int priorytet;
	static int nr = 0;
	int tenNr;
	StartNiecierpliwienia startNiecierpliwienia;
	public KoniecNiecierpliwienia koniecNiecierpliwosci;

	public Klient(int priorytet, double Czas) throws SimControlException {
		this.priorytet = priorytet;
		czasWejscia = Czas;
		setTenNr();
		startNiecierpliwienia = new StartNiecierpliwienia(this);
	}

	public double getczasWejscia() {
		return czasWejscia;
	}

	public void setczasWejscia(double czasWejscia) {
		this.czasWejscia = czasWejscia;
	}

	public double getCzasRozpoczecia() {
		return czasRozpoczecia;
	}

	public void setCzasRozpoczecia(double czasRozpoczecia) {
		this.czasRozpoczecia = czasRozpoczecia;
	}

	@Override
	public void reflect(IPublisher publisher, INotificationEvent event) {

	}

	@Override
	public boolean filter(IPublisher publisher, INotificationEvent event) {
		return false;
	}

	public void setTenNr() {
		this.tenNr = nr++;
	}

	public int getTenNr() {
		return tenNr;
	}

	public void setPrio(int a) {
		this.priorytet = a;
	}

	public int getPrio() {
		return this.priorytet;
	}

	public int getNum() {
		return this.nr;
	}

}
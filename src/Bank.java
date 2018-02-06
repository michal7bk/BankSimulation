

import dissimlab.broker.INotificationEvent;
import dissimlab.broker.IPublisher;
import dissimlab.simcore.BasicSimObj;
import dissimlab.simcore.SimControlException;

public class Bank extends BasicSimObj {
	public NowyKlient nk;
	public Okienko ok;
	public Okienko2 ok2;

	public Bank(Okienko ok, Okienko2 ok2) throws SimControlException {
		nk = new NowyKlient(this, 0.0);
		this.ok = ok;
		this.ok2 = ok2;
	}

	@Override
	public void reflect(IPublisher publisher, INotificationEvent event) {
	}

	@Override
	public boolean filter(IPublisher publisher, INotificationEvent event) {
		return false;
	}
}

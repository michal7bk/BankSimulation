
import java.util.LinkedList;

public class Kolejka {

	public static LinkedList<Klient> kolejka;
	public static int rozmiar;

	public Kolejka() {
		kolejka = new LinkedList<Klient>();
	}

	public static boolean dodaj(Klient k, double czas) {
		if (Kolejka.kolejka.size() < Kolejka.rozmiar) {
			if (kolejka.isEmpty()) {
				kolejka.addLast(k);
				Main.MVdlKolejki.setValue(Kolejka.kolejka.size(), czas);
				return true;
			} else if (k.getPrio() > kolejka.getLast().getPrio()) {
				kolejka.addLast(k);
				Main.MVdlKolejki.setValue(Kolejka.kolejka.size(), czas);
				return true;
			} else {
				for (int i = 0; i < kolejka.size(); i++) {
					if (k.getPrio() <= kolejka.get(i).getPrio()) {
						kolejka.add(i, k);
						Main.MVdlKolejki.setValue(Kolejka.kolejka.size(), czas);
						return true;
					} else if (k.getPrio() > kolejka.get(i).getPrio() && k.getPrio() <= kolejka.get(i + 1).getPrio()) {
						kolejka.add(i + 1, k);
						Main.MVdlKolejki.setValue(Kolejka.kolejka.size(), czas);
						return true;
					}
				}
			}
		}
		return false;
	}

	public static void usunWskazany(Klient k, double czas) {
		for (int i = 0; i < kolejka.size(); i++) {
			if ((k.getPrio() == kolejka.get(i).getPrio()) && (k.getNum() == kolejka.get(i).getNum())) {
				kolejka.remove(i);
				Main.MVdlKolejki.setValue(Kolejka.kolejka.size(), czas);
				break;
			}
		}
	}

	public static Klient pobierz() {
		return kolejka.removeLast();
	}

	public static void setRozm(int a) {
		rozmiar = a;
	}

}



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Properties;

import dissimlab.monitors.Diagram;
import dissimlab.monitors.MonitoredVar;
import dissimlab.monitors.Statistics;
import dissimlab.monitors.Diagram.DiagramType;
import dissimlab.simcore.SimControlEvent;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimManager;
import dissimlab.simcore.SimParameters.SimControlStatus;

public class Main {
	public static double pbienstwoPowrotu;
	public static double pbienstwo1Awarii;
	public static double pbienstwo2Awarii;
	private static Properties prop = null;
	public static MonitoredVar MVdlKolejki;
	public static MonitoredVar MVutraconeZgl;
	public static MonitoredVar MVczas_w_banku;
	
	public static void main(String[] args) throws IOException {
		
			MVdlKolejki = new MonitoredVar();
			MVutraconeZgl = new MonitoredVar();
			MVczas_w_banku = new MonitoredVar();
			prop = new Properties();
			String InFile = "Bank/resources/plik.properties";
			InputStream input = new FileInputStream(InFile);
			prop.load(input);
			File OutFile = new File("Bank/resources/log.txt");
			PrintStream ps = new PrintStream(OutFile);
			System.setOut(ps);
			pbienstwoPowrotu = Double.valueOf(prop.getProperty("pPowrotu"));
			pbienstwo1Awarii = Double.valueOf(prop.getProperty("pAwarii"));
			pbienstwo2Awarii = Double.valueOf(prop.getProperty("pAwarii2"));
		try {

			SimManager model = SimManager.getInstance();
			Okienko ok = new Okienko();
			Okienko2 ok2 = new Okienko2();
			Kolejka k = new Kolejka();
			Kolejka.setRozm(Integer.valueOf(prop.getProperty("rozmiar_kolejki")));
			Bank generatorZgl = new Bank(ok, ok2);
			SimControlEvent stopEvent = new SimControlEvent(4000.0, SimControlStatus.STOPSIMULATION);
			model.startSimulation();
			double result = new BigDecimal(Statistics.max(MVutraconeZgl)).setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
			System.out.println("ilość zniecierpliwionych klientow " + result);
			
			result = new BigDecimal(Statistics.max(MVutraconeZgl) / NowyKlient.ilKlientow).setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
			System.out.println("prawdopodobieństwo rezygnacji z obsługi przez klienta " + result);
			result = NowyKlient.ilKlientow;
			System.out.println("liczba klientów w oddziale w czasie trwania symulacji " + result);
			result = new BigDecimal(Statistics.max(MVdlKolejki)).setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
			System.out.println("Maksymalna długosc kolejki  " + result);

			result = new BigDecimal(Statistics.max(ok.MVczasy_oczekiwania)).setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
			System.out.println("Maksymalna wartość czasu oczekiwania na obsługę: " + result);
			
			result = new BigDecimal(Statistics.max(ok.MVczasy_obslugi)).setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
			System.out.println("Maksymalna wartość czasu obsługi przy 1 okienku: " + result);
			
			result = new BigDecimal(Statistics.max(ok2.MVczasy_obslugi)).setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
			System.out.println("Maksymalna wartość czasu obsługi przy 2 okienku: " + result);
			
			Diagram d1 = new Diagram(DiagramType.HISTOGRAM, "Czas obsługiwania przy 2 okienku");
			d1.add(ok2.MVczasy_obslugi, java.awt.Color.BLUE);
			d1.show();
			
			Diagram d5 = new Diagram(DiagramType.HISTOGRAM, "Czas obsługiwania przy 1 okienku:");
			d5.add(ok.MVczasy_obslugi, java.awt.Color.BLACK);
			d5.show();

			Diagram d2 = new Diagram(DiagramType.HISTOGRAM, "Czasy oczekiwania na obsługę");
			d2.add(ok.MVczasy_oczekiwania, java.awt.Color.RED);
			d2.show();

			Diagram d3 = new Diagram(DiagramType.TIME_FUNCTION, "Długość kolejki");
			d3.add(MVdlKolejki, java.awt.Color.ORANGE);
			d3.show();
			
			
		} catch (SimControlException e) {
			e.printStackTrace();
		}
	}
}

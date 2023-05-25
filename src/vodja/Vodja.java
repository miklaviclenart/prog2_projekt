package vodja;

import gui.Okno;
import inteligenca.Inteligenca;
import logika.Igra;
import logika.Igralec;
import splosno.Poteza;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.swing.SwingWorker;

public class Vodja {
    
    public static Map<Igralec,VrstaIgralca> vrstaIgralca;
	
	public static Okno okno;
	
	public static Igra igra = null;
	
	public static boolean clovekNaVrsti = false;
	
    // IMPLEMENTIRAJ INTELIGENCO
	public static Inteligenca inteligenca = new Inteligenca();
		
	public static void igramoNovoIgro () {
		igra = new Igra ();
		igramo ();
	}

    public static void igramo () {
        okno.osveziGUI();
        switch (igra.stanje()) {
            case ZMAGA_BEL:
            case ZMAGA_CRN:
            case NEODLOCENO:
                return; // odhajamo iz metode igramo
            case V_TEKU:
                Igralec igralec = igra.naPotezi();
                VrstaIgralca vrstaNaPotezi = vrstaIgralca.get(igralec);
                switch (vrstaNaPotezi) {
                    case C:
                        clovekNaVrsti = true;
                        break;
                    case R:
                        igrajRacunalnikovoPotezo();
                        break;
                }
        }
    }

    public static void igrajRacunalnikovoPotezo() {
        Igra zacetnaIgra = igra;
        SwingWorker<Poteza, Void> worker = new SwingWorker<Poteza, Void>() {
            @Override
            protected Poteza doInBackground() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {};
                return inteligenca.izberiPotezo(igra);
            }
    
            @Override
            protected void done() {
                Poteza poteza = null;
                    try {
                        poteza = get();
                    } catch (Exception e) {};
                    if (igra == zacetnaIgra && poteza != null) {
                        igra.odigraj(poteza);
                        igramo();
                    }
                }
        };
        worker.execute();
    }

    public static void igrajClovekovoPotezo(Poteza poteza) {
        if (igra.odigraj(poteza)) {
            clovekNaVrsti = false;
            igramo();
        }
    }
}

package inteligenca;

import splosno.*;
import logika.Igra;

public class Inteligenca extends KdoIgra {
    
    public Inteligenca() {
        super("Lenart");
    }

    public Poteza izberiPotezo(Igra igra) {

        // Za začetek naredi naključno potezo.
        int randomX = (int) (Math.random() * (igra.getVelikost() - 1));
        int randomY = (int) (Math.random() * (igra.getVelikost() - 1));
        Poteza poteza = new Poteza(randomX, randomY);
        return poteza;
    }
}

package logika;

import java.util.ArrayList;
import java.util.List;

import splosno.Poteza;

public class Igra {
    private Polje[][] polje;
    public static final int velikost = 9;
    private Igralec naPotezi;
    
    public Igra() {
        polje = new Polje[velikost][velikost];
        for (int i = 0; i < velikost; i++) {
            for (int j = 0; j < velikost; j++) {
                polje[i][j] = Polje.PRAZNO;
            }
        }

        naPotezi = Igralec.CRN;
    }

    public int getVelikost() {
        return velikost;
    }

    public Polje[][] getPolje() {
        return polje;
    }

    // Seznam možnih potez (prosta polja).
    public List<Poteza> getMoznePoteze() {
        List<Poteza> moznePoteze = new ArrayList<>();

        for (int vrstica = 0; vrstica < velikost; vrstica++) {
            for (int stolpec = 0; stolpec < velikost; stolpec++) {
                if (polje[vrstica][stolpec] == Polje.PRAZNO) {
                    moznePoteze.add(new Poteza(vrstica, stolpec));
                }
            }
        }

        return moznePoteze;
    }

    public boolean odigraj(Poteza poteza) {
        if (jeVeljavna(poteza)) {
            polje[poteza.x()][poteza.y()] = naPotezi.getPolje();
            naPotezi = naPotezi.nasprotnik();
            return true;
        }

        return false;
    }

    private boolean jeVeljavna(Poteza poteza) {
        int vrstica = poteza.x();
        int stolpec = poteza.y();

        if (vrstica < 0 || vrstica >= velikost || stolpec < 0 || stolpec >= velikost) {
            return false;
        }

        return polje[vrstica][stolpec] == Polje.PRAZNO;
    }

    public Igralec naPotezi() {
        return naPotezi;
    }

}

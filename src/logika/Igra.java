package logika;

import java.util.ArrayList;
import java.util.List;

import splosno.Poteza;

public class Igra {
    
    // Velikost igralne plošče je 9 x 9.
    public static final int velikost = 9;

    // Igralno polje
    private Polje[][] polje;

    // Igralec, ki je trenutno na potezi.
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

    public boolean staPovezana(int[] polje1, int[] polje2) {
        return Math.abs(polje1[0] - polje2[0]) + Math.abs(polje1[1] - polje2[1]) == 1;
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

    public boolean konecIgre() {
        return getMoznePoteze().isEmpty();
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

    public boolean preveriZmago() {
        // Potrembno implementirati
        return true;
    }

    public Igralec naPotezi() {
        return naPotezi;
    }

    private Igralec zmagovalec() {
        for (int i = 1; i < polje.length - 1; i++) {
            for (int j = 1; j < polje[0].length - 1; j++) {
                if (jeObkrozen(i, j)) {
                    return polje[i][j] == Polje.BEL ? Igralec.CRN : Igralec.BEL;
                }
            }
        }

        return null;
    }

    private boolean jeObkrozen(int i, int j) {
        Polje nasprotnik = polje[i][j] == Polje.BEL ? Polje.CRN : Polje.BEL;

        if (polje[i - 1][j] == nasprotnik && polje[i + 1][j] == nasprotnik && 
                polje[i][j - 1] == nasprotnik && polje[i][j + 1] == nasprotnik) {
                    return true;
                }

                return false;
    }

    public Stanje stanje() {
        // Ali imamo zmagovalca?
        Igralec zmagovalec = zmagovalec();
        if (zmagovalec != null) {
            return switch (zmagovalec) {
                case BEL -> Stanje.ZMAGA_BEL;
                case CRN -> Stanje.ZMAGA_CRN;
            };
        }

        return Stanje.V_TEKU;
    }

}

package gui;

import splosno.Poteza;
import logika.Igra;
import logika.Polje;
import vodja.Vodja;

import javax.swing.JPanel;
import java.awt.event.MouseListener;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;





/**
 * Pravokotno območje, v katerem je narisano igralno polje.
 */
//@SuppressWarnings("serial")
public class Platno extends JPanel implements MouseListener {
	
	public Platno() {
		setBackground(Color.GRAY);
		this.addMouseListener(this);
		
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(400, 400);
	}

	
	// Relativna širina črte
	private final static double LINE_WIDTH = 0.08;
	
	// Širina enega kvadratka
	private double squareWidth() {
		return Math.min(getWidth(), getHeight()) / Igra.velikost;
	}
	
	// Relativni prostor okoli X in O
	private final static double PADDING = 0.18;
	
	private void paintBEL(Graphics2D g2, int i, int j) {
		double w = squareWidth();
		double d = w * (1.0 - LINE_WIDTH - 2.0 * PADDING); // premer O
		double x = w * (i) - (d / 2) + LINE_WIDTH * 3.0;
        double y = w * (j) - (d / 2) + LINE_WIDTH * 3.0;
		g2.setColor(Color.WHITE);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
        g2.fillOval((int) x, (int) y, (int) d, (int) d);
	}
	
	private void paintCRN(Graphics2D g2, int i, int j) {
        double w = squareWidth();
        double d = w * (1.0 - LINE_WIDTH - 2.0 * PADDING); // premer O
        double x = w * (i) - (d / 2) + LINE_WIDTH * 3.0;
        double y = w * (j) - (d / 2) + LINE_WIDTH * 3.0;
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
        g2.fillOval((int) x, (int) y, (int) d, (int) d);
    }
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        double w = squareWidth();

        // črte
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
        int stevilo_crt = Igra.velikost + 1;
        for (int i = 1; i < stevilo_crt; i++) {
            g2.drawLine((int) (i * w),
                    (int) (0),
                    (int) (i * w),
                    (int) (stevilo_crt * w));
            g2.drawLine((int) (0),
                    (int) (i * w),
                    (int) (stevilo_crt * w),
                    (int) (i * w));
        }

        // draw stones
        Polje[][] polje;
        ;
        if (Vodja.igra != null) {
            polje = Vodja.igra.getPolje();
            for (int i = 0; i < Igra.velikost; i++) {
                for (int j = 0; j < Igra.velikost; j++) {
                    switch (polje[i][j]) {
                        case CRN:
                            paintCRN(g2, i + 1, j + 1);
                            break;
                        case BEL:
                            paintBEL(g2, i + 1, j + 1);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }
	
	@Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse clicked at: " + e.getX() + ", " + e.getY());
        if (Vodja.clovekNaVrsti) {
            int x = e.getX();
            int y = e.getY();
            int w = (int) (squareWidth());
            double pi = (double) x / (double) w;
            int i = pi - Math.floor(pi) < 0.5 ? (int) Math.floor(pi) : (int) Math.floor(pi) + 1;
            double di = (x % w) / squareWidth();
            double pj = (double) y / (double) w;
            int j = pj - Math.floor(pj) < 0.5 ? (int) Math.floor(pj) : (int) Math.floor(pj) + 1;
            double dj = (y % w) / squareWidth();
            if (0 <= i && i < Igra.velikost &&
                    0.5 * LINE_WIDTH < di && di < 1.0 - 0.5 * LINE_WIDTH &&
                    0 <= j && j < Igra.velikost &&
                    0.5 * LINE_WIDTH < dj && dj < 1.0 - 0.5 * LINE_WIDTH) {
                Vodja.igrajClovekovoPotezo(new Poteza(i - 1, j - 1));
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}

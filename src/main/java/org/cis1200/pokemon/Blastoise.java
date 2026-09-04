package org.cis1200.pokemon;

import java.awt.*;

/**
 * A game object displayed using an image.
 *
 * Note that the image is read from the file when the object is constructed, and
 * that all objects created by this constructor share the same image data (i.e.
 * img is static). This is important for efficiency: your program will go very
 * slowly if you try to create a new BufferedImage every time the draw method is
 * invoked.
 */
public class Blastoise extends Tower {

    public Blastoise(int courtWidth, int courtHeight, int x, int y) {
        super(x, y, 150, 150, courtWidth,
                courtHeight, 500, 10, 10,
                200, 0, 200, "Blastoise", false, 2);
        loadGIF("files/blastoise_clean.gif", 100, 100);
    }

    public boolean canMega() {
        return true;
    }

    public void tryMegaEvolve() {
        System.out.println("Mega evolve attempt for " + getName());

        if (isMega()) {
            System.out.println("Already Mega");
            return;
        }

        if (getLevel() < getMegaLVL()) {
            System.out.println("Level too low");
            return;
        }

        if (GameCourt.pokeDollars < 500) {
            System.out.println("Not enough $$");
            return;
        }

        System.out.println("MEGA EVOLUTION SUCCESSFUL!");

        GameCourt.pokeDollars -= 500;
        setMega(true);
        addDamage(getDamage());
        addRange(100);
        setAttackSpeed(3);
        double hFactor = 199.0 / 197.0;
        int nWidth = 150;
        int nHeight = (int) (nWidth * hFactor);

        loadGIF("files/megaBlastoise_clean.gif", nWidth, nHeight);
        setPy(getPy() - getHeight() / 4);
    }

    public Point attackStart() {
        if (isMega()) {
            int sx = getPx() + 25;
            int sy = getPy() - getHeight() / 4;
            return new Point(sx, sy);
        } else {
            int sx = getPx() + 30;
            int sy = getPy() - getHeight() / 3;
            return new Point(sx, sy);
        }
    }
}

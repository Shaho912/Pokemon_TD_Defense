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
public class Charizard extends Tower {

    public Charizard(int courtWidth, int courtHeight, int x, int y) {
        super(x, y, 150, 150, courtWidth,
                courtHeight, 500, 7, 8,
                200, 0, 200, "Charizard", false, 2);
        loadGIF("files/charizardSprite.gif", 175, 175);
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
        double hFactor = 0.66459627329;
        int nWidth = 200;
        int nHeight = (int) (nWidth * hFactor);

        loadGIF("files/megaCharizardX.gif", nWidth, nHeight);
        setPy(getPy() + getHeight() / 4);
    }

    public Point attackStart() {
        if (isMega()) {
            int sx = getPx() + 50;
            int sy = getPy();
            return new Point(sx, sy);
        } else {
            int sx = getPx() + 50;
            int sy = getPy() + getHeight() / 3;
            return new Point(sx, sy);
        }
    }




}

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
public class Pikachu extends Tower {

    public Pikachu(int courtWidth, int courtHeight, int x, int y) {
        super(x, y, 150, 150, courtWidth,
                courtHeight, 100, 7, 5,
                200, 0, 200, "Pikachu", false, 1000);
        loadGIF("files/pikachueMain.gif", 100, 100);
    }

    public boolean canMega() {
        return false;
    }

    public void tryMegaEvolve() {
        System.out.println("No mega evolve for " + getName());
    }

    public Point attackStart() {
        int sx = getPx() + 25;
        int sy = getPy();
        return new Point(sx, sy);
    }


}

package org.cis1200.pokemon;

import java.awt.*;
import java.util.List;

public class Zubat extends Enemy {

    private List<Point> path;
    public Zubat(int courtWidth, int courtHeight, List<Point> path) {
        super(0, 150, 80, 80, courtWidth,
                courtHeight, 50, 3, 5, 50, "Zubat");
        loadImage("files/zubatNEW.png");
        this.path = path;
    }

    @Override
    public void move() {
        moveAlongPath(path);
        setPy(getPy() + (int)(Math.sin(getPx() * 0.1) * 2));
    }

}


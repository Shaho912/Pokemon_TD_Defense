package org.cis1200.pokemon;

import java.awt.*;
import java.util.List;

public class Houndoom extends Enemy {

    private List<Point> path;
    public Houndoom(int courtWidth, int courtHeight, List<Point> path) {
        super(0, 150, 80, 80, courtWidth,
                courtHeight, 200, 4, 100, 100, "Houndoom");
        loadImage("files/houndoom.png");
        this.path = path;
    }

    @Override
    public void move() {
        moveAlongPath(path);
//        setPy(getPy() + (int)(Math.sin(getPx() * 0.1) * 2));
    }

}


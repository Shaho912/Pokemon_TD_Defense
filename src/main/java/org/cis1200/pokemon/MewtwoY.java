package org.cis1200.pokemon;

import java.awt.*;
import java.util.List;

public class MewtwoY extends Enemy {

    private List<Point> path;
    public MewtwoY(int courtWidth, int courtHeight, List<Point> path) {
        super(0, 150, 80, 80, courtWidth,
                courtHeight, 1000, 2, 50, 200, "MewtwoY");
        loadImage("files/mewtwoY.png");
        this.path = path;
    }

    @Override
    public void move() {
        moveAlongPath(path);
//        setPy(getPy() + (int)(Math.sin(getPx() * 0.1) * 2));
    }

}


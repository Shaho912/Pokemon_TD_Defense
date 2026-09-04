package org.cis1200.pokemon;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.util.LinkedList;

public abstract class Attack extends GameObj {

    private Enemy target;
    private double speed;
    private boolean alive = true;
    private int damage;
    private ImageIcon sprite;
    private BufferedImage img;
    private Tower owner;
    private boolean megaAttack;

    public Attack(int initX, int initY, int width, int height,
                  int courtWidth, int courtHeight,
                  double speed, int damage, Enemy target, Tower owner, boolean megaAttack) {

        super(0, 0, initX, initY, width, height, courtWidth, courtHeight);
        this.speed = speed;
        this.damage = damage + owner.getDamage();
        this.target = target;
        this.owner = owner;
        this.megaAttack = megaAttack;
    }

    public void loadSprite(String filename) {
        sprite = new ImageIcon(filename);
    }

    public void setAlive(boolean value) {
        alive = value;
    }

    public boolean isAlive() {
        return alive;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public Tower getOwner() {
        return owner;
    }

    public void move(LinkedList<Enemy> enemies) {
        if (target == null || target.isDead()) {
            alive = false;
            return;
        }

        double dx = target.getPx() - getPx();
        double dy = target.getPy() - getPy();
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist < speed) {
            setAlive(false);
            return;
        }
        setPx(getPx() + (int) (speed * dx / dist));
        setPy(getPy() + (int) (speed * dy / dist));
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(sprite.getImage(),
                getPx(), getPy(),
                getWidth(), getHeight(),
                null);
    }

}

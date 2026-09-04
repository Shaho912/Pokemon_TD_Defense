package org.cis1200.pokemon;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public abstract class Tower extends GameObj {
    private int cost;
    private int attackSpeed;
    private int damage;
    private int range;
    private ImageIcon sprite;
    private BufferedImage img;
    private int cooldown = 0;
    private int level;
    private int EXP = 0;
    private int maxEXP;
    private String name;
    private FloatingTowerWindow infoWindow;
    private Enemy target = null;
    private boolean mega;
    private int megaLVL;
    public boolean sold = false;




    public Tower(int initX, int initY,
                 int width, int height, int courtWidth, int courtHeight,
                 int cost, int attackSpeed, int damage, int range, int level,
                 int EXP, String name, boolean mega, int megaLVL) {
        super(0, 0, initX, initY, width, height, courtWidth, courtHeight);
        this.cost = cost;
        this.attackSpeed = attackSpeed;
        this.damage = damage;
        this.range = range;
        this.level = 0;
        this.maxEXP = EXP;
        this.name = name;
        this.mega = mega;
        this.megaLVL = megaLVL;
    }

    public void loadImage(String fileName) {
        try {
            img = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }

    public void loadGIF(String filename) {
        sprite = new ImageIcon(filename);
    }

    public void loadGIF(String filename, int newWidth, int newHeight) {
        sprite = new ImageIcon(filename);
        setWidth(newWidth);
        setHeight(newHeight);
    }

    public boolean sell() {
        return !sold;
    }



    public int getMegaLVL() {
        return megaLVL;
    }

    public void setMegaLVL(int megaLVL) {
        this.megaLVL = megaLVL;
    }

    public int getCost() {
        return cost;
    }

    public boolean isMega() {
        return mega;
    }

    public void setMega(boolean mega) {
        this.mega = mega;
    }

    public FloatingTowerWindow getInfoWindow() {
        return infoWindow;
    }
    public int getAttackSpeed() {
        return attackSpeed;
    }

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }

    public int getLevel() {
        return level;
    }

    public int getEXP() {
        return EXP;
    }

    public int getMaxEXP() {
        return maxEXP;
    }

    public Enemy getTarget() {
        return target;
    }

    public void setTarget(Enemy en) {
        target = en;
    }

    public void addLVL(int amt) {
        level += amt;
    }

    public void setEXP(int exp) {
        EXP = exp;
    }

    public void addEXP(int amount) {
        EXP += amount;
        while (EXP >= maxEXP) {
            EXP -= maxEXP;
            level++;
            onLevelUp();
        }
    }

    public void addDamage(int amt) {
        damage += amt;
    }

    public void addRange(int amt) {
        range += amt;
    }

    public void setAttackSpeed(int amt) {
        attackSpeed = amt;
    }

    private void onLevelUp() {
        damage += 2;
        range += 5;
        attackSpeed = Math.max(attackSpeed - 1, 5);
    }


    public void resetEXP() {
        EXP = 0;
    }

    public String getName() {
        return name;
    }
    public boolean canShoot() {
        return cooldown <= 0;
    }

    public void resetCooldown() {
        cooldown = attackSpeed;   // attackSpeed = ticks between shots
    }

    public void reduceCooldown() {
        if (cooldown > 0) {
            cooldown--;
        }
    }

    public abstract void tryMegaEvolve();
    public abstract boolean canMega();

    public abstract Point attackStart();

    @Override
    public void draw(Graphics g) {
//        g.drawImage(img, this.getPx(), this.getPy(),
//                this.getWidth(), this.getHeight(), null);
        g.drawImage(sprite.getImage(),
                getPx(), getPy(),
                getWidth(), getHeight(),
                null);
//        g.drawImage(sprite.getImage(), getPx(), getPy(), null);
        System.out.println("Draw: " + getWidth() + " x " + getHeight());

    }
}

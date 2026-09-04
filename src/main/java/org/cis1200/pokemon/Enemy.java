package org.cis1200.pokemon;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.List;

public abstract class Enemy extends GameObj {
    private int HP;
    private int maxHP;
    private int speed;
    private int damage;
    private int waypoint = 0;
    private int value = 0;
    private String name;
    private BufferedImage img;

    public Enemy(int initX, int initY,
                 int width, int height, int courtWidth, int courtHeight,
                 int maxHP, int speed, int damage, int value, String name) {
        super(0, 0, initX, initY, width, height, courtWidth, courtHeight);
        this.maxHP = maxHP;
        this.HP = maxHP;
        this.speed = speed;
        this.damage = damage;
        this.value = value;
        this.name = name;
    }

    public void loadImage(String fileName) {
        try {
            img = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }

    public int getMaxHP() {
        return maxHP;
    }
    public int getHealth() {
        return HP;
    }
    public void takeDamage(int amount) {
        HP -= amount;
        if (HP <= 0) {
            HP = 0;
        }
    }
    public boolean isDead() {
        return HP <= 0;
    }

    public int getDamage() {
        return damage;
    }

    String getName() {
        return name;
    }

    public void moveAlongPath(List<Point> path) {
        if (waypoint >= path.size()) {
            return;
        }
        Point target = path.get(waypoint);

        int dx = target.x - getPx();
        int dy = target.y - getPy();
        int dist = (int) Math.sqrt(dx * dx + dy * dy);

        if (dist < speed) {
            waypoint++;
            return;
        }

        setPx(getPx() + speed * dx / dist);
        setPy(getPy() + speed * dy / dist);
    }

    public abstract void move();

    @Override
    public void draw(Graphics g) {
        g.drawImage(img, getPx(), getPy(), getWidth(), getHeight(), null);

        Graphics2D g2 = (Graphics2D) g.create();
        Graphics2D g3 = (Graphics2D) g.create();

        int hbarWidth = getWidth();
        int hbarHeight = 6;

        int x = getPx();
        int y = getPy() - hbarHeight;

        g2.setColor(Color.RED);
        g2.fillRect(x, y, hbarWidth, hbarHeight);

        double hpPercent = (double) HP / maxHP;
        int hpWidth = (int) (hbarWidth * hpPercent);

        g3.setColor(Color.GREEN);
        g3.fillRect(x, y, hpWidth, hbarHeight);

        g2.setColor(Color.BLACK);
        g2.drawRect(x - 1, y - 1, hbarWidth + 1, hbarHeight + 1);

    }

}

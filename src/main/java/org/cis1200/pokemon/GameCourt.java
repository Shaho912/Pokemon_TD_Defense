package org.cis1200.pokemon;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * GameCourt
 *
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 */
public class GameCourt extends JPanel {

    // the state of the game logic
    private PathEnd end;
    public static final String BACK_FILE = "files/lvl1.png";
    private static BufferedImage background;

    private boolean playing = false;
    private final JLabel dollLabel;
    private final JLabel status;
    // Game constants
    public static final int COURT_WIDTH = 1280;
    public static final int COURT_HEIGHT = 600;
    public static int pokeDollars = 0;

    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 35;
    public static final LinkedList<Point> path = new LinkedList<Point>();

    private boolean towerMode = false;
    private String tower = null;
    private Tower previewTower = null;

    private int lastX;
    private int lastY;
    private static LinkedList<Tower> towers = new LinkedList<>();

    private LinkedList<Enemy> enemies = new LinkedList<>();

    private LinkedList<Attack> attacks = new LinkedList<>();

    public static final int interval2 = 1000;
    public static final int interval3 = 10000;
    private FloatingTowerWindow towerWindow;

    private LinkedList<Enemy> currentWave = null;
    private int waveIndex = 0;
    private int waveNumber = 1;

    private JProgressBar hpBar;
    public static int playerHP = 200;
    public static final int MAX_HP = 200;
    private boolean gameWon = false;

    private Timer timer;
    private Timer timer2;
    private Timer timer3;





    public GameCourt(JLabel dollLabel, JLabel status,
                     FloatingTowerWindow towerWindow, JProgressBar hpBar) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // The timer is an object which triggers an action periodically with the
        // given INTERVAL. We register an ActionListener with this timer, whose
        // actionPerformed() method is called each time the timer triggers. We
        // define a helper method called tick() that actually does everything
        // that should be done in a single time step.
        this.towerWindow = towerWindow;
        this.hpBar = hpBar;
        timer = new Timer(INTERVAL, e -> tick());

        timer2 = new Timer(interval2, e -> tick2());

        timer3 = new Timer(interval3, e -> tick3());

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);
        requestFocusInWindow();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lastX = e.getX();
                lastY = e.getY();
//                Point curr = new Point(e.getX(), e.getY());
                Rectangle curr = new Rectangle(lastX, lastY, 1, 1);
                if (!towerMode) {
                    for (Tower t : towers) {
                        if (t.intersectsCenter(curr))  {
                            towerWindow.displayTower(t);
                            return;
                        }
                    }
                }
                towerWindow.hideWindow();

                if (towerMode) {
                    place();
                }
                System.out.println("Clicked at: (" + lastX + ", " + lastY + ")");
            }

        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (towerMode && previewTower != null) {
                    lastX = e.getX();
                    lastY = e.getY();
                    previewTower.setPx(lastX - previewTower.getWidth() / 2);
                    previewTower.setPy(lastY - previewTower.getHeight() / 2);

                    repaint();
                }
            }
        });

        this.dollLabel = dollLabel;
        this.status = status;
        try {
            if (background == null) {
                background = ImageIO.read(new File(BACK_FILE));
            }
        } catch (IOException e) {
            System.out.println("IO error:" + e.getMessage());
        }
        path.add(new Point(0, 150));
        path.add(new Point(680, 150));
        path.add(new Point(680, 300));
        path.add(new Point(680, 400));
        path.add(new Point(680, 600));
    }

    public static LinkedList<Tower> getTowers() {
        return towers;
    }

    public static void sell(Tower t) {
        pokeDollars += t.getCost();
        towers.remove(t);
    }

    public JLabel getStatus() {
        return status;
    }

    public LinkedList<Enemy> getEnemies() {
        return enemies;
    }


    public void reset() {
        enemies.clear();
        attacks.clear();
        towers.clear();
        currentWave = loadWave1();
        waveIndex = 0;
        waveNumber = 2;
        end = new PathEnd(COURT_WIDTH, COURT_HEIGHT, Color.BLACK);
        previewTower = null;
        playing = true;
        pokeDollars = 100;
        dollLabel.setText(": " + pokeDollars);
        status.setText("Wave 1");

        playerHP = MAX_HP;
        if (hpBar != null) {
            hpBar.setValue(playerHP);
            hpBar.setForeground(Color.GREEN);
        }

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();

        showInstructions();
        timer.start();
        timer2.start();
        timer3.start();
    }

    public void showInstructions() {
        JTextArea text = new JTextArea();
        text.setEditable(false);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);

        text.setText("""
    GAME INSTRUCTIONS
      Help! Team rocket is trying to invade Pallet Town! You have met
      them on Route 22 and now it is your job to stop their evil plans!
      
      To play, click a tower icon, then click a valid location on the map.
      Green = valid placement and Red = invalid placement.

      Defeating enemies gives you PokeDollars. Use it to buy stronger 
      towers.

      Towers automatically target the nearest enemy inside their range.

      Towers gain EXP when killing enemies. Open a tower window by clicking 
      the tower. Some towers, like Charizard and Blastoise, can mega evolve.

      Enemies spawn every few seconds and there are 3 total waves.
      Survive all waves to win.

      When an enemy reaches the end, you lose HP. If your HP gets to
      0, then you lose!

      Open a tower window and press Sell to refund its cost.
      
      **MAKE SURE TO PLAY IN FULL SCREEN**

      Enjoy the game!
      
      This game was made by Shaho Solaman for UPenn's CIS 1200.
    """);

        JScrollPane scroll = new JScrollPane(text);
        scroll.setPreferredSize(new Dimension(400, 300));

        JOptionPane.showMessageDialog(
                this,
                scroll,
                "How to Play",
                JOptionPane.INFORMATION_MESSAGE
        );
    }


    public void setTowerMode(String pokemon) {
        this.towerMode = true;
        this.tower = pokemon;

        if (pokemon.equals("Charizard")) {
            previewTower = new Charizard(COURT_WIDTH, COURT_HEIGHT, lastX, lastY);
        } else if (pokemon.equals("Blastoise")) {
            previewTower = new Blastoise(COURT_WIDTH, COURT_HEIGHT, lastX, lastY);
        } else if (pokemon.equals("Pikachu")) {
            previewTower = new Pikachu(COURT_WIDTH, COURT_HEIGHT, lastX, lastY);
        }
        requestFocusInWindow();
    }
    public void place() {
        if (tower == null) {
            return;
        }

        int x = lastX - (previewTower.getWidth() / 2);
        int y = lastY - (previewTower.getHeight() / 2);

        Tower newTower = null;
        if (tower.equals("Charizard")) {
            newTower = new Charizard(COURT_WIDTH, COURT_HEIGHT, x, y);
        } else if (tower.equals("Blastoise")) {
            newTower = new Blastoise(COURT_WIDTH, COURT_HEIGHT, x, y);
        } else if (tower.equals("Pikachu")) {
            newTower = new Pikachu(COURT_WIDTH, COURT_HEIGHT, x, y);
        }
        if (newTower != null) {
            if (pokeDollars < newTower.getCost()) {
                showTemporaryStatus("Insufficient PokeDollars");
            } else if (!isValidPlacement(newTower)) {
                showTemporaryStatus("Invalid Placement");
            } else {
                towers.add(newTower);
                pokeDollars -= newTower.getCost();
                showTemporaryStatus("Placed " + newTower.getName());
            }
        }


        towerMode = false;
        tower = null;
        previewTower = null;
        repaint();
    }

    private void showTemporaryStatus(String message) {
        String oldText = status.getText();
        status.setText(message);

        Timer t = new Timer(1000, e -> status.setText(oldText));
        t.setRepeats(false);
        t.start();
    }



    public void damagePlayer(Enemy en) {
        playerHP -= en.getDamage();
        if (playerHP < 0) {
            playerHP = 0;
        }
        hpBar.setValue(playerHP);

        if (playerHP > 120) {
            hpBar.setForeground(Color.GREEN);
        } else if (playerHP > 60) {
            hpBar.setForeground(Color.ORANGE);
        } else {
            hpBar.setForeground(Color.RED);
        }

        if (playerHP == 0) {

            playing = false;
            status.setText("Game Over!");
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(
                        this,
                        "Game Over! Team Rocket has infiltrated Pallet Town!",
                        "You Lose!",
                        JOptionPane.INFORMATION_MESSAGE
                );
            });
//            timer.stop();
//            timer2.stop();
//            timer3.stop();
        }

    }



    public boolean isValidPlacement(Tower thisTower) {
        for (Tower t : towers) {
            if (thisTower.intersectsCenter(t.getCenterHitbox())) {
                return false;
            }
        }

        Rectangle topPath = new Rectangle(
                0, 161, 1280, 205 - 161);

        Rectangle midPath = new Rectangle(
                525, 200, 935 - 525, 507 - 203);

        if (thisTower.intersectsCenter(topPath)) {
            return false;
        }
        if (thisTower.intersectsCenter(midPath)) {
            return false;
        }

        return true;
    }

    public boolean inRadius(Tower t, Enemy en) {
        int ex = en.getPx() + en.getWidth() / 2;
        int ey = en.getPy() + en.getHeight() / 2;


        int tx = t.getPx() + t.getWidth() / 2;
        int ty = t.getPy() + t.getHeight() / 2;
        int range = t.getRange();

        double dx = ex - tx;
        double dy = ey - ty;
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist <= range) {
            return true;
        }
        return false;
    }


    public void towerAttack(Tower t, Enemy en) {
        if (t.canShoot()) {
            if (inRadius(t, en)) {
                Point start = t.attackStart();
                Attack shot = null;
                if (t.getName().equals("Charizard")) {
                    shot = new FireBlast(start.x, start.y, COURT_WIDTH,
                            COURT_HEIGHT, en, t, t.isMega());
                } else if (t.getName().equals("Blastoise")) {
                    shot = new HydroPump(start.x, start.y, COURT_WIDTH,
                            COURT_HEIGHT, en, t, t.isMega());
                } else if (t.getName().equals("Pikachu")) {
                    shot = new ElectroBall(start.x, start.y, COURT_WIDTH,
                            COURT_HEIGHT, en, t, t.isMega());
                }
                attacks.add(shot);
                t.resetCooldown();
            }
        }
    }



    public void takeDamage(Attack a, Enemy en) {
        en.takeDamage(a.getDamage());
        a.setAlive(false);
    }

    void tick() {
        if (playing) {

            for (Enemy en : enemies) {
                en.move();
            }

            for (Tower t : towers) {
                Enemy curr = t.getTarget();
                if (curr != null) {
                    if (!inRadius(t, curr) || curr.isDead()) {
                        t.setTarget(null);
                        curr = null;
                    }
                }
                if (curr == null) {
                    for (Enemy en : enemies) {
                        if (inRadius(t, en)) {
                            t.setTarget(en);
                            curr = en;
                            break;
                        }
                    }
                }
                if (curr != null) {
                    towerAttack(t, curr);
                }
            }

            for (Tower t : towers) {
                t.reduceCooldown();
            }

            for (Attack a : attacks) {
                a.move(enemies);
                if (a.isAlive()) {
                    for (int i = enemies.size() - 1; i >= 0; i--) {
                        Enemy en = enemies.get(i);
                        if (a.intersectsCenter(en)) {
                            takeDamage(a, en);
                            if (en.isDead()) {
                                Tower owner = a.getOwner();
                                owner.addEXP(en.getMaxHP());
                                if (towerWindow.isVisible() &&
                                        towerWindow.getCurrentTower() == owner) {
                                    towerWindow.refreshXP(owner);
                                }
                            }
                            break;
                        }
                    }
                }
            }
            attacks.removeIf(a -> !a.isAlive());
            Iterator<Enemy> iter = enemies.iterator();
            while (iter.hasNext()) {
                Enemy en = iter.next();
                if (en.intersects(end)) {
                    damagePlayer(en);
                    iter.remove();
                    continue;
                }
                if (en.isDead()) {
                    pokeDollars += en.getMaxHP();
                    iter.remove();
                }
            }
            if (!gameWon &&
                    waveNumber > 3 &&
                    (currentWave == null || currentWave.isEmpty()) &&
                    enemies.isEmpty()) {
                gameWon = true;
                playing = false;
                status.setText("YOU WIN!");
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(
                            this,
                            "Congratulations! You cleared all waves!",
                            "You Win!",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                });
            }



            repaint();
        }
    }


    public LinkedList<Enemy> loadWave1() {
        LinkedList<Enemy> wave1 = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            wave1.add(new Zubat(COURT_WIDTH, COURT_HEIGHT, path));
        }
        wave1.add(new Houndoom(COURT_WIDTH, COURT_HEIGHT, path));
        status.setText("Wave 1");
        return wave1;
    }

    public LinkedList<Enemy> loadWave2() {
        LinkedList<Enemy> wave2 = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            wave2.add(new Houndoom(COURT_WIDTH, COURT_HEIGHT, path));
        }
        status.setText("Wave 2");
        return wave2;
    }

    public LinkedList<Enemy> loadWave3() {
        LinkedList<Enemy> wave3 = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j ++) {
                wave3.add(new Houndoom(COURT_WIDTH, COURT_HEIGHT, path));
            }
            wave3.add(new MewtwoY(COURT_WIDTH, COURT_HEIGHT, path));
        }
        status.setText("Wave 3");
        return wave3;
    }

    void tick2() {
        if (currentWave != null && !currentWave.isEmpty()) {
            Enemy next = currentWave.removeFirst();
            enemies.add(next);
            waveIndex++;
        }
    }


    void tick3() {
        if (currentWave == null || currentWave.isEmpty()) {
            if (waveNumber == 1) {
                currentWave = loadWave1();
            } else if (waveNumber == 2) {
                currentWave = loadWave2();
            } else if (waveNumber == 3) {
                currentWave = loadWave3();
            } else {
                return;
            }

            waveIndex = 0;
            waveNumber++;
        }
    }




    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
        if (!enemies.isEmpty()) {
            for (Enemy en : enemies) {
                en.draw(g);
            }
        }
        if (!towers.isEmpty()) {
            for (Tower t : towers) {
                t.draw(g);
            }
        }
        if (!attacks.isEmpty()) {
            for (Attack a : attacks) {
                a.draw(g);
            }
        }
        if (towerMode && (previewTower != null)) {
            Graphics2D g2 = (Graphics2D) g.create();
            Graphics2D g3 = (Graphics2D) g.create();
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            previewTower.draw(g2);
            g2.setComposite(AlphaComposite.SrcOver);
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(2));
            int radius = previewTower.getRange();
            int centerX = previewTower.getPx() + previewTower.getWidth() / 2;
            int centerY = previewTower.getPy() + previewTower.getHeight() / 2;
            g3.setColor(Color.BLACK);
            g3.drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2);

            if (!isValidPlacement(previewTower)) {
                g2.setColor(Color.RED);
            } else {
                g2.setColor(Color.GREEN);
            }
            int startX = previewTower.getPx() + previewTower.getWidth() / 5;
            int startY = previewTower.getPy() + (previewTower.getHeight() / 3);
            int w = previewTower.getWidth() - (previewTower.getWidth() / 3);
            int h = previewTower.getHeight() - (previewTower.getHeight() / 3);
            g2.drawRect(startX, startY, w, h);
            g2.dispose();
        }

        end.draw(g);
        dollLabel.setText(": " + pokeDollars);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }


}
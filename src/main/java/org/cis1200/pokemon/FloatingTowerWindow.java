package org.cis1200.pokemon;

import javax.swing.*;
import java.awt.*;

public class FloatingTowerWindow extends JDialog {

    private JLabel nameLabel;
    private JLabel dmgLabel;
    private JLabel rangeLabel;
    private JLabel speedLabel;
    private JLabel costLabel;
    private JLabel lvlLabel;
    private JProgressBar xpBar;
    private Tower currentTower;
    private JLabel status;
    private JButton evo;
    private JButton sell;


    public FloatingTowerWindow(JFrame parent) {
        super(parent, false);
        setTitle("Pokemon Stats");
        setSize(230, 230);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setLocation(0, 400);

        nameLabel = new JLabel("No tower selected");
        dmgLabel = new JLabel("");
        rangeLabel = new JLabel("");
        speedLabel = new JLabel("");
        costLabel = new JLabel("");
        lvlLabel = new JLabel("");
        xpBar = new JProgressBar(0, 100);
        status = new JLabel("");
        evo = new JButton("");
        evo.setAlignmentX(Component.CENTER_ALIGNMENT);
        evo.addActionListener(e -> {
            if (currentTower != null) {
                currentTower.tryMegaEvolve();
                displayTower(currentTower);
            }
        });
        sell = new JButton("");
        sell.setAlignmentX(Component.CENTER_ALIGNMENT);
        sell.addActionListener(e -> {
            if (currentTower != null) {
                GameCourt.sell(currentTower);
                displayTower(currentTower);
                hideWindow();
            }
        });

        add(nameLabel);
        add(Box.createRigidArea(new Dimension(0, 6)));
        add(dmgLabel);
        add(rangeLabel);
        add(speedLabel);
        add(costLabel);
        add(lvlLabel);
        add(xpBar);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(status);
        add(evo);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(sell);

        // allow user to manually close
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    public Tower getCurrentTower() {
        return currentTower;
    }
    public void refreshXP(Tower t) {
        if (isVisible() && t != null) {
            xpBar.setMaximum(t.getMaxEXP());
            xpBar.setValue(t.getEXP());
            dmgLabel.setText("Damage: " + t.getDamage());
            rangeLabel.setText("Range: " + t.getRange());
            speedLabel.setText("Speed: " + t.getAttackSpeed());
            lvlLabel.setText("Level: " + t.getLevel());

            double pct = (double) t.getEXP() / t.getMaxEXP();
            if (pct < 0.33) {
                xpBar.setForeground(Color.RED);
            } else if (pct < 0.66) {
                xpBar.setForeground(Color.ORANGE);
            } else {
                xpBar.setForeground(Color.YELLOW);
            }

            xpBar.repaint();
            if (t.isMega()) {
                status.setText("MEGA");
            } else {
                status.setText("Normal");
            }
            evo.setEnabled(t.getLevel() >= t.getMegaLVL() && !t.isMega());

        }
    }


    public void displayTower(Tower t) {
        currentTower = t;

        nameLabel.setText("Pokemon: " + t.getName());
        dmgLabel.setText("Damage: " + t.getDamage());
        rangeLabel.setText("Range: " + t.getRange());
        speedLabel.setText("Speed: " + t.getAttackSpeed());
        costLabel.setText("Cost: " + t.getCost());
        lvlLabel.setText("Level: " + t.getLevel());


        xpBar.setMaximum(t.getMaxEXP());
        xpBar.setValue(t.getEXP());

        if (t.isMega()) {
            status.setText("MEGA");
        } else {
            status.setText("Normal");
        }
        evo.setEnabled(!t.isMega() && t.getLevel() >= t.getMegaLVL());
        evo.setText("MEGA EVOLVE");
        if (!t.canMega()) {
            evo.setText("Cannot Mega Evolve");
        }


        sell.setText("Sell for " + t.getCost());
        setVisible(true);
    }


    public void hideWindow() {
        setVisible(false);
    }
}

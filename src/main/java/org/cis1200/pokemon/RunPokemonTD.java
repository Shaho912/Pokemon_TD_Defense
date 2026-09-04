package org.cis1200.pokemon;

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class RunPokemonTD implements Runnable {
    public void run() {
        // NOTE : recall that the 'final' keyword notes immutability even for
        // local variables.

        // Top-level frame in which game components live.
        // Be sure to change "TOP LEVEL FRAME" to the name of your game
        final JFrame frame = new JFrame("Pokemon Tower Defense!");
        frame.setLocation(0, 0);

        final JPanel statusPanel = new JPanel();
        frame.add(statusPanel, BorderLayout.NORTH);
        final JPanel pokePanel = new JPanel();
        frame.add(pokePanel, BorderLayout.SOUTH);
        JLabel dollLabel = null;
        JLabel status = new JLabel("Wave: 1");
        try {
            BufferedImage img = ImageIO.read(new File("files/pokedollar.png"));
            Image scaled = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            dollLabel = new JLabel(new ImageIcon(scaled));
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }

        statusPanel.add(dollLabel);
        statusPanel.add(status);
        JProgressBar hpBar = new JProgressBar(0, GameCourt.MAX_HP);
        hpBar.setValue(GameCourt.playerHP);
        hpBar.setStringPainted(true);
        hpBar.setForeground(Color.GREEN);
        hpBar.setPreferredSize(new Dimension(200, 20));
        statusPanel.add(new JLabel("HP: "));
        statusPanel.add(hpBar);


        FloatingTowerWindow towerWindow = new FloatingTowerWindow(frame);


        // Main playing area
        final GameCourt court = new GameCourt(dollLabel, status, towerWindow, hpBar);
        frame.add(court, BorderLayout.CENTER);

        // Reset button

        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> court.reset());
        statusPanel.add(reset);

        Image charImg = loadImage("files/charizard.png");
        final JButton CharizardButton = new JButton("Charizard ($500)", new ImageIcon(charImg));
        CharizardButton.addActionListener(e -> {
            court.setTowerMode("Charizard");
        });
        pokePanel.add(CharizardButton);

        Image blastImg = loadImage("files/blastIcon.png");
        final JButton BlastButton = new JButton("Blastoise ($500)", new ImageIcon(blastImg));
        BlastButton.addActionListener(e -> {
            court.setTowerMode("Blastoise");
        });
        pokePanel.add(BlastButton);

        Image pikaImg = loadImage("files/pikaIcon.png");
        final JButton PikaButton = new JButton("Pikachu ($100)", new ImageIcon(pikaImg));
        PikaButton.addActionListener(e -> {
            court.setTowerMode("Pikachu");
        });
        pokePanel.add(PikaButton);




        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        court.reset();
    }

    private Image loadImage(String s) {
        try {
            BufferedImage img = ImageIO.read(new File(s));
            return img.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
        return null;
    }
}
package org.cis1200.pokemon;

import org.junit.jupiter.api.*;
import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import static org.junit.jupiter.api.Assertions.*;

public class GameCourtTest {

    private GameCourt createCourt() {
        JLabel dollars = new JLabel();
        JLabel status = new JLabel();
        JProgressBar hp = new JProgressBar(0, GameCourt.MAX_HP);
        FloatingTowerWindow fw = new FloatingTowerWindow(new JFrame());
        return new GameCourt(dollars, status, fw, hp);
    }

    static class TestEnemy extends Enemy {
        public TestEnemy(int dmg) {
            super(0, 0, 20, 20,
                    1280, 600, 50,
                    0, dmg, 0, "TestEnemy");
        }
        @Override
        public void move() {}
    }

    static class TestTower extends Tower {
        public TestTower(int x, int y) {
            super(x, y, 40, 40, 1280, 600,
                    50, 10, 10, 200, 0,
                    50, "TestTower", false, 2);
        }
        @Override
        public void tryMegaEvolve() {}
        @Override
        public boolean canMega() {
            return false;
        }
        @Override
        public Point attackStart() {
            return new Point(getPx(), getPy());
        }
    }


    @Test
    public void resetRestoresDefaults() {
        GameCourt gc = createCourt();
        gc.reset();

        assertEquals(GameCourt.MAX_HP, GameCourt.playerHP);
        assertEquals(100, GameCourt.pokeDollars);
        assertTrue(gc.isFocusable());
    }

    @Test
    public void damagePlayerReducesHP() {
        GameCourt gc = createCourt();
        gc.reset();
        TestEnemy e = new TestEnemy(30);

        gc.damagePlayer(e);
        assertEquals(170, GameCourt.playerHP);

        gc.damagePlayer(e);
        assertEquals(140, GameCourt.playerHP);
    }

    @Test
    public void damagePlayerTriggersGameOver() {
        GameCourt gc = createCourt();
        gc.reset();
        TestEnemy e = new TestEnemy(300);

        gc.damagePlayer(e);
        assertEquals(0, GameCourt.playerHP);
        assertEquals("Game Over!", gc.getStatus().getText());
    }

    @Test
    public void wave1LoadsCorrectly() {
        GameCourt gc = createCourt();
        LinkedList<Enemy> wave = gc.loadWave1();
        assertEquals(6, wave.size());
        assertTrue(wave.get(0).getName().equals("Zubat"));
    }

    @Test
    public void waveSpawningWorks() {
        GameCourt gc = createCourt();
        gc.reset();

        gc.tick3();
        assertNotNull(gc.loadWave1());
        gc.tick2();
    }

    @Test
    public void inRadiusWorks() {
        GameCourt gc = createCourt();
        TestTower t = new TestTower(100, 100);
        TestEnemy e = new TestEnemy(10);
        e.setPx(150);
        e.setPy(150);

        assertTrue(gc.inRadius(t, e));

        e.setPx(1000);
        e.setPy(1000);
        assertFalse(gc.inRadius(t, e));
    }

    @Test
    public void validPlacementRejectsOverlap() {
        GameCourt gc = createCourt();
        gc.reset();

        TestTower t1 = new TestTower(100, 100);
        TestTower t2 = new TestTower(110, 110);

        GameCourt.getTowers().add(t1);
        assertFalse(gc.isValidPlacement(t2));
    }

    @Test
    public void enemyReachingEndDamagesPlayer() {
        GameCourt gc = createCourt();
        gc.reset();
        TestEnemy e = new TestEnemy(20);
        gc.damagePlayer(e);
        assertEquals(180, GameCourt.playerHP);
    }


}

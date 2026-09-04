package org.cis1200.pokemon;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.*;

public class PokemonTDTowerTest {
    private static class TestTower extends Tower {
        public TestTower() {
            super(
                    50, 60, 40, 40, 500, 500, 100,
                    10, 5, 100, 1, 50, "TestTower",
                    false, 3
            );
        }

        @Override
        public void tryMegaEvolve() {}

        @Override
        public boolean canMega() {
            return getLevel() >= getMegaLVL();
        }

        @Override
        public Point attackStart() {
            return new Point(getPx(), getPy());
        }
    }

    private static class TestEnemy extends Enemy {
        public TestEnemy() {
            super(
                    100, 100, 40, 40, 500, 500,
                    100, 1, 10, 5, "TestEnemy"
            );
        }

        @Override
        public void move() {}
    }


    @Test
    public void towerInitialValuesCorrect() {
        TestTower t = new TestTower();
        assertEquals(50, t.getPx());
        assertEquals(60, t.getPy());
        assertEquals(40, t.getWidth());
        assertEquals(40, t.getHeight());
        assertEquals(100, t.getRange());
        assertEquals(5, t.getDamage());
        assertEquals(0, t.getLevel());
        assertEquals(50, t.getMaxEXP());
    }

    @Test
    public void expGainWithoutLevelUp() {
        TestTower t = new TestTower();
        t.addEXP(20);
        assertEquals(20, t.getEXP());
        assertEquals(0, t.getLevel());
    }

    @Test
    public void expGainTriggersLevelUp() {
        TestTower t = new TestTower();
        t.addEXP(60);

        assertEquals(10, t.getEXP());
        assertEquals(1, t.getLevel());
    }

    @Test
    public void levelUpImprovesStats() {
        TestTower t = new TestTower();
        int oldDamage = t.getDamage();
        int oldRange = t.getRange();
        int oldAS = t.getAttackSpeed();

        t.addEXP(200);

        assertTrue(t.getDamage() > oldDamage);
        assertTrue(t.getRange() > oldRange);
        assertTrue(t.getAttackSpeed() <= oldAS);
    }

    @Test
    public void cooldownStartsReadyThenCountsDown() {
        TestTower t = new TestTower();

        assertTrue(t.canShoot());

        t.resetCooldown();
        assertFalse(t.canShoot());

        for (int i = 0; i < 10; i++) {
            t.reduceCooldown();
        }

        assertTrue(t.canShoot());
    }

    @Test
    public void targetAssignmentWorks() {
        TestTower tower = new TestTower();
        TestEnemy enemy = new TestEnemy();

        assertNull(tower.getTarget());
        tower.setTarget(enemy);
        assertEquals(enemy, tower.getTarget());
    }

    @Test
    public void megaEligibilityCorrect() {
        TestTower t = new TestTower();

        assertFalse(t.canMega());
        t.addLVL(3);
        assertTrue(t.canMega());
    }

    @Test
    public void sellReturnsCorrectBoolean() {
        TestTower t = new TestTower();
        assertTrue(t.sell());
    }
}

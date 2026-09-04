package org.cis1200.pokemon;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.*;
import java.util.LinkedList;

public class PokemonTDAttackTest {

    private static class TestEnemy extends Enemy {
        public TestEnemy(int x, int y) {
            super(x, y, 40, 40, 500, 500, 50, 1, 0, 0, "TestEnemy");
        }
        @Override public void move() {}
        @Override public void draw(Graphics g) {}
    }

    private static class TestTower extends Tower {
        public TestTower() {
            super(100, 100, 50, 50, 500, 500,
                    100, 5, 10, 150, 1, 100, "TestTower", false, 2);
        }
        @Override public void tryMegaEvolve() {}

        @Override
        public boolean canMega() {
            return false;
        }

        @Override public void draw(Graphics g) {}
        @Override public Point attackStart() { return new Point(100, 100); }
    }

    private static class TestAttack extends Attack {
        public TestAttack(int x, int y, Enemy e, Tower owner) {
            super(x, y, 20, 20, 500, 500,
                    10, 5, e, owner, false);
            loadSprite("files/fireblast.gif");
        }
    }

    @Test
    public void testDamageCalculation() {
        TestEnemy en = new TestEnemy(200, 200);
        TestTower tower = new TestTower();
        TestAttack atk = new TestAttack(100, 100, en, tower);

        assertEquals(5 + tower.getDamage(), atk.getDamage());
    }

    @Test
    public void testOwnerReference() {
        TestEnemy en = new TestEnemy(200, 200);
        TestTower tower = new TestTower();
        TestAttack atk = new TestAttack(100, 100, en, tower);

        assertEquals(tower, atk.getOwner());
    }

    @Test
    public void testMoveTowardTarget() {
        TestEnemy en = new TestEnemy(200, 200);
        TestTower tower = new TestTower();
        TestAttack atk = new TestAttack(100, 100, en, tower);

        int oldX = atk.getPx();
        int oldY = atk.getPy();

        atk.move(new LinkedList<>());

        assertTrue(atk.getPx() > oldX);
        assertTrue(atk.getPy() > oldY);
    }

    @Test
    public void testProjectileDiesIfTargetDies() {
        TestEnemy en = new TestEnemy(200, 200);
        TestTower tower = new TestTower();
        TestAttack atk = new TestAttack(100, 100, en, tower);

        en.takeDamage(9999);

        atk.move(new LinkedList<>());

        assertFalse(atk.isAlive());
    }

    @Test
    public void testProjectileReachesTargetAndDies() {
        TestEnemy en = new TestEnemy(105, 105);
        TestTower tower = new TestTower();
        TestAttack atk = new TestAttack(100, 100, en, tower);

        atk.move(new LinkedList<>());

        assertFalse(atk.isAlive());
    }

    @Test
    public void testSpriteLoads() {
        TestEnemy en = new TestEnemy(200, 200);
        TestTower tower = new TestTower();
        TestAttack atk = new TestAttack(100, 100, en, tower);

        assertNotNull(atk);
    }
}

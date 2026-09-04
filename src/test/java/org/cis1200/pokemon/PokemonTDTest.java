package org.cis1200.pokemon;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.*;

public class PokemonTDTest {

    private static class TestObj extends GameObj {
        public TestObj(int px, int py, int w, int h) {
            super(0, 0, px, py, w, h, 500, 500);
        }
        @Override
        public void draw(Graphics g) { }
    }

    @Test
    public void testPositionSettersAndClipping() {
        TestObj obj = new TestObj(0, 0, 50, 50);

        obj.setPx(200);
        obj.setPy(150);
        assertEquals(200, obj.getPx());
        assertEquals(150, obj.getPy());

        obj.setPx(600);
        obj.setPy(-20);
        assertEquals(450, obj.getPx());
        assertEquals(0, obj.getPy());
    }

    @Test
    public void testMoveWithVelocity() {
        TestObj obj = new TestObj(0, 0, 50, 50);

        obj.setVx(10);
        obj.setVy(5);
        obj.move();

        assertEquals(10, obj.getPx());
        assertEquals(5, obj.getPy());
    }

    @Test
    public void testIntersectsBoundingBox() {
        TestObj a = new TestObj(0, 0, 50, 50);
        TestObj b = new TestObj(25, 25, 50, 50);
        assertTrue(a.intersects(b));

        TestObj c = new TestObj(100, 100, 50, 50);
        assertFalse(a.intersects(c));
    }

    @Test
    public void testIntersectsCenter() {
        TestObj a = new TestObj(0, 0, 100, 100);
        TestObj b = new TestObj(40, 40, 20, 20);
        assertTrue(a.intersectsCenter(b));

        TestObj c = new TestObj(300, 300, 50, 50);
        assertFalse(a.intersectsCenter(c));
    }

    @Test
    public void testIntersectsCenterWithRectangle() {
        TestObj obj = new TestObj(100, 100, 100, 100);

        Rectangle hb = obj.getCenterHitbox();
        Rectangle rectTouch = new Rectangle(hb.x, hb.y, 5, 5);
        assertTrue(obj.intersectsCenter(rectTouch));

        Rectangle rectFar = new Rectangle(0, 0, 20, 20);
        assertFalse(obj.intersectsCenter(rectFar));
    }

    @Test
    public void testGetCenterHitbox() {
        TestObj obj = new TestObj(100, 100, 120, 120);

        Rectangle hb = obj.getCenterHitbox();

        assertEquals(100 + 120 / 5, hb.x);
        assertEquals(100 + 120 / 3, hb.y);
        assertEquals(120 - (120 / 3), hb.width);
        assertEquals(120 - (120 / 3), hb.height);
    }

    @Test
    public void testWillIntersect() {
        TestObj a = new TestObj(0, 0, 50, 50);
        TestObj b = new TestObj(60, 0, 50, 50);

        a.setVx(15);
        assertTrue(a.willIntersect(b));
    }
}

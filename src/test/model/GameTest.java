package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game g;
    private Board b;

    @BeforeEach
    public void runBefore() {
        b = new Board();
        g = new Game(b);
    }

    @Test
    public void testInitial() {
        assertFalse(g.isEnded());
        assertFalse(g.isStarted());
        assertEquals(0, g.getX());
        assertEquals(0, g.getY());
        assertEquals(0, g.getStartTime());
        assertEquals("000", g.getTimeString());
        assertEquals(b, g.getBoard());
    }

    @Test
    public void testTimer() throws InterruptedException {
        g.start();
        long testTimer = System.currentTimeMillis();
        Thread.sleep(1000L);
        Thread.sleep(1000L);
        Thread.sleep(1000L);
        g.tick();
        int expected = (int) (( System.currentTimeMillis() - testTimer) / 1000);
        assertEquals(expected, g.getTime());
    }

    @Test
    public void testGetTimeStringSingleDigits() {
        g.start();
        g.tick();
        assertEquals("000", g.getTimeString());
    }

    @Test
    public void testGetTimeStringDoubleDigits() {
        g.start();
        g.setTime(36);
        assertEquals("036", g.getTimeString());
    }

    @Test
    public void testGetTimeStringTripleDigits() {
        g.start();
        g.setTime(487);
        assertEquals("487", g.getTimeString());
    }

    @Test
    public void testGetTimeStringPast999() {
        g.start();
        g.setTime(1035);
        assertEquals("999", g.getTimeString());
    }

    @Test
    public void testEndGame() {
        g.end();
        assertTrue(g.isEnded());
    }

    @Test
    public void testMoveWrong() {
        g.moveUp();
        g.moveLeft();
        assertEquals(0, g.getX());
        assertEquals(0, g.getY());
        g.setPosition(b.getHeight() - 1, b.getWidth() - 1);
        g.moveDown();
        g.moveRight();
        assertEquals(b.getWidth() - 1, g.getX());
        assertEquals(b.getHeight() - 1, g.getY()); //
    }

    @Test
    public void testMoveRight() {
        g.moveDown();
        g.moveRight();
        g.moveDown();
        g.moveRight();
        g.moveLeft();
        g.moveUp();
        assertEquals(1, g.getX());
        assertEquals(1, g.getY());
    }

}


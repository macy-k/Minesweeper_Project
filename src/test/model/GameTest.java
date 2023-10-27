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
    public void testWinTickFailNotCleared() {
        b.setHeight(3);
        b.setWidth(3);
        b.setRandomSeed(26);
        b.generateLayout();
        b.changeUnflaggedBombs(b.getCell(0, 0).toggleFlag());
        b.getCell(0, 2).clear();
        b.getCell(1, 0).clear();
        b.getCell(1, 1).clear();
        b.getCell(1, 2).clear();
        b.getCell(2, 0).clear();
        b.getCell(2, 1).clear();
        b.getCell(2, 2).clear();
        g = new Game(b);
        g.tick();
        assertFalse(g.isEnded());
    }

    @Test
    public void testWinTickFailUnflagged() {
        b.setHeight(3);
        b.setWidth(3);
        b.setRandomSeed(26);
        b.generateLayout();
        b.getCell(0, 1).clear();
        b.getCell(0, 2).clear();
        b.getCell(1, 0).clear();
        b.getCell(1, 1).clear();
        b.getCell(1, 2).clear();
        b.getCell(2, 0).clear();
        b.getCell(2, 1).clear();
        b.getCell(2, 2).clear();
        g = new Game(b);
        g.tick();
        assertFalse(g.isEnded());
    }

    @Test
    public void testWinTickFailCorrectlyFlagged() {
        b.setHeight(3);
        b.setWidth(3);
        b.setRandomSeed(26);
        b.generateLayout();
        b.changeUnflaggedBombs(b.getCell(0, 1).toggleFlag());
        b.getCell(0, 2).clear();
        b.getCell(1, 0).clear();
        b.getCell(1, 1).clear();
        b.getCell(1, 2).clear();
        b.getCell(2, 0).clear();
        b.getCell(2, 1).clear();
        b.getCell(2, 2).clear();
        g = new Game(b);
        g.tick();
        assertFalse(g.isEnded());
    }

    @Test
    public void testWinTickSuccess() {
        b.setHeight(3);
        b.setWidth(3);
        b.setRandomSeed(26);
        b.generateLayout();
        b.changeUnflaggedBombs(b.getCell(0, 0).toggleFlag());
        b.getCell(0, 1).clear();
        b.getCell(0, 2).clear();
        b.getCell(1, 0).clear();
        b.getCell(1, 1).clear();
        b.getCell(1, 2).clear();
        b.getCell(2, 0).clear();
        b.getCell(2, 1).clear();
        b.getCell(2, 2).clear();
        g = new Game(b);
        g.tick();
        assertTrue(g.isEnded());
    }

    @Test
    public void testEndGame() {
        g.end();
        assertTrue(g.isEnded());
        assertFalse(g.isIncomplete());
        assertFalse(g.isWon());
    }

    @Test
    public void testIncompleteGame() {
        g.incomplete();
        assertTrue(g.isEnded());
        assertTrue(g.isIncomplete());
        assertFalse(g.isWon());
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

    @Test
    public void testToJson() {
        b.setHeight(3);
        b.setWidth(3);
        b.setRandomSeed(25);
        b.generateLayout();
        g = new Game(b);
        g.start();
        g.setTime(49);
        assertEquals("{\n" +
                "    \"time\": 49,\n" +
                "    \"board\": {\n" +
                "        \"layout\": [\n" +
                "            [\n" +
                "                {\n" +
                "                    \"inRadius\": 0,\n" +
                "                    \"isBomb\": false,\n" +
                "                    \"isClear\": false,\n" +
                "                    \"isFlagged\": false\n" +
                "                },\n" +
                "                {\n" +
                "                    \"inRadius\": 0,\n" +
                "                    \"isBomb\": false,\n" +
                "                    \"isClear\": false,\n" +
                "                    \"isFlagged\": false\n" +
                "                },\n" +
                "                {\n" +
                "                    \"inRadius\": 0,\n" +
                "                    \"isBomb\": false,\n" +
                "                    \"isClear\": false,\n" +
                "                    \"isFlagged\": false\n" +
                "                }\n" +
                "            ],\n" +
                "            [\n" +
                "                {\n" +
                "                    \"inRadius\": 1,\n" +
                "                    \"isBomb\": false,\n" +
                "                    \"isClear\": false,\n" +
                "                    \"isFlagged\": false\n" +
                "                },\n" +
                "                {\n" +
                "                    \"inRadius\": 1,\n" +
                "                    \"isBomb\": false,\n" +
                "                    \"isClear\": false,\n" +
                "                    \"isFlagged\": false\n" +
                "                },\n" +
                "                {\n" +
                "                    \"inRadius\": 0,\n" +
                "                    \"isBomb\": false,\n" +
                "                    \"isClear\": false,\n" +
                "                    \"isFlagged\": false\n" +
                "                }\n" +
                "            ],\n" +
                "            [\n" +
                "                {\n" +
                "                    \"inRadius\": 0,\n" +
                "                    \"isBomb\": true,\n" +
                "                    \"isClear\": false,\n" +
                "                    \"isFlagged\": false\n" +
                "                },\n" +
                "                {\n" +
                "                    \"inRadius\": 1,\n" +
                "                    \"isBomb\": false,\n" +
                "                    \"isClear\": false,\n" +
                "                    \"isFlagged\": false\n" +
                "                },\n" +
                "                {\n" +
                "                    \"inRadius\": 0,\n" +
                "                    \"isBomb\": false,\n" +
                "                    \"isClear\": false,\n" +
                "                    \"isFlagged\": false\n" +
                "                }\n" +
                "            ]\n" +
                "        ],\n" +
                "        \"unflaggedBombs\": 1,\n" +
                "        \"width\": 3,\n" +
                "        \"bombs\": 1,\n" +
                "        \"height\": 3\n" +
                "    }\n" +
                "}", g.toJson().toString(4));

    }

}


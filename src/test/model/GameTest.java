package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game g;
    private Board b;
    private static final List<List<Boolean>> bombsListV1 = Arrays.asList(
            Arrays.asList(false, false, false, false, false),
            Arrays.asList(true, false, false, false, false),
            Arrays.asList(true, true, false, true, true),
            Arrays.asList(false, false, false, false, false),
            Arrays.asList(false, false, false, false, false));
    private static final List<List<Boolean>> clearListV1 = Arrays.asList(
            Arrays.asList(false, true, true, true, true),
            Arrays.asList(false, true, true, true, true),
            Arrays.asList(false, false, false, false, false),
            Arrays.asList(false, false, false, false, false),
            Arrays.asList(false, false, false, false, false));
    private static final List<List<Integer>> inRadiusListV1 = Arrays.asList(
            Arrays.asList(1, 1, 0, 0, 0),
            Arrays.asList(2, 3, 2, 2, 2),
            Arrays.asList(2, 2, 2, 1, 1),
            Arrays.asList(2, 2, 2, 2, 2),
            Arrays.asList(0, 0, 0, 0, 0));
    private static final List<List<Boolean>> bombsListV2 = Arrays.asList(
            Arrays.asList(false, false, false, false, false),
            Arrays.asList(true, false, false, false, false),
            Arrays.asList(true, false, false, false, true),
            Arrays.asList(true, false, false, false, false),
            Arrays.asList(false, false, false, true, false));
    private static final List<List<Boolean>> clearListV2 = Arrays.asList(
            Arrays.asList(false, true, true, true, true),
            Arrays.asList(false, true, true, true, true),
            Arrays.asList(false, true, true, true, false),
            Arrays.asList(false, true, true, true, false),
            Arrays.asList(false, false, false, false, false));
    private static final List<List<Integer>> inRadiusListV2 = Arrays.asList(
            Arrays.asList(1, 1, 0, 0, 0),
            Arrays.asList(1, 2, 0, 1, 1),
            Arrays.asList(2, 3, 0, 1, 0),
            Arrays.asList(1, 2, 1, 2, 2),
            Arrays.asList(1, 1, 1, 0, 1));
    private static final List<List<Boolean>> clearListV3 = Arrays.asList(
            Arrays.asList(false, false, false, false, false),
            Arrays.asList(false, false, true, false, false),
            Arrays.asList(false, false, false, false, false),
            Arrays.asList(false, false, false, false, false),
            Arrays.asList(false, false, false, false, false));
    private static final List<List<Boolean>> bombsListV4 = Arrays.asList(
            Arrays.asList(false, false, false, false, false),
            Arrays.asList(false, false, false, false, false),
            Arrays.asList(false, false, false, false, false),
            Arrays.asList(true, false, false, false, false),
            Arrays.asList(false, false, false, false, false));
    private static final List<List<Boolean>> clearListV4 = Arrays.asList(
            Arrays.asList(true, true, true, true, true),
            Arrays.asList(true, true, true, true, true),
            Arrays.asList(true, true, true, true, true),
            Arrays.asList(false, true, true, true, true),
            Arrays.asList(false, true, true, true, true));
    private static final List<List<Integer>> inRadiusListV4 = Arrays.asList(
            Arrays.asList(0, 0, 0, 0, 0),
            Arrays.asList(0, 0, 0, 0, 0),
            Arrays.asList(1, 1, 0, 0, 0),
            Arrays.asList(0, 1, 0, 0, 0),
            Arrays.asList(1, 1, 0, 0, 0));
    private static final List<List<Boolean>> clearListV5 = Arrays.asList(
            Arrays.asList(true, true, true, true, true),
            Arrays.asList(true, true, true, false, true),
            Arrays.asList(true, true, true, true, true),
            Arrays.asList(false, true, true, true, true),
            Arrays.asList(false, true, true, true, true));

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
    public void testGetScoreString() {
        b.generateLayout(38);
        g = new Game(b);
        assertEquals("038", g.getScoreString());
    }

    @Test
    public void testGetScoreStringPast999() {
        b.setHeight(32);
        b.setWidth(32);
        b.generateLayout(1020);
        g = new Game(b);
        assertEquals("999", g.getScoreString());
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
    public void testAttemptClearNotStartedNoReplace() {
        b.setHeight(5);
        b.setWidth(5);
        b.setRandomSeed(35);
        b.generateLayout();
        g = new Game(b);
        g.attemptClear(0, 2);
        assertEquals(bombsListV1, b.getBombsList());
        assertEquals(clearListV1, b.getClearList());
        assertEquals(inRadiusListV1, b.getInRadiusList());
    }

    @Test
    public void testAttemptClearNotStartedWithReplace() {
        b.setHeight(5);
        b.setWidth(5);
        b.setRandomSeed(35);
        b.generateLayout();
        g = new Game(b);
        g.attemptClear(1, 2);
        assertEquals(bombsListV2, b.getBombsList());
        assertEquals(clearListV2, b.getClearList());
        assertEquals(inRadiusListV2, b.getInRadiusList());
    }

    @Test
    public void testAttemptClearStartedValidNoFlood() {
        b.setHeight(5);
        b.setWidth(5);
        b.setRandomSeed(35);
        b.generateLayout();
        g = new Game(b);
        assertFalse(g.isStarted());
        assertFalse(g.isEnded());
        g.start();
        assertTrue(g.isStarted());
        assertFalse(g.isEnded());
        g.attemptClear(1, 2);
        assertEquals(bombsListV1, b.getBombsList());
        assertEquals(clearListV3, b.getClearList());
        assertEquals(inRadiusListV1, b.getInRadiusList());
        assertTrue(g.isStarted());
        assertFalse(g.isEnded());
    }

    @Test
    public void testAttemptClearStartedValidFlood() {
        b.setHeight(5);
        b.setWidth(5);
        b.setRandomSeed(10);
        b.generateLayout(1);
        g = new Game(b);
        assertFalse(g.isStarted());
        assertFalse(g.isEnded());
        g.start();
        assertTrue(g.isStarted());
        assertFalse(g.isEnded());
        g.attemptClear(3, 3);
        assertEquals(bombsListV4, b.getBombsList());
        assertEquals(clearListV4, b.getClearList());
        assertEquals(inRadiusListV4, b.getInRadiusList());
        assertTrue(g.isStarted());
        assertFalse(g.isEnded());
    }

    @Test
    public void testAttemptClearStartedValidFloodWithFlag() {
        b.setHeight(5);
        b.setWidth(5);
        b.setRandomSeed(10);
        b.generateLayout(1);
        b.getCell(1, 3).toggleFlag();
        g = new Game(b);
        assertFalse(g.isStarted());
        assertFalse(g.isEnded());
        g.start();
        assertTrue(g.isStarted());
        assertFalse(g.isEnded());
        g.attemptClear(3, 3);
//        System.out.println(b.getBombsList());
//        System.out.println(b.getInRadiusList());
//        System.out.println(b.getClearList());
        assertEquals(bombsListV4, b.getBombsList());
        assertEquals(clearListV5, b.getClearList());
        assertEquals(inRadiusListV4, b.getInRadiusList());
        assertTrue(g.isStarted());
        assertFalse(g.isEnded());
    }

    @Test
    public void testAttemptClearOnBomb() {
        b.setHeight(5);
        b.setWidth(5);
        b.setRandomSeed(35);
        b.generateLayout();
        g = new Game(b);
        assertFalse(g.isStarted());
        assertFalse(g.isEnded());
        g.start();
        assertTrue(g.isStarted());
        assertFalse(g.isEnded());
        g.attemptClear(2, 1);
        assertTrue(g.isStarted());
        assertTrue(g.isEnded());
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


package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardTest {
    private static final List<List<Boolean>> bombsListV1 = Arrays.asList(
            Arrays.asList(false, false, false),
            Arrays.asList(false, false, false),
            Arrays.asList(true, false, false));
    private static final List<List<Integer>> inRadiusListV1 = Arrays.asList(
            Arrays.asList(0, 0, 0),
            Arrays.asList(1, 1, 0),
            Arrays.asList(0, 1, 0));
    private static final List<List<Boolean>> bombsListV2 = Arrays.asList(
            Arrays.asList(false, false, true, false, false),
            Arrays.asList(false, false, false, false, false),
            Arrays.asList(false, false, false, false, false),
            Arrays.asList(false, false, false, false, false),
            Arrays.asList(false, false, false, false, false));
    private static final List<List<Integer>> inRadiusListV2 = Arrays.asList(
            Arrays.asList(0, 1, 0, 1, 0),
            Arrays.asList(0, 1, 1, 1, 0),
            Arrays.asList(0, 0, 0, 0, 0),
            Arrays.asList(0, 0, 0, 0, 0),
            Arrays.asList(0, 0, 0, 0, 0));
    private static final List<List<Boolean>> bombsListV3 = Arrays.asList(
            Arrays.asList(false, false, false, false, false),
            Arrays.asList(false, false, false, false, false),
            Arrays.asList(false, false, false, false, false),
            Arrays.asList(true, false, false, false, false),
            Arrays.asList(false, false, false, false, false));
    private static final List<List<Integer>> inRadiusListV3 = Arrays.asList(
            Arrays.asList(0, 0, 0, 0, 0),
            Arrays.asList(0, 0, 0, 0, 0),
            Arrays.asList(1, 1, 0, 0, 0),
            Arrays.asList(0, 1, 0, 0, 0),
            Arrays.asList(1, 1, 0, 0, 0));
    private static final List<List<Boolean>> bombsListV4 = Arrays.asList(
            Arrays.asList(false, false, false, false, false),
            Arrays.asList(false, false, false, false, false),
            Arrays.asList(false, false, true, false, false),
            Arrays.asList(false, false, false, false, false),
            Arrays.asList(false, false, false, false, false));
    private static final List<List<Integer>> inRadiusListV4 = Arrays.asList(
            Arrays.asList(0, 0, 0, 0, 0),
            Arrays.asList(0, 1, 1, 1, 0),
            Arrays.asList(0, 1, 0, 1, 0),
            Arrays.asList(0, 1, 1, 1, 0),
            Arrays.asList(0, 0, 0, 0, 0));
    private static final List<List<Boolean>> bombsListV5 = Arrays.asList( //start of multiple bombs
            Arrays.asList(false, false, false, false, false),
            Arrays.asList(true, false, false, false, false),
            Arrays.asList(true, true, false, true, true),
            Arrays.asList(false, false, false, false, false),
            Arrays.asList(false, false, false, false, false));
    private static final List<List<Integer>> inRadiusListV5 = Arrays.asList(
            Arrays.asList(1, 1, 0, 0, 0),
            Arrays.asList(2, 3, 2, 2, 2),
            Arrays.asList(2, 2, 2, 1, 1),
            Arrays.asList(2, 2, 2, 2, 2),
            Arrays.asList(0, 0, 0, 0, 0));
    private static final List<List<Boolean>> bombsListV6 = Arrays.asList(
            Arrays.asList(false, false, false, false, false),
            Arrays.asList(true, false, false, false, false),
            Arrays.asList(true, true, false, true, true),
            Arrays.asList(true, false, false, false, false),
            Arrays.asList(false, false, false, false, false));
    private static final List<List<Integer>> inRadiusListV6 = Arrays.asList(
            Arrays.asList(1, 1, 0, 0, 0),
            Arrays.asList(2, 3, 2, 2, 2),
            Arrays.asList(3, 3, 2, 1, 1),
            Arrays.asList(2, 3, 2, 2, 2),
            Arrays.asList(1, 1, 0, 0, 0));
    private static final List<List<Boolean>> bombsListV7 = Arrays.asList(
            Arrays.asList(false, false, false, false, false),
            Arrays.asList(true, false, false, false, false),
            Arrays.asList(false, true, false, true, true),
            Arrays.asList(true, false, false, false, false),
            Arrays.asList(false, false, false, false, false));
    private static final List<List<Integer>> inRadiusListV7 = Arrays.asList(
            Arrays.asList(1, 1, 0, 0, 0),
            Arrays.asList(1, 2, 2, 2, 2),
            Arrays.asList(3, 2, 2, 1, 1),
            Arrays.asList(1, 2, 2, 2, 2),
            Arrays.asList(1, 1, 0, 0, 0));
    private static final List<List<Boolean>> bombsListV8 = Arrays.asList(
            Arrays.asList(false, false, false, false, false),
            Arrays.asList(false, false, false, true, true),
            Arrays.asList(false, false, false, true, true),
            Arrays.asList(false, false, false, false, false),
            Arrays.asList(false, false, false, true, false));
    private static final List<List<Integer>> inRadiusListV8 = Arrays.asList(
            Arrays.asList(0, 0, 1, 2, 2),
            Arrays.asList(0, 0, 2, 3, 3),
            Arrays.asList(0, 0, 2, 3, 3),
            Arrays.asList(0, 0, 2, 3, 3),
            Arrays.asList(0, 0, 1, 0, 1));


    private Board b;


    @BeforeEach
    public void runBefore() {
        b = new Board();
        b.setRandomSeed(24);
    }

    @Test
    public void testInit() {
        assertEquals(16, b.getHeight());
        assertEquals(30, b.getWidth());
        assertEquals(99, b.getBombs());
    }

    @Test
    public void testChangeDimensionsOnce() {
        b.setHeight(4);
        b.setWidth(5);
        b.generateLayout();
        assertEquals(4, b.getHeight());
        assertEquals(5, b.getWidth());
        assertEquals(4, b.getBombs());
    }

    @Test
    public void testChangeDimensionsMultiple() {
        b.setHeight(2);
        b.setWidth(98);
        b.setHeight(12);
        b.setWidth(73);
        b.setHeight(3);
        b.setWidth(20);
        b.generateLayout();
        assertEquals(3, b.getHeight());
        assertEquals(20, b.getWidth());
        assertEquals(12, b.getBombs());
    }

    @Test
    public void testCornerBomb() {
        b.setHeight(3);
        b.setWidth(3);
        b.setRandomSeed(25);
        b.generateLayout();
        assertEquals(3, b.getHeight());
        assertEquals(3, b.getWidth());
        assertEquals(1, b.getBombs());
        assertEquals(bombsListV1, b.getBombsList());
        assertEquals(inRadiusListV1, b.getInRadiusList());
    }

    @Test
    public void testTopEdgeBomb() {
        b.setHeight(5);
        b.setWidth(5);
        b.setRandomSeed(27);
        b.generateLayout(1);
        assertEquals(5, b.getHeight());
        assertEquals(5, b.getWidth());
        assertEquals(1, b.getBombs());
        assertEquals(bombsListV2, b.getBombsList());
        assertEquals(inRadiusListV2, b.getInRadiusList());
    }

    @Test
    public void testLeftEdgeBomb() {
        b.setHeight(5);
        b.setWidth(5);
        b.setRandomSeed(10);
        b.generateLayout(1);
        assertEquals(5, b.getHeight());
        assertEquals(5, b.getWidth());
        assertEquals(1, b.getBombs());
        assertEquals(bombsListV3, b.getBombsList());
        assertEquals(inRadiusListV3, b.getInRadiusList());
    }

    @Test
    public void testCenterBomb() {
        b.setHeight(5);
        b.setWidth(5);
        b.setRandomSeed(41);
        b.generateLayout(1);
        assertEquals(5, b.getHeight());
        assertEquals(5, b.getWidth());
        assertEquals(1, b.getBombs());
        assertEquals(bombsListV4, b.getBombsList());
        assertEquals(inRadiusListV4, b.getInRadiusList());
    }

    @Test
    public void testMultipleBombs() {
        b.setHeight(5);
        b.setWidth(5);
        b.setRandomSeed(35);
        b.generateLayout();
        assertEquals(5, b.getHeight());
        assertEquals(5, b.getWidth());
        assertEquals(5, b.getBombs());
        assertEquals(bombsListV5, b.getBombsList());
        assertEquals(inRadiusListV5, b.getInRadiusList());
    }

    @Test
    public void testReplaceBombWithoutRemove() {
        b.setHeight(5);
        b.setWidth(5);
        b.setRandomSeed(35);
        b.generateLayout();
        b.replaceBomb();
//        System.out.println(b.getBombsList());
//        System.out.println(b.getInRadiusList());
        assertEquals(bombsListV6, b.getBombsList());
        assertEquals(inRadiusListV6, b.getInRadiusList());
    }

    @Test
    public void testReplaceBombWithRemove() {
        b.setHeight(5);
        b.setWidth(5);
        b.setRandomSeed(35);
        b.generateLayout();
        b.getCell(2, 0).setBomb();
        b.deIncrementSurroundingCells(2, 0);
        b.replaceBomb();
        assertEquals(bombsListV7, b.getBombsList());
        assertEquals(inRadiusListV7, b.getInRadiusList());
    }

    @Test
    public void testReplaceBombsInRadius() {
        b.setHeight(5);
        b.setWidth(5);
        b.setRandomSeed(35);
        b.generateLayout();
        b.replaceBombsInRadius(2, 0);
        assertEquals(bombsListV8, b.getBombsList());
        assertEquals(inRadiusListV8, b.getInRadiusList());
    }

    @Test
    public void testGetFlaggedInRadius() {
        b.setHeight(5);
        b.setWidth(5);
        b.setRandomSeed(35);
        b.generateLayout();
        b.getCell(2, 1).toggleFlag();
        b.getCell(2, 3).toggleFlag();
        b.getCell(3, 2).toggleFlag();
        assertEquals(3, b.getFlaggedInRadius(2,2));
    }

    @Test
    public void testGetUnflaggedMultipe() {
        b.setHeight(5);
        b.setWidth(5);
        b.setRandomSeed(35);
        b.generateLayout();
        b.changeUnflaggedBombs(b.getCell(2, 1).toggleFlag());
        b.changeUnflaggedBombs(b.getCell(2, 3).toggleFlag());
        b.changeUnflaggedBombs(b.getCell(3, 2).toggleFlag());
        assertEquals(b.getBombs() - 3, b.getUnflaggedBombs());
    }

}
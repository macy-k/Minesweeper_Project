package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// unit tests for board class
class BoardTest {
    private static final List<List<Boolean>> bombsListV1 = Arrays.asList(
            Arrays.asList(false, false, false),
            Arrays.asList(false, false, false),
            Arrays.asList(true, false, false));
    private static final List<List<Integer>> inRadiusListV1 = Arrays.asList(
            Arrays.asList(0, 0, 0),
            Arrays.asList(1, 1, 0),
            Arrays.asList(0, 1, 0));
    private static final List<List<Integer>> inRadiusListV1V2 = Arrays.asList(
            Arrays.asList(0, 0, 0),
            Arrays.asList(0, 0, 0),
            Arrays.asList(0, 0, 0));
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
    private static final List<List<Boolean>> bombsListV9 = Arrays.asList(
            Arrays.asList(true, false, true, true, true),
            Arrays.asList(true, true, true, true, true),
            Arrays.asList(true, true, true, true, true),
            Arrays.asList(true, true, false, true, true),
            Arrays.asList(false, true, true, true, false));
    private static final List<List<Integer>> inRadiusListV9 = Arrays.asList(
            Arrays.asList(2, 5, 4, 5, 3),
            Arrays.asList(4, 7, 7, 8, 5),
            Arrays.asList(5, 7, 7, 7, 5),
            Arrays.asList(4, 6, 8, 6, 4),
            Arrays.asList(3, 3, 4, 3, 3));
    private static final List<List<Boolean>> bombsListV10 = Arrays.asList(
            Arrays.asList(false, false, false, false, false),
            Arrays.asList(false, true, false, false, false),
            Arrays.asList(false, false, false, false, false),
            Arrays.asList(false, false, false, false, false),
            Arrays.asList(false, false, false, false, false));
    private static final List<List<Integer>> inRadiusListV10 = Arrays.asList(
            Arrays.asList(1, 1, 1, 0, 0),
            Arrays.asList(1, 0, 1, 0, 0),
            Arrays.asList(1, 1, 1, 0, 0),
            Arrays.asList(0, 0, 0, 0, 0),
            Arrays.asList(0, 0, 0, 0, 0));
    private static final List<List<Boolean>> bombsListV11 = Arrays.asList(
            Arrays.asList(false, false, false, false, false),
            Arrays.asList(false, false, false, false, false),
            Arrays.asList(false, false, true, true, false),
            Arrays.asList(false, false, false, false, false),
            Arrays.asList(false, false, false, false, false));
    private static final List<List<Integer>> inRadiusListV11 = Arrays.asList(
            Arrays.asList(0, 0, 0, 0, 0),
            Arrays.asList(0, 1, 2, 2, 1),
            Arrays.asList(0, 1, 1, 1, 1),
            Arrays.asList(0, 1, 2, 2, 1),
            Arrays.asList(0, 0, 0, 0, 0));


    private Board b;
    // USE THE FOLLOWING FOR TESTING
    //        System.out.println(b.getBombsList());
    //        System.out.println(b.getInRadiusList());


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
        assertEquals(99, b.getUnflaggedBombs());
    }

    @Test
    public void testParameterizedInit() {
        b = new Board(12, 15, 36, 30);
        assertEquals(12, b.getHeight());
        assertEquals(15, b.getWidth());
        assertEquals(36, b.getBombs());
        assertEquals(30, b.getUnflaggedBombs());
        assertTrue(b.getLayout().isEmpty());
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
        b.incrementSurroundingCells(2, 0, false);
        b.replaceBomb();
        assertEquals(bombsListV7, b.getBombsList());
        assertEquals(inRadiusListV7, b.getInRadiusList());
    }

    @Test
    public void testReplaceBombOnBomb() {
        b.setHeight(5);
        b.setWidth(5);
        b.setRandomSeed(35);
        b.generateLayout(20);
        b.replaceBomb();
        assertEquals(bombsListV9, b.getBombsList());
        assertEquals(inRadiusListV9, b.getInRadiusList());
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
    public void testReplaceBombsInRadiusInLeftBottomCorner() {
        b.setHeight(5);
        b.setWidth(5);
        b.setRandomSeed(10);
        b.generateLayout(1);
        b.replaceBombsInRadius(4, 0);
        assertEquals(bombsListV10, b.getBombsList());
        assertEquals(inRadiusListV10, b.getInRadiusList());
    }

    @Test
    public void testReplaceBombsInRadiusInRightTopCorner() {
        b.setHeight(5);
        b.setWidth(5);
        b.setRandomSeed(41);
        b.generateLayout(1);
        b.getCell(1, 3).setBomb();
        b.incrementSurroundingCells(1,3, true);
        b.replaceBombsInRadius(0, 4);
        assertEquals(bombsListV11, b.getBombsList());
        assertEquals(inRadiusListV11, b.getInRadiusList());
    }


    @Test
    public void testDeIncrementSurroundCellsCorner() {
        b.setHeight(3);
        b.setWidth(3);
        b.setRandomSeed(25);
        b.generateLayout();
        b.incrementSurroundingCells(2, 0, false);
        assertEquals(inRadiusListV1V2, b.getInRadiusList());
    }

    @Test
    public void testAddRow() {
        b = new Board(3, 3, 1, 1);
        assertEquals(3, b.getHeight());
        assertEquals(3, b.getWidth());
        assertEquals(1, b.getBombs());
        assertEquals(1, b.getUnflaggedBombs());
        assertTrue(b.getLayout().isEmpty());
        Cell c1 = new Cell(true, false, true, 0);
        Cell c2 = new Cell(false, true, false, 1);
        Cell c3 = new Cell(false, false, false, 0);
        b.addRow(Arrays.asList(c1, c2, c3));
        assertEquals(Arrays.asList(Arrays.asList(c1, c2,c3)), b.getLayout());
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
    public void testGetFlaggedInRadiusLeftBottomCorner() {
        b.setHeight(5);
        b.setWidth(5);
        b.setRandomSeed(10);
        b.generateLayout(1);
        b.getCell(3, 0).toggleFlag();
        assertEquals(1, b.getFlaggedInRadius(4, 0));
    }

    @Test
    public void testGetFlaggedInRadiusRightTopCorner() {
        b.setHeight(5);
        b.setWidth(5);
        b.setRandomSeed(10);
        b.generateLayout(1);
        b.getCell(1, 4).toggleFlag();
        assertEquals(1, b.getFlaggedInRadius(0, 4));
    }

    @Test
    public void testGetBombsInRadiusLeftCorner() {
        b.setHeight(5);
        b.setWidth(5);
        b.setRandomSeed(10);
        b.generateLayout(1);
        assertEquals(1, b.getBombsInRadius(4, 0));
    }

    @Test
    public void testGetBombsInRadiusRightCorner() {
        b.setHeight(5);
        b.setWidth(5);
        b.setRandomSeed(10);
        b.generateLayout(1);
        b.getCell(3, 3).setBomb();
        b.incrementSurroundingCells(3,3, true);
        assertEquals(1, b.getBombsInRadius(4, 0));
    }

    @Test
    public void testGetUnflaggedMultiple() {
        b.setHeight(5);
        b.setWidth(5);
        b.setRandomSeed(35);
        b.generateLayout();
        b.changeUnflaggedBombs(b.getCell(2, 1).toggleFlag());
        b.changeUnflaggedBombs(b.getCell(2, 3).toggleFlag());
        b.changeUnflaggedBombs(b.getCell(3, 2).toggleFlag());
        assertEquals(b.getBombs() - 3, b.getUnflaggedBombs());
    }

    @Test
    public void testGetCorrectlyFlaggedBombs() {
        b.setHeight(5);
        b.setWidth(5);
        b.setRandomSeed(35);
        b.generateLayout();
        b.changeUnflaggedBombs(b.getCell(1, 0).toggleFlag());
        b.changeUnflaggedBombs(b.getCell(2, 0).toggleFlag());
        b.changeUnflaggedBombs(b.getCell(2, 1).toggleFlag());
        b.changeUnflaggedBombs(b.getCell(3, 2).toggleFlag());
        b.changeUnflaggedBombs(b.getCell(4, 4).toggleFlag());
        assertEquals(b.getBombs() - 5, b.getUnflaggedBombs());
        assertEquals(3, b.getCorrectlyFlaggedBombs());
    }

    @Test
    public void testGetAllUnflaggedCellsClearTrue() {
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
        assertTrue(b.getAllUnflaggedCellsClear());
    }

    @Test
    public void testGetAllUnflaggedCellsClearFalse() {
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
        assertFalse(b.getAllUnflaggedCellsClear());
    }

    @Test
    public void testToJson() {
        b.setHeight(3);
        b.setWidth(3);
        b.setRandomSeed(25);
        b.generateLayout();
        assertEquals("{\n" +
                "    \"layout\": [\n" +
                "        [\n" +
                "            {\n" +
                "                \"inRadius\": 0,\n" +
                "                \"isBomb\": false,\n" +
                "                \"isClear\": false,\n" +
                "                \"isFlagged\": false\n" +
                "            },\n" +
                "            {\n" +
                "                \"inRadius\": 0,\n" +
                "                \"isBomb\": false,\n" +
                "                \"isClear\": false,\n" +
                "                \"isFlagged\": false\n" +
                "            },\n" +
                "            {\n" +
                "                \"inRadius\": 0,\n" +
                "                \"isBomb\": false,\n" +
                "                \"isClear\": false,\n" +
                "                \"isFlagged\": false\n" +
                "            }\n" +
                "        ],\n" +
                "        [\n" +
                "            {\n" +
                "                \"inRadius\": 1,\n" +
                "                \"isBomb\": false,\n" +
                "                \"isClear\": false,\n" +
                "                \"isFlagged\": false\n" +
                "            },\n" +
                "            {\n" +
                "                \"inRadius\": 1,\n" +
                "                \"isBomb\": false,\n" +
                "                \"isClear\": false,\n" +
                "                \"isFlagged\": false\n" +
                "            },\n" +
                "            {\n" +
                "                \"inRadius\": 0,\n" +
                "                \"isBomb\": false,\n" +
                "                \"isClear\": false,\n" +
                "                \"isFlagged\": false\n" +
                "            }\n" +
                "        ],\n" +
                "        [\n" +
                "            {\n" +
                "                \"inRadius\": 0,\n" +
                "                \"isBomb\": true,\n" +
                "                \"isClear\": false,\n" +
                "                \"isFlagged\": false\n" +
                "            },\n" +
                "            {\n" +
                "                \"inRadius\": 1,\n" +
                "                \"isBomb\": false,\n" +
                "                \"isClear\": false,\n" +
                "                \"isFlagged\": false\n" +
                "            },\n" +
                "            {\n" +
                "                \"inRadius\": 0,\n" +
                "                \"isBomb\": false,\n" +
                "                \"isClear\": false,\n" +
                "                \"isFlagged\": false\n" +
                "            }\n" +
                "        ]\n" +
                "    ],\n" +
                "    \"unflaggedBombs\": 1,\n" +
                "    \"width\": 3,\n" +
                "    \"bombs\": 1,\n" +
                "    \"height\": 3\n" +
                "}", b.toJson().toString(4));
    }

}
package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CellTest {
    private Cell c1;

    @BeforeEach
    public void runBefore() {
        c1 = new Cell();
    }

    @Test
    public void testInit() {
        assertFalse(c1.getIsBomb());
        assertFalse(c1.getIsClear());
        assertFalse(c1.getIsFlagged());
        assertEquals(0, c1.getInRadius());
    }

    @Test
    public void testParameterizedInit() {
        c1 = new Cell(false, true, false, 5);
        assertFalse(c1.getIsBomb());
        assertTrue(c1.getIsClear());
        assertFalse(c1.getIsFlagged());
        assertEquals(5, c1.getInRadius());
    }

    @Test
    public void testSetBomb() {
        c1.setBomb();
        assertTrue(c1.getIsBomb());
        c1.setBomb();
        assertFalse(c1.getIsBomb());
    }

    @Test
    public void testToggleFlagMultiple() {
        assertEquals(1, c1.toggleFlag());
        assertTrue(c1.getIsFlagged());
        assertEquals(-1, c1.toggleFlag());
        assertFalse(c1.getIsFlagged());
        assertEquals(1, c1.toggleFlag());
        assertTrue(c1.getIsFlagged());
    }

    @Test
    public void testClearUnflaggedNonBomb() {
        assertFalse(c1.getIsFlagged());
        c1.clear();
        assertTrue(c1.getIsClear());
    }

    @Test
    public void testClearUnflaggedBomb() {
        c1.setBomb();
        assertTrue(c1.getIsBomb());
        assertFalse(c1.getIsFlagged());
        c1.clear();
        assertTrue(c1.getIsClear());
    }

    @Test
    public void testClearFlaggedNonBomb() {
        c1.toggleFlag();
        assertTrue(c1.getIsFlagged());
        c1.clear();
        assertFalse(c1.getIsClear());
    }

    @Test
    public void testClearFlaggedBomb() {
        c1.setBomb();
        assertTrue(c1.getIsBomb());
        c1.toggleFlag();
        assertTrue(c1.getIsFlagged());
        c1.clear();
        assertFalse(c1.getIsClear());
    }

    @Test
    public void testIncreaseInRadius() {
        assertEquals(0, c1.getInRadius());
        c1.increaseInRadius();
        assertEquals(1, c1.getInRadius());
    }

    @Test
    public void testIncreaseInRadiusMultiple() {
        assertEquals(0, c1.getInRadius());
        c1.increaseInRadius();
        c1.increaseInRadius();
        c1.increaseInRadius();
        c1.increaseInRadius();
        assertEquals(4, c1.getInRadius());
    }

    @Test
    public void testDecreaseInRadius() {
        assertEquals(0, c1.getInRadius());
        c1.increaseInRadius();
        assertEquals(1, c1.getInRadius());
        c1.decreaseInRadius();
        assertEquals(0, c1.getInRadius());
    }

    @Test
    public void testDecreaseInRadiusMultiple() {
        assertEquals(0, c1.getInRadius());
        c1.increaseInRadius();
        c1.increaseInRadius();
        c1.increaseInRadius();
        c1.increaseInRadius();
        assertEquals(4, c1.getInRadius());
        c1.decreaseInRadius();
        c1.decreaseInRadius();
        c1.decreaseInRadius();
        assertEquals(1, c1.getInRadius());
    }

    @Test
    public void toJson() {
        JSONObject json = c1.toJson();
        assertEquals("{\n" +
                "    \"inRadius\": 0,\n" +
                "    \"isBomb\": false,\n" +
                "    \"isClear\": false,\n" +
                "    \"isFlagged\": false\n" +
                "}", json.toString(4));
    }


}

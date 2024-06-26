package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// unit test for Log class
public class LogTest {
    private Log l1;
    private Log l2;
    private Log l3;

    @BeforeEach
    public void runBefore() {
        l1 = new Log(true, false, 20,5, 19);
        l2 = new Log(false, false, 63, 12, 489);
        l3 = new Log(false, true, 9, 9, 33);
    }

    @Test
    public void testConstructor() {
        assertTrue(l1.getIncomplete());
        assertFalse(l1.getWon());
        assertEquals(20, l1.getBombs());
        assertEquals(5, l1.getScore());
        assertEquals(19, l1.getTime());
        assertEquals("Incomplete", l1.getStateString());
        assertFalse(l2.getIncomplete());
        assertFalse(l2.getWon());
        assertEquals(63, l2.getBombs());
        assertEquals(12, l2.getScore());
        assertEquals(489, l2.getTime());
        assertEquals("Lost", l2.getStateString());
        assertFalse(l3.getIncomplete());
        assertTrue(l3.getWon());
        assertEquals(9, l3.getBombs());
        assertEquals(9, l3.getScore());
        assertEquals(33, l3.getTime());
        assertEquals("Won", l3.getStateString());
    }

    @Test
    public void testToJson() {
        assertEquals("{\n" +
                "    \"score\": 5,\n" +
                "    \"incomplete\": true,\n" +
                "    \"won\": false,\n" +
                "    \"bombs\": 20,\n" +
                "    \"time\": 19\n" +
                "}", l1.toJson().toString(4));
    }
}

package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameLogsTest {
    private GameLogs gameLogs;
    private static final Log L1 = new Log(true, false, 5, 19);
    private static final Log L2 = new Log(false, false, 12, 489);
    private static final Log L3 = new Log(false, true, 9, 33);
    private static final Log L4 = new Log(true, false, 48, 999);
    private static final Log L5 = new Log(false, false, 3, 3);
    private static final Log L6 = new Log(false, true, 99, 899);
    private static final Log L7 = new Log(false, true, 88, 201);

    @BeforeEach
    public void runBefore() {
        gameLogs = new GameLogs();
    }

    @Test
    public void testConstructor() {
        assertTrue(gameLogs.getGameLogs().isEmpty());
        assertEquals(0, gameLogs.getWon());
        assertEquals(0, gameLogs.getLost());
    }

    @Test
    public void testAddLogOnce() {
        gameLogs.addLog(L1);
        assertEquals(1, gameLogs.getGameLogs().size());
        assertEquals(0, gameLogs.getWon());
        assertEquals(0, gameLogs.getLost());
    }

    @Test
    public void testAddLogMultiple() {
        gameLogs.addLog(L1);
        gameLogs.addLog(L2);
        gameLogs.addLog(L3);
        gameLogs.addLog(L4);
        gameLogs.addLog(L5);
        gameLogs.addLog(L6);
        gameLogs.addLog(L7);
        assertEquals(7, gameLogs.getGameLogs().size());
        assertEquals(3, gameLogs.getWon());
        assertEquals(2, gameLogs.getLost());
    }

    @Test
    public void testPrintGameLogsEmpty() {
        assertEquals("No Logs\n", gameLogs.printGameLogs());
    }

    @Test
    public void testPrintGameLogsOnce() {
        gameLogs.addLog(L1);
        assertEquals("Games Finished:0 -- Won:0 -- Win Rate:0%\n" +
                "\n" +
                "Score:5 -- Time:19 -- Incomplete\n", gameLogs.printGameLogs());
    }

    @Test
    public void testPrintGameLogsMultiple() {
        gameLogs.addLog(L1);
        gameLogs.addLog(L2);
        gameLogs.addLog(L3);
        gameLogs.addLog(L4);
        gameLogs.addLog(L5);
        gameLogs.addLog(L6);
        gameLogs.addLog(L7);
        assertEquals("Games Finished:5 -- Won:3 -- Win Rate:60%\n" +
                "\n" +
                "Score:5 -- Time:19 -- Incomplete\n" +
                "Score:12 -- Time:489 -- Lost\n" +
                "Score:9 -- Time:33 -- Won\n" +
                "Score:48 -- Time:999 -- Incomplete\n" +
                "Score:3 -- Time:3 -- Lost\n" +
                "Score:99 -- Time:899 -- Won\n" +
                "Score:88 -- Time:201 -- Won\n", gameLogs.printGameLogs());
    }

    @Test
    public void testGetWinRateOne() {
        gameLogs.addLog(L3);
        assertEquals(100, gameLogs.getWinRate());
    }

    @Test
    public void testWinRateMultipleInteger() {
        gameLogs.addLog(L1);
        gameLogs.addLog(L2);
        gameLogs.addLog(L3);
        gameLogs.addLog(L4);
        gameLogs.addLog(L5);
        gameLogs.addLog(L6);
        gameLogs.addLog(L7);
        assertEquals(60, gameLogs.getWinRate());
    }

    @Test
    public void testWinRateMultipleDecimal() {
        gameLogs.addLog(L2);
        gameLogs.addLog(L2);
        gameLogs.addLog(L2);
        gameLogs.addLog(L2);
        gameLogs.addLog(L2);
        gameLogs.addLog(L2);
        gameLogs.addLog(L3);
        assertEquals(14, gameLogs.getWinRate());
    }

    @Test
    public void testToJson() {
        gameLogs.addLog(L1);
        gameLogs.addLog(L2);
        gameLogs.addLog(L3);
        gameLogs.addLog(L4);
        gameLogs.addLog(L5);
        gameLogs.addLog(L6);
        gameLogs.addLog(L7);
        assertEquals("{\"gameLogs\": [\n" +
                "    {\n" +
                "        \"score\": 5,\n" +
                "        \"incomplete\": true,\n" +
                "        \"won\": false,\n" +
                "        \"time\": 19\n" +
                "    },\n" +
                "    {\n" +
                "        \"score\": 12,\n" +
                "        \"incomplete\": false,\n" +
                "        \"won\": false,\n" +
                "        \"time\": 489\n" +
                "    },\n" +
                "    {\n" +
                "        \"score\": 9,\n" +
                "        \"incomplete\": false,\n" +
                "        \"won\": true,\n" +
                "        \"time\": 33\n" +
                "    },\n" +
                "    {\n" +
                "        \"score\": 48,\n" +
                "        \"incomplete\": true,\n" +
                "        \"won\": false,\n" +
                "        \"time\": 999\n" +
                "    },\n" +
                "    {\n" +
                "        \"score\": 3,\n" +
                "        \"incomplete\": false,\n" +
                "        \"won\": false,\n" +
                "        \"time\": 3\n" +
                "    },\n" +
                "    {\n" +
                "        \"score\": 99,\n" +
                "        \"incomplete\": false,\n" +
                "        \"won\": true,\n" +
                "        \"time\": 899\n" +
                "    },\n" +
                "    {\n" +
                "        \"score\": 88,\n" +
                "        \"incomplete\": false,\n" +
                "        \"won\": true,\n" +
                "        \"time\": 201\n" +
                "    }\n" +
                "]}", gameLogs.toJson().toString(4));
    }
}

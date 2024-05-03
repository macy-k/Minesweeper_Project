package model;

import exceptions.NoSavedGameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// unit tests for GameLogs class
public class GameLogsTest {
    private final GameLogs gameLogs = GameLogs.getInstance();
    private static final Log L1 = new Log(true, false, 20, 5, 19);
    private static final Log L2 = new Log(false, false, 88, 12, 489);
    private static final Log L3 = new Log(false, true, 9, 9, 33);
    private static final Log L4 = new Log(true, false, 99, 48, 999);
    private static final Log L5 = new Log(false, false, 4, 3, 3);
    private static final Log L6 = new Log(false, true, 99, 99, 899);
    private static final Log L7 = new Log(false, true, 88, 88, 201);

    @BeforeEach
    public void runBefore() {
        gameLogs.clearLogs();
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
    public void testClearLogs() {
        gameLogs.addLog(L1);
        gameLogs.addLog(L2);
        gameLogs.addLog(L3);
        gameLogs.addLog(L4);
        gameLogs.addLog(L5);
        gameLogs.addLog(L6);
        gameLogs.addLog(L7);
        assertEquals(7, gameLogs.getGameLogs().size());
        gameLogs.clearLogs();
        assertEquals(0, gameLogs.getGameLogs().size());
    }

    @Test
    public void testPrintGameLogsEmpty() {
        assertEquals("No Logs\n", gameLogs.printGameLogs());
        assertEquals("No Logs\n", gameLogs.printGameLogs(false));
        assertEquals("No Logs\n", gameLogs.printGameLogs(true));
    }

    @Test
    public void testPrintGameLogsOnce() {
        gameLogs.addLog(L1);
        assertEquals("Games Played: 1\n" +
                "Games Finished: 0\n" +
                "Games Won: 0\n" +
                "Completion Rate: 0%\n" +
                "Win Rate: 0%\n" +
                "\n" +
                "Bombs\tScore\tTime\tGame State\n" +
                "B: 20\tS: 5\tT: 19\tIncomplete", gameLogs.printGameLogs());
        assertEquals("Games Played: 1\n" +
                "Games Finished: 0\n" +
                "Games Won: 0\n" +
                "Completion Rate: 0%\n" +
                "Win Rate: 0%\n" +
                "\n" +
                "Bombs\tScore\tTime\tGame State\n" +
                "B: 20\tS: 5\tT: 19\tIncomplete", gameLogs.printGameLogs(false));
    }

    @Test
    public void testPrintGameLogsOnceFiltered() {
        gameLogs.addLog(L1);
        assertEquals("Games Played: 1\n" +
                "Games Finished: 0\n" +
                "Games Won: 0\n" +
                "Completion Rate: 0%\n" +
                "Win Rate: 0%\n" +
                "\n" +
                "Bombs\tScore\tTime\tGame State", gameLogs.printGameLogs(true));
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
        assertEquals("Games Played: 7\n" +
                "Games Finished: 5\n" +
                "Games Won: 3\n" +
                "Completion Rate: 71%\n" +
                "Win Rate: 60%\n" +
                "\n" +
                "Bombs\tScore\tTime\tGame State\n" +
                "B: 20\tS: 5\tT: 19\tIncomplete\n" +
                "B: 88\tS: 12\tT: 489\tLost\n" +
                "B: 9\tS: 9\tT: 33\tWon\n" +
                "B: 99\tS: 48\tT: 999\tIncomplete\n" +
                "B: 4\tS: 3\tT: 3\tLost\n" +
                "B: 99\tS: 99\tT: 899\tWon\n" +
                "B: 88\tS: 88\tT: 201\tWon", gameLogs.printGameLogs());
        assertEquals("Games Played: 7\n" +
                "Games Finished: 5\n" +
                "Games Won: 3\n" +
                "Completion Rate: 71%\n" +
                "Win Rate: 60%\n" +
                "\n" +
                "Bombs\tScore\tTime\tGame State\n" +
                "B: 20\tS: 5\tT: 19\tIncomplete\n" +
                "B: 88\tS: 12\tT: 489\tLost\n" +
                "B: 9\tS: 9\tT: 33\tWon\n" +
                "B: 99\tS: 48\tT: 999\tIncomplete\n" +
                "B: 4\tS: 3\tT: 3\tLost\n" +
                "B: 99\tS: 99\tT: 899\tWon\n" +
                "B: 88\tS: 88\tT: 201\tWon", gameLogs.printGameLogs(false));
    }

    @Test
    public void testPrintGameLogsMultipleFiltered() {
        gameLogs.addLog(L1);
        gameLogs.addLog(L2);
        gameLogs.addLog(L3);
        gameLogs.addLog(L4);
        gameLogs.addLog(L5);
        gameLogs.addLog(L6);
        gameLogs.addLog(L7);
        assertEquals("Games Played: 7\n" +
                "Games Finished: 5\n" +
                "Games Won: 3\n" +
                "Completion Rate: 71%\n" +
                "Win Rate: 60%\n" +
                "\n" +
                "Bombs\tScore\tTime\tGame State\n" +
                "B: 88\tS: 12\tT: 489\tLost\n" +
                "B: 9\tS: 9\tT: 33\tWon\n" +
                "B: 4\tS: 3\tT: 3\tLost\n" +
                "B: 99\tS: 99\tT: 899\tWon\n" +
                "B: 88\tS: 88\tT: 201\tWon", gameLogs.printGameLogs(true));
    }

    @Test
    public void testGetRatesOne() {
        assertEquals(0, gameLogs.getCompletionRate());
        gameLogs.addLog(L3);
        assertEquals(100, gameLogs.getWinRate());
    }

    @Test
    public void testRatesMultipleInteger() {
        gameLogs.addLog(L1);
        gameLogs.addLog(L2);
        gameLogs.addLog(L3);
        gameLogs.addLog(L4);
        gameLogs.addLog(L5);
        gameLogs.addLog(L6);
        gameLogs.addLog(L7);
        assertEquals(60, gameLogs.getWinRate());
        assertEquals(71, gameLogs.getCompletionRate());
    }

    @Test
    public void testRatesMultipleDecimal() {
        gameLogs.addLog(L2);
        gameLogs.addLog(L2);
        gameLogs.addLog(L2);
        gameLogs.addLog(L2);
        gameLogs.addLog(L2);
        gameLogs.addLog(L2);
        gameLogs.addLog(L3);
        assertEquals(14, gameLogs.getWinRate());
        assertEquals(100, gameLogs.getCompletionRate());
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
                "        \"bombs\": 20,\n" +
                "        \"time\": 19\n" +
                "    },\n" +
                "    {\n" +
                "        \"score\": 12,\n" +
                "        \"incomplete\": false,\n" +
                "        \"won\": false,\n" +
                "        \"bombs\": 88,\n" +
                "        \"time\": 489\n" +
                "    },\n" +
                "    {\n" +
                "        \"score\": 9,\n" +
                "        \"incomplete\": false,\n" +
                "        \"won\": true,\n" +
                "        \"bombs\": 9,\n" +
                "        \"time\": 33\n" +
                "    },\n" +
                "    {\n" +
                "        \"score\": 48,\n" +
                "        \"incomplete\": true,\n" +
                "        \"won\": false,\n" +
                "        \"bombs\": 99,\n" +
                "        \"time\": 999\n" +
                "    },\n" +
                "    {\n" +
                "        \"score\": 3,\n" +
                "        \"incomplete\": false,\n" +
                "        \"won\": false,\n" +
                "        \"bombs\": 4,\n" +
                "        \"time\": 3\n" +
                "    },\n" +
                "    {\n" +
                "        \"score\": 99,\n" +
                "        \"incomplete\": false,\n" +
                "        \"won\": true,\n" +
                "        \"bombs\": 99,\n" +
                "        \"time\": 899\n" +
                "    },\n" +
                "    {\n" +
                "        \"score\": 88,\n" +
                "        \"incomplete\": false,\n" +
                "        \"won\": true,\n" +
                "        \"bombs\": 88,\n" +
                "        \"time\": 201\n" +
                "    }\n" +
                "]}", gameLogs.toJson().toString(4));
    }

    @Test
    public void testNoSavedGameExcpetion() {
        try {
            throw new NoSavedGameException();
        } catch (NoSavedGameException e) {
            // pass
        } catch (Exception e) {
            fail();
        }
    }
}

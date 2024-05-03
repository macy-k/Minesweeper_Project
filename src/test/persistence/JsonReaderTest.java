package persistence;

import model.Board;
import model.Game;
import model.GameLogs;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {
    private JsonReader jr;

    @Test
    public void testInit() {
        jr = new JsonReader("./data/testReaderSource.json");
        assertEquals("./data/testReaderSource.json", jr.getSource());
    }

    @Test
    public void testReadEmptyGame() {
        try {
            jr = new JsonReader(".data/testNonExistentFile.json");
            Game g = jr.readGame();
        } catch (IOException e) {
            return;
            // pass
        }
        fail();
    }

    @Test
    public void testReadEmptyGameLogs() {
        try {
            jr = new JsonReader("./data/testNonExistentFile.json");
            jr.readGameLogs();
        } catch (IOException e) {
            return;
            // pass
        }
        fail();
    }

    @Test
    public void testReadGeneralGame(){
        jr = new JsonReader("./data/testGeneralGameWithTime.json");
        Game g;
        g = new Game(new Board()); //so that it initializes
        try {
            g = jr.readGame();
        } catch (IOException e) {
            fail();
        }
        assertFalse(g.isIncomplete());
        assertFalse(g.isStarted());
        assertFalse(g.isWon());
        assertEquals(0, g.getX());
        assertEquals(0, g.getY());
        assertEquals(0, g.getStartTime());
        assertEquals(73, g.getTime());
        Board b = g.getBoard();
        assertEquals(3, b.getHeight());
        assertEquals(3, b.getWidth());
        assertEquals(1, b.getBombs());
        assertEquals(1, b.getUnflaggedBombs());
        assertEquals(Arrays.asList(
                Arrays.asList(false, false, false),
                Arrays.asList(false, false, false),
                Arrays.asList(true, false, false)), b.getBombsList());
        assertEquals(Arrays.asList(
                Arrays.asList(0, 0, 0),
                Arrays.asList(1, 1, 0),
                Arrays.asList(0, 1, 0)), b.getInRadiusList());
    }

    @Test
    public void testReadGeneralGameLogs() {
        jr = new JsonReader("./data/testGeneralGameLogs.json");
        GameLogs gl = GameLogs.getInstance();
        gl.clearLogs();
        try {
            jr.readGameLogs();
        } catch (IOException e) {
            fail();
        }
        assertEquals(7, gl.getGameLogs().size());
        assertEquals(3, gl.getWon());
        assertEquals(2, gl.getLost());
        assertEquals("Games Played: 7\n" +
                "Games Finished: 5\n" +
                "Games Won: 3\n" +
                "Completion Rate: 71%\n" +
                "Win Rate: 60%\n" +
                "\n" +
                "Bombs\tScore\tTime\tGame State\n" +
                "B: 6\tS: 5\tT: 19\tIncomplete\n" +
                "B: 30\tS: 12\tT: 489\tLost\n" +
                "B: 9\tS: 9\tT: 33\tWon\n" +
                "B: 99\tS: 48\tT: 999\tIncomplete\n" +
                "B: 50\tS: 3\tT: 3\tLost\n" +
                "B: 99\tS: 99\tT: 899\tWon\n" +
                "B: 88\tS: 88\tT: 201\tWon", gl.printGameLogs());
    }
}

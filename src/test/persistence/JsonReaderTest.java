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
        assertEquals("Games Finished:5 -- Won:3 -- Win Rate:60%\n" +
                "\n" +
                "Score:5 -- Time:19 -- Incomplete\n" +
                "Score:12 -- Time:489 -- Lost\n" +
                "Score:9 -- Time:33 -- Won\n" +
                "Score:48 -- Time:999 -- Incomplete\n" +
                "Score:3 -- Time:3 -- Lost\n" +
                "Score:99 -- Time:899 -- Won\n" +
                "Score:88 -- Time:201 -- Won\n", gl.printGameLogs());
    }
}

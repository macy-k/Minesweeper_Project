package persistence;

import model.Board;
import model.Game;
import model.GameLogs;
import model.Log;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {
    private static final String  destination = "./data/writingTestFile.json";
    private JsonWriter jw;

    @BeforeEach
    public void runBefore() {
        jw = new JsonWriter(destination);
    }

    @Test
    public void testInit() {
        assertEquals(destination, jw.getDestination());
        assertNull(jw.getWriter());
    }

    @Test
    public void testInitInvalidDestination() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }


    @Test
    public void testOpen(){
        try {
            jw.open();
        } catch (FileNotFoundException e) {
            fail();
        }
    }

    // empty games will not be written
    @Test
    public void testWriteGeneralGame() throws IOException {
        Board b = new Board();
        b.setHeight(3);
        b.setWidth(3);
        b.setRandomSeed(25);
        b.generateLayout();
        Game g = new Game(b);
        jw.open();
        jw.write(g);
        jw.close();

        JSONObject jsonExpected = new JSONObject(JsonReader.readFile("./data/testGeneralGame.json"));
        JSONObject jsonActual = new JSONObject(JsonReader.readFile(destination));
        assertEquals(jsonExpected.toString(4),
                jsonActual.toString(4));
    }

    // empty GameLogs will not be written
    @Test
    public void testWriteGeneralGameLogs() throws IOException {
        GameLogs gl = new GameLogs();
        gl.addLog(new Log(true, false, 5, 19));
        gl.addLog(new Log(false, false, 12, 489));
        gl.addLog(new Log(false, true, 9, 33));
        gl.addLog(new Log(true, false, 48, 999));
        gl.addLog(new Log(false, false, 3, 3));
        gl.addLog(new Log(false, true, 99, 899));
        gl.addLog(new Log(false, true, 88, 201));
        jw.open();
        jw.write(gl);
        jw.close();

        JSONObject jsonExpected = new JSONObject(JsonReader.readFile("./data/testGeneralGameLogs.json"));
        JSONObject jsonActual = new JSONObject(JsonReader.readFile(destination));
        assertEquals(jsonExpected.toString(4),
                jsonActual.toString(4));
    }
}

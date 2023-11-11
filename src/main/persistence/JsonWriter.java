// Inspiration for the design of persistence taken from JsonSerialization Demo

package persistence;

import model.Game;
import model.GameLogs;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Used to write objects to json
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destination);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of GameLogs to file
    public void write(GameLogs gl) {
        JSONObject json = gl.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of Game to file
    public void write(Game g) {
        JSONObject json = g.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public String getDestination() {
        return destination;
    }

}

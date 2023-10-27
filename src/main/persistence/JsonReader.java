// Inspiration for the design of persistence taken from JsonSerialization Demo

package persistence;

import model.Board;
import model.Cell;
import model.Game;
import model.GameLogs;
import model.Log;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads GameLogs from file and returns it;
    // throws IOException if an error occurs reading data from file
    public GameLogs readGameLogs() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGameLogs(jsonObject);
    }

    // EFFECTS: reads Game from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Game readGame() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGame(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    public static String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: reads a json object containing a GameLogs into a GameLogs object and returns it
    private GameLogs parseGameLogs(JSONObject jsonObject) {
        GameLogs gl = new GameLogs();
        addLogs(gl, jsonObject);
        return gl;
    }

    // EFFECTS: adds a list of Log read from the json object to GameLogs
    private void addLogs(GameLogs gl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("gameLogs");
        for (Object json : jsonArray) {
            JSONObject nextLog = (JSONObject) json;
            addLog(gl, nextLog);
        }
    }

    // EFFECTS: adds a Log read from the json object to GameLogs
    private void addLog(GameLogs gl, JSONObject jsonObject) {
        gl.addLog(new Log(jsonObject.getBoolean("incomplete"),
                jsonObject.getBoolean("won"),
                jsonObject.getInt("score"),
                jsonObject.getInt("time")));
    }

    // EFFECTS: reads a json object containing a Game into a Game object and returns it
    private Game parseGame(JSONObject jsonObject) {
        Board b = getBoard(jsonObject);
        Game g = new Game(b);
        g.setTime(jsonObject.getInt("time"));
        return g;
    }

    // EFFECTS: reads a json object containing a Board into a Board object and returns it
    private Board getBoard(JSONObject jsonObject) {
        JSONObject boardJson = jsonObject.getJSONObject("board");
        Board b = new Board(boardJson.getInt("height"),
                boardJson.getInt("width"),
                boardJson.getInt("bombs"),
                boardJson.getInt("unflaggedBombs"));
        List<List<Cell>> layout = getLayout(boardJson);
        for (List<Cell> row : layout) {
            b.addRow(row);
        }
        return b;
    }

    // EFFECTS: reads a json object containing a layout into a List<List<Cell>> and returns it
    private List<List<Cell>> getLayout(JSONObject jsonObject) {
        JSONArray layoutJson = jsonObject.getJSONArray("layout");

        List<List<Cell>> layout = new ArrayList<>();
        for (Object rowJsonObj : layoutJson) {
            JSONArray rowJson = (JSONArray) rowJsonObj;
            layout.add(getRow(rowJson));
        }
        return layout;
    }

    // EFFECTS: reads a json object containing a row of cells into a List<Cell> and returns it
    private List<Cell> getRow(JSONArray rowJson) {
        List<Cell> row = new ArrayList<>();
        for (Object json : rowJson) {
            JSONObject cellJson = (JSONObject) json;
            row.add(getCell(cellJson));
        }
        return row;
    }

    // EFFECTS: reads a json object containing a Cell into a Cell object and returns it
    private Cell getCell(JSONObject cellJson) {
        Cell c = new Cell(cellJson.getBoolean("isBomb"),
                cellJson.getBoolean("isClear"),
                cellJson.getBoolean("isFlagged"),
                cellJson.getInt("inRadius"));
        return c;
    }

    public String getSource() {
        return source;
    }
}

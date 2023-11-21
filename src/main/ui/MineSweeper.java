// Inspiration for the design of ui taken from SnakeGameDemo

package ui;

import model.Board;
import model.Game;
import model.GameLogs;
import model.Log;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

import static java.lang.System.exit;

// Operates the Console Menu and facilitates the activation of a game
public class MineSweeper {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final String JSON_STORE_GAME = "./data/savedGame.json";
    private static final String JSON_STORE_LOG = "./data/Logs.json";
    private static final Boolean engineUsesSwing = true;  // USER CHANGE THIS TO SWITCH ENGINES

    private Board board;
    private Integer rows = 16;
    private Integer columns = 30;
    private GameLogs gameLogs;
    private JsonReader jsonReaderGame;
    private JsonWriter jsonWriterLog;
    private JsonReader jsonReaderLog;

    // EFFECTS: starts minesweeper game.
    public MineSweeper() throws IOException, InterruptedException {
        board = new Board();
        jsonReaderGame = new JsonReader(JSON_STORE_GAME);
        jsonWriterLog = new JsonWriter(JSON_STORE_LOG);
        jsonReaderLog = new JsonReader(JSON_STORE_LOG);
        gameLogs = readGameLogs();

        if (engineUsesSwing) {
//            resetBoard();
//            tryInitiate(swingOrLanterna(board));
            tryLoad();
        } else {
            consoleMenu();
        }
    }

//####################################################################
// Console Game Methods
//####################################################################

    // EFFECTS: runs the console menu while application is still open
    public void consoleMenu() throws IOException, InterruptedException {
        while (true) {
            gameCycleInstructions();
            String command = reader.readLine();
            if (Objects.equals(command, "D")) {
                getNewDimensions();
            } else if (Objects.equals(command, "S")) {
                gameInstructions();
                resetBoard();
                tryInitiate(swingOrLanterna(board));
            } else if (Objects.equals(command, "L")) {
                tryLoad();
            } else if (Objects.equals(command, "V")) {
                System.out.println(gameLogs.printGameLogs());
            } else if (Objects.equals(command, "E")) {
                exit(0);
            } else {
                System.out.println("Not a valid command");
            }
        }
    }

    // EFFECTS: prints instruction for playing game.
    public void gameInstructions() {
        System.out.println("\nNavigate to game window.");
        System.out.println("Controls are: (w, a, s, d) to move cursor, (c) to clear a cell, (n) to save current board, "
                + "(m) to exit current game, and (SPACE) to either");
        System.out.println("    1) When cursor is on an uncleared cell, flags it");
        System.out.println("    2) When cursor is on a clear cell, if the amount of flags in it's radius matches "
                + "it's number, clears all other cells in radius.");
        System.out.println("(if you have closed the game-panel and are stuck, press m)");
        System.out.println();
    }

    // EFFECTS: prints game cycle instructions
    public void gameCycleInstructions() {
        System.out.println("Game Dimensions are currently " + rows + " rows by "
                + columns + " columns.");
        System.out.println("Can either choose the game Dimensions (D), Start Game (S), "
                + "Load Game (L), View Game Logs (V), or Exit (E). ");
    }

//####################################################################
// Game Methods
//####################################################################

    // MODIFIES: this
    // EFFECTS: changes dimensions that will be used to reset board as specified by user
    public void getNewDimensions() throws IOException {
        System.out.print("Rows: ");
        Integer rows = Integer.valueOf(reader.readLine());
        System.out.print("Columns: ");
        Integer columns = Integer.valueOf(reader.readLine());
        if (rows > 0 & columns > 0) {
            this.rows = rows;
            this.columns = columns;
            System.out.println();
        } else {
            System.out.println("Failed. Dimensions must be positive non-zero integers.");
        }
    }

    // MODIFIES: board
    // EFFECTS: reset board with current rows and columns
    public void resetBoard() {
        board.setHeight(rows);
        board.setWidth(columns);
        board.generateLayout();
    }

    // MODIFIES: this
    // EFFECTS: starts engine and update logs after game is over
    public void initiateEngineAndLogs(Engine gameHandler) throws IOException, InterruptedException {
//        gameLogs.addLog(gameHandler.start());
//        jsonWriterLog.open();
//        jsonWriterLog.write(gameLogs);
//        jsonWriterLog.close();
        gameHandler.start();
    }

    // EFFECTS: tries to initiate a game  into an engine
    public void tryInitiate(Engine gameHandler) throws InterruptedException {
        try {
            initiateEngineAndLogs(gameHandler);
        } catch (IOException e) {
            System.out.print("\nStart failed. IOException.\n\n");
        }
    }

    // EFFECTS: tries to load a game file into an engine
    public void tryLoad() throws InterruptedException {
        try {
            Engine gameHandler = readGame();
            initiateEngineAndLogs(gameHandler);
        } catch (IOException e) {
            System.out.print("\nThere is no saved file yet, so a file cannot be loaded.\n\n");
        }
    }

    // EFFECTS: returns GameLogs if there's a saved GameLogs, and otherwise returns an empty GameLogs
    private GameLogs readGameLogs() {
        try {
            return jsonReaderLog.readGameLogs();
        } catch (IOException e) {
            return new GameLogs();
        }
    }

    // EFFECTS: reads in saved game and starts it. Assigns it to a new EngineLanterna constructor. Throws IOException.
    private Engine readGame() throws IOException {
        Game g = jsonReaderGame.readGame();
        g.start();
        return swingOrLanterna(g);
    }

    // MODIFIES: this
    // EFFECTS: writes a log to game logs
    public void writeGameToLogs(Log log) throws IOException, InterruptedException {
        gameLogs.addLog(log);
        jsonWriterLog.open();
        jsonWriterLog.write(gameLogs);
        jsonWriterLog.close();
    }

    // EFFECTS: returns engine using swing or lanterna based on set final boolean engineUsesSwing. Takes Game
    private Engine swingOrLanterna(Game g) {
        if (engineUsesSwing) {
            return new EngineSwing(this, g);
        } else {
            return new EngineLanterna(g);
        }
    }

    // EFFECTS: returns engine using swing or lanterna based on set final boolean engineUsesSwing. Takes Board
    private Engine swingOrLanterna(Board b) {
        if (engineUsesSwing) {
            return new EngineSwing(this, b);
        } else {
            return new EngineLanterna(b);
        }
    }

    // EFFECT: returns string equivalent of game logs
    public String getGameLogs() {
        try {
            return gameLogs.printGameLogs();
        } catch (Exception e) {
            return "";
        }
    }


    public static void main(String[] args) throws Exception  {
        new MineSweeper();
    }
}

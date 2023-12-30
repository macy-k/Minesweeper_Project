// Inspiration for the design of ui taken from SnakeGameDemo

package ui;

import exceptions.NoSavedGameException;
import model.*;
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

    private final Engine engine;
    private final JsonReader jsonReaderGame;
    private final JsonWriter jsonWriterLog;
    private final JsonReader jsonReaderLog;

    // EFFECTS: starts minesweeper game.
    public MineSweeper() throws IOException, InterruptedException {
        jsonReaderGame = new JsonReader(JSON_STORE_GAME);
        jsonWriterLog = new JsonWriter(JSON_STORE_LOG);
        jsonReaderLog = new JsonReader(JSON_STORE_LOG);
        engine = swingOrLanterna(new Game(new Board()));
        readGameLogs();

        if (engineUsesSwing) {
            initiateEngineAndLogs();
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
                resetGame();
                initiateEngineAndLogs();
            } else if (Objects.equals(command, "L")) {
                try {
                    loadGame();
                } catch (NoSavedGameException e) {
                    System.out.print("\nThere is no saved file yet, so a file cannot be loaded.\n\n");
                }
            } else if (Objects.equals(command, "V")) {
                System.out.println(GameLogs.getInstance().printGameLogs());
            } else if (Objects.equals(command, "E")) {
                exit(0);
            } else {
                System.out.println("Not a valid command");
            }
        }
    }

    // EFFECTS: prints game cycle instructions
    public void gameCycleInstructions() {
        System.out.println("Game Dimensions are currently " + engine.getGame().getBoard().getHeight() + " rows by "
                + engine.getGame().getBoard().getWidth() + " columns.");
        System.out.println("Can either choose the game Dimensions (D), Start Game (S), "
                + "Load Game (L), View Game Logs (V), or Exit (E). ");
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

    // MODIFIES: this
    // EFFECTS: changes dimensions that will be used to reset board as specified by user
    public void getNewDimensions() throws IOException {
        System.out.print("Rows: ");
        int rows = Integer.parseInt(reader.readLine());
        System.out.print("Columns: ");
        int columns = Integer.parseInt(reader.readLine());
        if (rows > 0 & columns > 0) {
            engine.getGame().getBoard().setWidth(columns);
            engine.getGame().getBoard().setHeight(rows);
            engine.getGame().getBoard().generateLayout();
            System.out.println();
        } else {
            System.out.println("Failed. Dimensions must be positive non-zero integers.");
        }
    }

//####################################################################
// Game Methods
//####################################################################

    // MODIFIES: this
    // EFFECTS: starts engine and update logs after game is over
    public void initiateEngineAndLogs() throws IOException, InterruptedException {
        engine.start();
        jsonWriterLog.open();
        jsonWriterLog.write();
        jsonWriterLog.close();
    }

    // EFFECTS: tries to start and load a game file into a new engine
    public void loadGame() throws InterruptedException, IOException, NoSavedGameException {
        Game savedGame = readGame();
        savedGame.start();
        engine.newGame(savedGame);
        initiateEngineAndLogs();
    }

    // EFFECTS: returns engine using swing or lanterna based on set final boolean engineUsesSwing. Takes Game
    private Engine swingOrLanterna(Game g) {
        if (engineUsesSwing) {
            return new EngineSwing(this, g);
        } else {
            return new EngineLanterna(this, g);
        }
    }

    // MODIFIES: game, board
    // EFFECTS: reset board with current rows and columns
    public void resetGame() throws IOException, InterruptedException {
        engine.getGame().getBoard().generateLayout();
        engine.newGame(new Game(engine.getGame().getBoard()));
    }

    // EFFECTS: reads saved game. Throws IOException.
    public Game readGame() throws NoSavedGameException {
        try {
            return jsonReaderGame.readGame();
        } catch (IOException e) {
            throw new NoSavedGameException();
        }
    }

    // EFFECTS: sets GameLogs if there's a saved GameLogs, and otherwise initializes GameLogs
    private void readGameLogs() {
        try {
            jsonReaderLog.readGameLogs();
        } catch (IOException e) {
            GameLogs.getInstance().clearLogs();
        }
    }

    // MODIFIES: this
    // EFFECTS: writes a log to game logs
    public void writeGameToLogs(Log log) throws IOException, InterruptedException {
        GameLogs.getInstance().addLog(log);
        jsonWriterLog.open();
        jsonWriterLog.write();
        jsonWriterLog.close();
    }

    // EFFECT: returns string equivalent of game logs
    public String getGameLogString() {
        try {
            return GameLogs.getInstance().printGameLogs();
        } catch (Exception e) {
            return "";
        }
    }

    public static void main(String[] args) throws Exception  {
        new MineSweeper();
    }
}

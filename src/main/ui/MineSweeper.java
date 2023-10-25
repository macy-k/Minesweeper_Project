// Inspiration for the design of ui taken from SnakeGameDemo

package ui;

import model.Board;
import model.Game;
import model.GameLogs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class MineSweeper {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private Board board;
    private Integer rows = 16;
    private Integer columns = 30;
    private GameLogs gameLogs;

    // EFFECTS: Asks questions required to start minesweeper game.
    public MineSweeper() throws IOException, InterruptedException {
        board = new Board();
        gameLogs = new GameLogs();

        while (true) {
            gameCycleInstructions();
            String command = reader.readLine();
            if (Objects.equals(command, "D")) {
                getNewDimensions();
            } else if (Objects.equals(command, "S")) {
                gameInstructions();
                resetBoard();
                Engine gameHandler = new Engine(board);
                gameLogs.addLog(gameHandler.start());
            } else if (Objects.equals(command, "L")) {
                // Load saved board
            } else if (Objects.equals(command, "V")) {
                System.out.println(gameLogs.printGameLogs() + "\n");
            } else if (Objects.equals(command, "E")) {
                return;
            } else {
                System.out.println("Not a valid command");
            }
        }
    }

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
            System.out.println("");
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

    // EFFECTS: prints instruction for playing game.
    public void gameInstructions() {
        System.out.println("Navigate to game window.");
        System.out.println("Controls are: w, a, s, d to move cursor, c to clear a cell, n to save current board, "
                + "m to exit current game, and SPACE to either");
        System.out.println("(1) When cursor is on an uncleared cell, flags it");
        System.out.println("(2) When cursor is on a clear cell, if the amount of flags in it's radius matches "
                + "it's number, clears all other cells in radius.");
        System.out.println("(if you have closed the game-panel and are stuck, press m)");
        System.out.println("");
    }

    // EFFECTS: prints game cycle instructions
    public void gameCycleInstructions() {
        System.out.println("Game Dimensions are currently " + rows + " rows by "
                + columns + " columns.");
        System.out.println("Can either choose the game Dimensions (D), Start Game (S), "
                + "Load Game (L), View Game Logs (V), or Exit (E). ");
    }

    public static void main(String[] args) throws Exception  {
        new MineSweeper();
    }
}

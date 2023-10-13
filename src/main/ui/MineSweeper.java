// Inspiration for the design of ui taken from SnakeGameDemo

package ui;

import model.Board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class MineSweeper {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private Board board;

    // EFFECTS: Asks questions required to start minesweeper game.
    public MineSweeper() throws IOException, InterruptedException {
        board = new Board();
        boolean playing = true;

        while (playing) {
            System.out.println("Game Dimensions are currently " + Integer.toString(board.getWidth())
                    + " x " + Integer.toString(board.getHeight()));
            System.out.println("Can either choose the game Dimensions (D), Start Game (S), or Exit (E). ");
            String command = reader.readLine();
            if (Objects.equals(command, "D")) {
                getNewDimensions();
            } else if (Objects.equals(command, "S")) {
                System.out.println("Navigate to game window.");
                System.out.println("Controls are: w, a, s, d to move cursor, c to clear a cell, and SPACE to either");
                System.out.println("(1) When cursor is on an uncleared cell, flags it");
                System.out.println("(2) When cursor is on a clear cell, if the amount of flags in it's radius matches "
                        + "it's number, clears all other cells in radius.");
                System.out.println("");
                Terminal gameHandler = new Terminal(board);
                gameHandler.start();
            } else if (Objects.equals(command, "E")) {
                playing = false;
            }
        }
    }

    // MODIFIES: board
    // EFFECTS: changes dimensions of board as specified by user
    public void getNewDimensions() throws IOException {
        System.out.print("Rows: ");
        Integer rows = Integer.valueOf(reader.readLine());
        System.out.print("Columns: ");
        Integer columns = Integer.valueOf(reader.readLine());
        if (rows > 0 & columns > 0) {
            board.setHeight(rows);
            board.setWidth(columns);
            board.generateLayout();
            System.out.println("");
        } else {
            System.out.println("Failed. Dimensions must be positive non-zero integers.");
        }
    }

    public static void main(String[] args) throws Exception  {
        new MineSweeper();
    }
}

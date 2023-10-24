package ui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.input.KeyStroke;
import model.Board;
import model.Cell;
import model.Game;


import java.io.IOException;

public class Terminal {
    private static final int SCALE = 1;
    private WindowBasedTextGUI gui;

    private Board board;
    private Screen screen;
    private Game game;

    // EFFECTS: Initiates Terminal with given Board
    public Terminal(Board board) {
        this.board = board;
    }

    // EFFECTS: specifies terminal of size needed for given board and creates screen
    public void start() throws IOException, InterruptedException {
        TerminalSize terminalSize = new TerminalSize(
                (board.getWidth() * SCALE),
                ((board.getHeight() * SCALE) + 2));
        screen = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize).createScreen();
        screen.startScreen();
        gui = new MultiWindowTextGUI(screen);

        game = new Game(board);

        beginTicks();
    }

    // EFFECTS: Begins game cycle with a certain amount of TICKS_PER_SECOND. Continues till game is over. Ends game
    // if window has been exited.
    private void beginTicks() throws InterruptedException, IOException {
        while (!game.isEnded()) {
            tick();
            Thread.sleep(1000L / Game.TICKS_PER_SECOND);
        }
    }

    // MODIFIES: this, game
    // EFFECTS: Handles one cycle of the game
    private void tick() throws IOException {
        handleUserInput();
        game.tick();
        screen.clear();
        render();
        screen.refresh();
    }

    // MODIFIES: game
    // EFFECTS: Handles user keystrokes used to operate game. Responsible for clearing and movement commands
    private void handleUserInput() throws IOException {
        KeyStroke stroke = screen.pollInput();

        if (stroke == null) {
            return;
        }
        if (stroke.getKeyType().equals(KeyType.Character)) {
            if (stroke.getCharacter().equals(' ')) {
                determineSpaceAction();
            } else if (stroke.getCharacter().equals('c')) {
                attemptClear(game.getY(), game.getX());
            } else if (stroke.getCharacter().equals('w')) {
                game.moveUp();
            } else if (stroke.getCharacter().equals('a')) {
                game.moveLeft();
            } else if (stroke.getCharacter().equals('s')) {
                game.moveDown();
            } else if (stroke.getCharacter().equals('d')) {
                game.moveRight();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: renders the window according to game state and board data
    private void render() {
        if (game.isEnded()) {
            renderEndScreen();
            return;
        }
        drawBoard();
        drawUnflaggedBombs();
        drawTimer();
    }

    // MODIFIES: this
    // EFFECTS: draws timer in top right of screen
    private void drawTimer() {
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(TextColor.ANSI.WHITE);
        text.putString(screen.getTerminalSize().getColumns() - 4, 0, game.getTimeString());
    }

    // MODIFIES: this
    // EFFECTS: draws unflagged-bomb count in upper left corner of screen
    private void drawUnflaggedBombs() {
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(TextColor.ANSI.WHITE);
        text.putString(1, 0, "Score: ");
        text.putString(8, 0, String.valueOf(board.getUnflaggedBombs()));
    }

    // MODIFIES: this
    // EFFECTS: draws each individual board cell on screen, aka draws board
    private void drawBoard() {
        for (int i = 0; i < board.getHeight(); i++) {
            for (int ii = 0; ii < board.getWidth(); ii++) {
                drawCell(ii, i);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: draws screen required for when the game has ended
    private void renderEndScreen() {
        drawEndBoard();
        drawUnflaggedBombs();
        drawTimer();
    }

    // MODIFIES: this
    // EFFECTS: draws each individual board cell on screen according to what is needed at the end of the game
    // aka draws end-game board
    private void drawEndBoard() {
        for (int i = 0; i < board.getHeight(); i++) {
            for (int ii = 0; ii < board.getWidth(); ii++) {
                drawEndCells(ii, i);
            }
        }
    }

    // USED FOR DEBUGGING ONLY
    // MODIFIES: this
    // EFFECTS: draws board with bombs visible for debugging
    private void drawDebugBoard() {
        for (int i = 0; i < board.getHeight(); i++) {
            for (int ii = 0; ii < board.getWidth(); ii++) {
                drawDebugCells(ii, i);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: draws a cell based on its state
    @SuppressWarnings({"checkstyle:AvoidEscapedUnicodeCharacters", "checkstyle:SuppressWarnings"}) //Need to use unicode
    private void drawCell(int column, int row) {
        Cell cell = board.getCell(row, column);
        if (cell.getIsClear()) {
            drawPosition(column, row, getPositionColor(column, row), Integer.toString(cell.getInRadius()));
        } else if (cell.getIsFlagged()) {
            drawPosition(column, row, getPositionColor(column, row), '\u2691');
        } else {
            drawPosition(column, row, getPositionColor(column, row), '\u2588');
        }
    }

    // MODIFIES: this
    // EFFECTS: draws a cell based on its state and what is needed to show during the end-game screen
    @SuppressWarnings({"checkstyle:AvoidEscapedUnicodeCharacters", "checkstyle:SuppressWarnings"}) //Need to use unicode
    private void drawEndCells(int column, int row) {
        Cell cell = board.getCell(row, column);
        if (cell.getIsClear()) {
            drawPosition(column, row, getPositionColor(column, row), Integer.toString(cell.getInRadius()));
        } else if (cell.getIsBomb()) {
            drawPosition(column, row, getPositionColor(column, row), '\u2600');
        } else {
            drawPosition(column, row, getPositionColor(column, row), '\u2588');
        }
    }

    // USED FOR DEBUGGING ONLY
    // MODIFIES: this
    // EFFECTS: draws a cell based on its state and what is needed for debugging
    @SuppressWarnings({"checkstyle:AvoidEscapedUnicodeCharacters", "checkstyle:SuppressWarnings"}) //Need to use unicode
    private void drawDebugCells(int column, int row) {
        Cell cell = board.getCell(row, column);
        if (cell.getIsBomb()) {
            drawPosition(column, row, getPositionColor(column, row), '\u2600');
        } else {
            drawPosition(column, row, getPositionColor(column, row), Integer.toString(cell.getInRadius()));
        }
    }

    // MODIFIES: this
    // EFFECTS: draws a given character of a given color at a given position (overloaded)
    private void drawPosition(int column, int row, TextColor color, char c) {
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(color);
        text.putString(column,  row + 2, String.valueOf(c));
    }

    // MODIFIES: this
    // EFFECTS: draws a given string of a given color at a given position (overloaded)
    private void drawPosition(int column, int row, TextColor color, String c) {
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(color);
        text.putString(column,  row + 2, c);
    }

    // EFFECTS chooses color for a tile based on: if it is the selected position, if it is a number which has a certain
    // color, and otherwise is white for uncleared cells
    private TextColor getPositionColor(int column, int row) {
        if ((column == game.getX()) & (row == game.getY())) {
            return TextColor.ANSI.RED;
        } else if (board.getCell(row, column).getIsClear()) {
            int cellInRadius = board.getCell(row, column).getInRadius();
            if (cellInRadius == 0) {
                return TextColor.ANSI.BLACK;
            } else if (cellInRadius == 1) {
                return TextColor.ANSI.BLUE;
            } else if (cellInRadius == 2) {
                return TextColor.ANSI.GREEN;
            } else if (cellInRadius == 3) {
                return TextColor.ANSI.YELLOW;
            } else if (cellInRadius == 4) {
                return TextColor.ANSI.MAGENTA;
            } else {
                return TextColor.ANSI.CYAN;
            }
        } else {
            return TextColor.ANSI.WHITE;
        }
    }

    // MODIFIES: game, cell
    // EFFECTS: attempts to clear a cell, which either succeeds or ends game. Also initiates game if not started.
    public void attemptClear(int row, int column) {
        Cell cell = board.getCell(row, column);
        if (!game.isStarted()) {
            game.start();
            board.replaceBombsInRadius(row, column);
            cell.clear();
            floodClear(row, column);
        } else {
            if (cell.getIsBomb()) {
                game.end();
            } else {
                cell.clear();
                floodClear(row, column);
            }
        }
    }

    // EFFECTS: attempts to clear all cells in radius of given cell (excluding given cell). If a clear cell in
    // radius is a 0, start flood clear.
    public void clearInRadius(int startRow, int startColumn) {
        for (int row = startRow - 1; row <= startRow + 1; row++) {
            if (row >= 0 & row < board.getHeight()) {
                for (int column = startColumn - 1; column <= startColumn + 1; column++) {
                    if (column >= 0 & column < board.getWidth() & !(row == startRow & column == startColumn)) {
                        if (!board.getCell(row, column).getIsFlagged() & !board.getCell(row, column).getIsClear()) {
                            attemptClear(row, column);
                        }
                    }
                }
            }
        }
    }

    // MODIFIES: this, cell
    // EFFECTS: preforms clearInRadius if cell is has 0 in Radius
    public void floodClear(int row, int column) {
        if (board.getCell(row, column).getInRadius() == 0) {
            clearInRadius(row, column);
        }
    }

    // MODIFIES: board, cell
    // EFFECTS: determines what action the space key should do based on cursor position, be it either toggling
    // the flag of an uncleared cell or clearing the radius of cleared cell
    private void determineSpaceAction() {
        Cell cell = board.getCell(game.getY(), game.getX());
        if (!cell.getIsClear()) {
            board.changeUnflaggedBombs(cell.toggleFlag());
        } else if (board.getFlaggedInRadius(game.getY(), game.getX()) == cell.getInRadius()) {
            clearInRadius(game.getY(), game.getX());
        }
    }
}


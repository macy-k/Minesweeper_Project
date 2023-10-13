package ui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
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

    private final Board board;
    private Screen screen;
    private Game game;
//    private WindowBasedTextGUI endGui;

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

        game = new Game(board);

        beginTicks();
    }

    // EFFECTS: Begins game cycle with a certain amount of TICKS_PER_SECOND. Continues till game is over or window has
    // been exited.
    private void beginTicks() throws InterruptedException, IOException {
        while (!game.isEnded()) {
            tick();
            Thread.sleep(1000L / Game.TICKS_PER_SECOND);
        }
    }

    // EFFECTS: Handles one cycle of the game
    private void tick() throws IOException {
        handleUserInput();
        game.tick();
        screen.clear();
        render();
        screen.refresh();
    }

    // MODIFIES: game x and game y
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

    //
    private void render() {
        if (game.isEnded()) {
            renderEndScreen();
            return;
        }
        drawBoard();
        drawUnflaggedBombs();
        drawTimer();
    }

    private void drawTimer() {
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(TextColor.ANSI.WHITE);
        text.putString(screen.getTerminalSize().getColumns() - 4, 0, game.getTimeString());
    }

    private void drawUnflaggedBombs() {
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(TextColor.ANSI.WHITE);
        text.putString(1, 0, "Score: ");
        text.putString(8, 0, String.valueOf(board.getUnflaggedBombs()));
    }

    private void drawBoard() {
        for (int i = 0; i < board.getHeight(); i++) {
            for (int ii = 0; ii < board.getWidth(); ii++) {
                drawCell(ii, i);
            }
        }
    }

    private void renderEndScreen() {
        drawEndBoard();
        drawUnflaggedBombs();
        drawTimer();
    }

    private void drawEndBoard() {
        for (int i = 0; i < board.getHeight(); i++) {
            for (int ii = 0; ii < board.getWidth(); ii++) {
                drawEndCells(ii, i);
            }
        }
    }

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

    @SuppressWarnings({"checkstyle:AvoidEscapedUnicodeCharacters", "checkstyle:SuppressWarnings"}) //Need to use unicode
    private void drawEndCells(int column, int row) {
        Cell cell = board.getCell(row, column);
        if (cell.getIsBomb()) {
            drawPosition(column, row, getPositionColor(column, row), '\u2600');
        } else {
            drawPosition(column, row, getPositionColor(column, row), Integer.toString(cell.getInRadius()));
        }
    }

    private void drawPosition(int column, int row, TextColor color, char c) {
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(color);
        text.putString(column,  row + 2, String.valueOf(c));
    }

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
    
    private void determineSpaceAction() {
        Cell cell = board.getCell(game.getY(), game.getX());
        if (!cell.getIsClear()) {
            board.changeUnflaggedBombs(cell.toggleFlag());
        } else if (board.getFlaggedInRadius(game.getY(), game.getX()) == cell.getInRadius()) {
            clearInRadius(game.getY(), game.getX());
        }
    }

    private void clearInRadius(int startRow, int startColumn) {
        for (int row = startRow - 1; row <= startRow + 1; row++) {
            if (row >= 0 & row < board.getHeight()) {
                for (int column = startColumn - 1; column <= startColumn + 1; column++) {
                    if (column >= 0 & column < board.getWidth() & !(row == startRow & column == startColumn)) {
                        if (!board.getCell(row, column).getIsFlagged()) {
                            attemptClear(row, column);
                        }
                    }
                }
            }
        }
    }

    private void attemptClear(int row, int column) {
        if (!game.isStarted()) {
            game.start();
            board.replaceBombsInRadius(row, column);
            board.getCell(row, column).clear();
        } else {
            Cell cell = board.getCell(row, column);
            if (cell.getIsBomb()) {
                game.end();
            } else {
                cell.clear();
            }
        }
    }
}


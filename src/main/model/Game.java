package model;

import org.json.JSONObject;
import persistence.Writable;

// Holds game state and facilitates operations on board
public class Game implements Writable {
    public static final int TICKS_PER_SECOND = 10;

    private Board board;
    private boolean ended;
    private boolean started;
    private boolean incomplete;
    private boolean won;
    private Integer posX; // column
    private Integer posY; // row
    private long startTime;
    private int time;

    // EFFECTS: Initiates game in top left corner with a non-active clock
    public Game(Board board) {
        ended = false;
        started = false;
        incomplete = false;
        won = false;
        posX = 0;
        posY = 0;
        startTime = 0;
        time = 0;
        this.board = board;
    }

//####################################################################
//Game State Interactions
//####################################################################

    // MODIFIES: this
    // EFFECTS: progresses clock if game is in play. Also checks if game is won, and ends game if so.
    public void tick() {
        if (board.getUnflaggedBombs() == 0) {
            if (board.getCorrectlyFlaggedBombs() == board.getBombs() & board.getAllUnflaggedCellsClear()) {
                end();
                won = true;
            }
        }
        if (started & !ended) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            time = (int) elapsedTime / 1000;
        }
    }

    // MODIFIES: this
    // EFFECTS: records start time for use in the tick() function
    public void startTimer() {
        startTime = System.currentTimeMillis() - (time * 1000);
    }

    // EFFECTS: formats the time into a string used for keeping time in minesweeper
    public String getTimeString() {
        return toTripleString(time);
    }

    // EFFECTS: formats the score into a string used for keeping score in minesweeper
    public String getScoreString() {
        return toTripleString(board.getUnflaggedBombs());
    }

    // EFFECTS: takes an integer and formats it into a string used in minesweeper. String never exceeds "999"
    private String toTripleString(Integer num) {
        if (num < 10) {
            return "00" + Integer.toString(num);
        } else if (num < 100) {
            return "0" + Integer.toString(num);
        } else if (num <= 999) {
            return Integer.toString(num);
        } else {
            return "999";
        }
    }

    // MODIFIES: this
    // EFFECTS: moves the posY up by one
    public void moveUp() {
        if (posY > 0) {
            posY--;
        }
    }

    // MODIFIES: this
    // EFFECTS: moves the posY down by one
    public void moveDown() {
        if (posY < board.getHeight() - 1) {
            posY++;
        }
    }

    // MODIFIES: this
    // EFFECTS: moves the posX left by one
    public void moveLeft() {
        if (posX > 0) {
            posX--;
        }
    }

    // MODIFIES: this
    // EFFECTS: moves the posX right by one
    public void moveRight() {
        if (posX < board.getWidth() - 1) {
            posX++;
        }
    }

    // MODIFIES: this
    // EFFECTS: starts game
    public void start() {
        started = true;
        startTimer();
    }

    // MODIFIES: this
    // EFFECTS: ends game
    public void end() {
        ended = true;
    }

    // MODIFIES: this
    // EFFECTS: ends game in incomplete state
    public void incomplete() {
        ended = true;
        incomplete = true;
    }

    // MODIFIES: this
    // EFFECTS: sets the clock time
    public void setTime(int set) {
        time = set;
    }

    // MODIFIES: this
    // EFFECTS: sets position
    public void setPosition(Integer row, Integer column) {
        posX = column;
        posY = row;
    }

    public boolean isEnded() {
        return ended;
    }

    public boolean isStarted() {
        return started;
    }

    public boolean isIncomplete() {
        return incomplete;
    }

    public boolean isWon() {
        return won;
    }

    public Integer getX() {
        return posX;
    }
    
    public Integer getY() {
        return posY;
    }

    public long getStartTime() {
        return startTime;
    }

    public Board getBoard() {
        return board;
    }

    public int getTime() {
        return time;
    }

//####################################################################
//Board Interactions
//####################################################################

    // MODIFIES: this, board, cell
    // EFFECTS: attempts to clear a cell, which either succeeds or ends game. Also initiates game if not started.
    public void attemptClear(int row, int column) {
        Cell cell = board.getCell(row, column);
        if (!ended) {
            if (!started) {
                start();
                board.replaceBombsInRadius(row, column);
                cell.clear();
                floodClear(row, column);
            } else {
                if (cell.getIsBomb()) {
                    end();
                } else {
                    cell.clear();
                    floodClear(row, column);
                }
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

    // EFFECTS: preforms clearInRadius if cell has 0 in Radius
    public void floodClear(int row, int column) {
        if (board.getCell(row, column).getInRadius() == 0) {
            clearInRadius(row, column);
        }
    }

//####################################################################
//Json Setup
//####################################################################

    // EFFECTS: converts a game into a json object and returns it
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("time", time);
        json.put("board", board.toJson());
        return json;
    }
}

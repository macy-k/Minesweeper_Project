package model;

import org.json.JSONObject;
import persistence.Writable;

public class Game implements Writable {
    public static final int TICKS_PER_SECOND = 10;

    private Board board;
    private boolean ended;
    private boolean started;
    private boolean incomplete;
    private boolean won;
    private int posX; // column
    private int posY; // row
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

    // EFFECTS: thakes an integer and formats it into a string used for keeping time in minesweeper. String never
    // exceeds "999"
    public String getTimeString() {
        if (time < 10) {
            return "00" + Integer.toString(time);
        } else if (time < 100) {
            return "0" + Integer.toString(time);
        } else if (time <= 999) {
            return Integer.toString(time);
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
    // EFFECTS: sets position (for testing purposed)
    public void setPosition(int row, int column) {
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

    public int getX() {
        return posX;
    }
    
    public int getY() {
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

    // EFFECTS: converts a game into a json object and returns it
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("time", time);
        json.put("board", board.toJson());
        return json;
    }
}

package model;

public class Game {
    public static final int TICKS_PER_SECOND = 10;

    private Board board;
    private boolean ended;
    private boolean started;
    private int posX; // column
    private int posY; // row
    private long startTime;
    private int time;

    public Game(Board board) {
        ended = false;
        started = false;
        posX = 0;
        posY = 0;
        startTime = 0;
        time = 0;
        this.board = board;
    }

    public void tick() {
        if (started & !ended) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            time = (int) elapsedTime / 1000;
        }
    }

    public void startTimer() {
        startTime = System.currentTimeMillis();
    }

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
    // EFFECTS: moves the posX up by one
    public void moveUp() {
        if (posY > 0) {
            posY--;
        }
    }

    // MODIFIES: this
    // EFFECTS: moves the posX down by one
    public void moveDown() {
        if (posY < board.getWidth() - 1) {
            posY++;
        }
    }

    // MODIFIES: this
    // EFFECTS: moves the posY left by one
    public void moveLeft() {
        if (posX > 0) {
            posX--;
        }
    }

    // MODIFIES: this
    // EFFECTS: moves the posX right by one
    public void moveRight() {
        if (posX < board.getHeight() - 1) {
            posX++;
        }
    }

    public void start() {
        started = true;
        startTimer();
    }

    public void end() {
        ended = true;
    }

    public boolean isEnded() {
        return ended;
    }

    public boolean isStarted() {
        return started;
    }
    
    public int getX() {
        return posX;
    }
    
    public int getY() {
        return posY;
    }
}

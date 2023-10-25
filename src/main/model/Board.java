package model;

import java.util.*;


public class Board {
    private static final double RATIO = 0.2063;
    private static final Random rand = new Random();

    private int height; // amount of rows
    private int width; // amount of columns
    private int bombs;
    private List<List<Cell>> layout;
    
    private int unflaggedBombs;

    //####################################################################
    //Board Setup
    //####################################################################

    // EFFECTS: initiates standard board with randomized bomb layout
    public Board() {
        height = 16;
        width = 30;
        bombs = 99;
        unflaggedBombs = bombs;
        generateLayout();
    }

    // REQUIRES: height > 0 and width > 0
    // MODIFIES: this, cell
    // EFFECTS: generates layout of blank cells without bombs, then places bombs. Dimensions of height and width
    public void generateLayout() {
        correctBombs();
        layout = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            List row = new ArrayList<>();
            for (int ii = 0; ii < width; ii++) {
                row.add(new Cell());
            }
            layout.add(row);
        }
        placeBombs();
    }

    // REQUIRES: height > 0 and width > 0
    // MODIFIES: this
    // EFFECTS: generates layout of blank cells without bombs, then places a certain number of bombs.
    // Dimensions of height and width. The ability to set number of bombs is used for test purposes.
    public void generateLayout(int setBombs) {
        bombs = setBombs;
        unflaggedBombs = bombs;
        layout = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            List row = new ArrayList<>();
            for (int ii = 0; ii < width; ii++) {
                row.add(new Cell());
            }
            layout.add(row);
        }
        placeBombs();
    }

    // REQUIRES: height > 0, width > 0
    // MODIFIES: this, cell
    // EFFECTS: places all bombs randomly throughout the layout while updating each cell's inRadius fields
    public void placeBombs() {
        for (int i = bombs; i > 0; i--) {
            boolean placedBomb = false;
            while (!placedBomb) {
                int selectedRow = rand.nextInt(height);
                int selectedColumn = rand.nextInt(width);
                Cell targetCell = layout.get(selectedRow).get(selectedColumn);
                if (!targetCell.getIsBomb()) {
                    targetCell.setBomb();
                    incrementSurroundingCells(selectedRow, selectedColumn);
                    placedBomb = true;
                }
            }
        }
    }

    // REQUIRES: height > 0, width > 0
    // MODIFIES: this, cell
    // EFFECTS: places a single bomb randomly on the layout while updating each cell's inRadius fields
    public void replaceBomb() {
        boolean placedBomb = false;
        while (!placedBomb) {
            int selectedRow = rand.nextInt(height);
            int selectedColumn = rand.nextInt(width);
            Cell targetCell = layout.get(selectedRow).get(selectedColumn);
            if (!targetCell.getIsBomb()) {
                targetCell.setBomb();
                incrementSurroundingCells(selectedRow, selectedColumn);
                placedBomb = true;
            }
        }
    }

    // MODIFIES: cell
    // EFFECTS: increments the inRadius field of cells surrounding the starting cell
    // (excluding the starting cell)
    public void incrementSurroundingCells(int startRow, int startColumn) {
        for (int row = startRow - 1; row <= startRow + 1; row++) {
            if (row >= 0 & row < height) {
                for (int column = startColumn - 1; column <= startColumn + 1; column++) {
                    if (column >= 0 & column < width & !(row == startRow & column == startColumn)) {
                        getCell(row, column).increaseInRadius();
                    }
                }
            }
        }
    }

    // MODIFIES: cell
    // EFFECTS: de-increments the inRadius field of cells surrounding the starting cell
    // (excluding the starting cell)
    public void deIncrementSurroundingCells(int startRow, int startColumn) {
        for (int row = startRow - 1; row <= startRow + 1; row++) {
            if (row >= 0 & row < height) {
                for (int column = startColumn - 1; column <= startColumn + 1; column++) {
                    if (column >= 0 & column < width & !(row == startRow & column == startColumn)) {
                        getCell(row, column).decreaseInRadius();
                    }
                }
            }
        }
    }

    // MODIFIES: this, cell
    // EFFECTS: replace all bomb cells in radius (including original cell)
    public void replaceBombsInRadius(int startRow, int startColumn) {
        while (getBombsInRadius(startRow, startColumn) > 0) {
            for (int row = startRow - 1; row <= startRow + 1; row++) {
                if (row >= 0 & row < height) {
                    for (int column = startColumn - 1; column <= startColumn + 1; column++) {
                        if (column >= 0 & column < width) {
                            if (getCell(row, column).getIsBomb()) {
                                getCell(row, column).setBomb();
                                deIncrementSurroundingCells(row, column);
                                replaceBomb();
                            }
                        }
                    }
                }
            }
        }
    }

    //####################################################################
    //Board Interactions
    //####################################################################

    // REQUIRES: height > 0
    // MODIFIES: this
    // EFFECTS: sets the height of the board
    public void setHeight(int height) {
        this.height = height;
    }

    // REQUIRES: width > 0
    // MODIFIES: this
    // EFFECTS: sets the width of the board
    public void setWidth(int width) {
        this.width = width;
    }

    // MODIFIES: this
    // EFFECTS: ensures the value of bombs is correct for the board's height and width
    public void correctBombs() {
        bombs = (int) (height * width * RATIO);
        unflaggedBombs = bombs;
    }

    // MODIFIES: this
    // EFFECTS: changes SecureRandom seed of object rand (for test purposes)
    public void setRandomSeed(int seed) {
        rand.setSeed(seed);
    }

    // MODIFIES: this
    // EFFECTS: changes the value of unflaggedBombs
    public void changeUnflaggedBombs(int change) {
        unflaggedBombs = unflaggedBombs - change;
    }

    //####################################################################
    //Get Board Data
    //####################################################################

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getBombs() {
        return bombs;
    }

    public int getUnflaggedBombs() {
        return unflaggedBombs;
    }

    // EFFECTS: gets certain cell via row and column number (index starts at 0)
    public Cell getCell(int row, int column) {
        return layout.get(row).get(column);
    }
    
    // EFFECTS: get the amount of flagged cells in radius
    public int getFlaggedInRadius(int startRow, int startColumn) {
        int countFlagged = 0;
        for (int row = startRow - 1; row <= startRow + 1; row++) {
            if (row >= 0 & row < height) {
                for (int column = startColumn - 1; column <= startColumn + 1; column++) {
                    if (column >= 0 & column < width & !(row == startRow & column == startColumn)) {
                        if (getCell(row, column).getIsFlagged()) {
                            countFlagged++;
                        }
                    }
                }
            }
        }
        return countFlagged;
    }

    // EFFECTS: get the amount of bomb cells in radius
    public int getBombsInRadius(int startRow, int startColumn) {
        int countBombs = 0;
        for (int row = startRow - 1; row <= startRow + 1; row++) {
            if (row >= 0 & row < height) {
                for (int column = startColumn - 1; column <= startColumn + 1; column++) {
                    if (column >= 0 & column < width) {
                        if (getCell(row, column).getIsBomb()) {
                            countBombs++;
                        }
                    }
                }
            }
        }
        return countBombs;
    }

    // EFFECTS: gets arraylist of each cell's isBomb field
    public List<List<Boolean>> getBombsList() {
        List<List<Boolean>> bombsList = new ArrayList<>();
        for (List<Cell> row : layout) {
            List<Boolean> bombsListRow = new ArrayList<>();
            for (Cell column : row) {
                bombsListRow.add(column.getIsBomb());
            }
            bombsList.add(bombsListRow);
        }
        return bombsList;
    }

//    // EFFECTS: gets arraylist of each cell's isBomb field
//    public List<List<Boolean>> getFlaggedList() {
//        List<List<Boolean>> flaggedList = new ArrayList<>();
//        for (List<Cell> row : layout) {
//            List<Boolean> flaggedListRow = new ArrayList<>();
//            for (Cell column : row) {
//                flaggedListRow.add(column.getIsFlagged());
//            }
//            flaggedList.add(flaggedListRow);
//        }
//        return flaggedList;
//    }

    // EFFECTS: gets arraylist of each cell's inRadius field
    public List<List<Integer>> getInRadiusList() {
        List<List<Integer>> inRadiusList = new ArrayList<>();
        for (List<Cell> row : layout) {
            List<Integer> inRadiusListRow = new ArrayList<>();
            for (Cell column : row) {
                inRadiusListRow.add(column.getInRadius());
            }
            inRadiusList.add(inRadiusListRow);
        }
        return inRadiusList;
    }

    // EFFECTS: gets number of correctly flagged bombs
    public Integer getCorrectlyFlaggedBombs() {
        Integer correctlyFlagged = 0;
        for (List<Cell> row : layout) {
            for (Cell cell : row) {
                if (cell.getIsBomb() & cell.getIsFlagged()) {
                    correctlyFlagged++;
                }
            }
        }
        return correctlyFlagged;
    }
}


package ui;

import model.Cell;
import model.Game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static ui.EngineSwing.lightGrey;

// class used to represent board component of gui
public class BoardPanel extends JPanel implements Scrollable {
    static final Integer cellSideLength = 27;
    private Game game;
    List<List<CellLabel>> cellLabelLayout;

    private GridLayout layoutManager;

    public BoardPanel(Game game) {
        this.game = game;
        constrainBoardSize();
        setBackground(lightGrey); // Use to look for errors
        layoutManager = new GridLayout(game.getBoard().getHeight(), game.getBoard().getWidth());
        layoutManager.setHgap(0);
        layoutManager.setVgap(0);
        setLayout(layoutManager);
        drawBoard();
    }

    // EFFECTS: updates board graphics
    public void update() {
        if (game.isEnded()) {
            removeAll();
            drawBoard();
            revalidate();
        }
        revalidate();
    }

    // MODIFIES: this
    // EFFECTS: constrains board size according to the board assigned to game
    public void constrainBoardSize() {
        int width = game.getBoard().getWidth() * cellSideLength;
        int height = game.getBoard().getHeight() * cellSideLength;
        setMinimumSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));
    }

    // MODIFIES: this
    // EFFECTS: draws board by adding CellLabels to GridLayout in order of board.layout. CellLabels are determined by
    //      each cell's state and the game state
    private void drawBoard() {
        cellLabelLayout = new ArrayList<>();
        Integer rowNum = 0;
        for (List<Cell> row : game.getBoard().getLayout()) {
            List<CellLabel> labelRow = new ArrayList<>();
            Integer colNum = 0;
            for (Cell cell : row) {
                CellLabel c = new CellLabel(cell, rowNum, colNum, this);
                add(c);
                labelRow.add(c);
                colNum++;
            }
            cellLabelLayout.add(labelRow);
            rowNum++;
        }
    }

    public Game getGame() {
        return game;
    }

    public List<List<CellLabel>> getCellLabelLayout() {
        return cellLabelLayout;
    }

//####################################################################
// CellLabel Management
//####################################################################

// These methods are exact copies of the clear methods in Game, except they funnel through CellLabel.clear() first

    // MODIFIES: game, board, cellLabel, cell
    // EFFECTS: attempts to clear a cell, which either succeeds or ends game. Also initiates game if not started.
    //      Reflects these changes through CellLabel and updates CellLabel along the way.
    public void attemptClear(int row, int column) {
        CellLabel cellLabel = getCellLabel(row, column);
        if (!game.isEnded()) {
            if (!game.isStarted()) {
                game.start();
                game.getBoard().replaceBombsInRadius(row, column);
                cellLabel.clear();
                floodClear(row, column);
            } else {
                if (cellLabel.getCell().getIsBomb()) {
                    game.end();
                } else {
                    cellLabel.clear();
                    floodClear(row, column);
                }
            }
        }
    }

    // EFFECTS: attempts to clear all cells in radius of given cell (excluding given cell). If a clear cell in
    // radius is a 0, start flood clear.
    public void clearInRadius(int startRow, int startColumn) {
        for (int row = startRow - 1; row <= startRow + 1; row++) {
            if (row >= 0 & row < game.getBoard().getHeight()) {
                for (int column = startColumn - 1; column <= startColumn + 1; column++) {
                    if (column >= 0 & column < game.getBoard().getWidth()
                            & !(row == startRow & column == startColumn)) {
                        if (!game.getBoard().getCell(row, column).getIsFlagged()
                                & !game.getBoard().getCell(row, column).getIsClear()) {
                            attemptClear(row, column);
                        }
                    }
                }
            }
        }
    }

    // EFFECTS: preforms clearInRadius if cell has 0 in Radius
    public void floodClear(int row, int column) {
        if (getCellLabel(row, column).getCell().getInRadius() == 0) {
            clearInRadius(row, column);
        }
    }

    // EFFECTS: returns CellLabel from CellLabelLayout at given point
    public CellLabel getCellLabel(int row, int column) {
        return cellLabelLayout.get(row).get(column);
    }

//####################################################################
// Scrollable Interface
//####################################################################

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return this.getPreferredSize();
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return cellSideLength * 3;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return cellSideLength;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return false;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }
}

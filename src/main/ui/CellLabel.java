package ui;

import model.Cell;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import java.awt.*;
import java.awt.event.MouseEvent;

import static ui.BoardPanel.cellSideLength;
import static ui.EngineSwing.*;

// Dedicated to creating the correct graphics for each JLabel based on the cell they are representing and the state
//  of the game.
public class CellLabel extends JLabel {
    static final Color oneText = new Color(0,0,255);
    static final Color twoText = new Color(0,123,0);
    static final Color threeText = new Color(255,0,0);
    static final Color fourText = new Color(0,0,123);
    static final Color fiveText = new Color(123, 0, 0);
    static final Color sixText = new Color(0,123,123);
    static final Color sevenText = new Color(123,0,123);
    static final Color eightText = new Color(123, 123, 123);

    private final Cell cell;
    private final Integer row;
    private final Integer col;
    private final BoardPanel bp;

    public CellLabel(Cell cell, Integer row, Integer col, BoardPanel bp) {
        this.cell = cell;
        this.row = row;
        this.col = col;
        this.bp = bp;

        Dimension size = new Dimension(cellSideLength, cellSideLength);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);

        setBackground(lightGrey);
        setHorizontalAlignment(JLabel.CENTER);
        setVerticalAlignment(JLabel.CENTER);
        setHorizontalTextPosition(JLabel.CENTER);
        setVerticalTextPosition(JLabel.CENTER);

        MouseHandler mh = new MouseHandler();
        addMouseListener(mh);
        addMouseMotionListener(mh);

        determineLabelType();
    }


    // MODIFIES: this, cell
    // EFFECTS: clears cell assigned to this CellLabel and updates CellLabel graphics
    public void clear() {
        cell.clear();
        updateLabelType();
    }

    // EFFECTS: removes current label graphics and re-determines them then revalidates
    public void updateLabelType() {
        setIcon(null);
        setText(null);
        determineLabelType();
        revalidate();
    }

    // EFFECTS: determines label graphics to be used based on game state
    private void determineLabelType() {
        if (bp.getGame().isEnded()) {
            determineEndedLabel();
        } else {
            determineInProgressLabel();
        }
    }

    // EFFECTS: determines label graphics to be used when game is ended based on cell
    private void determineEndedLabel() {
        if (cell.getIsClear()) {
            if (cell.getIsBomb()) {
                createClearedBombCell();
            } else {
                createClearedCell();
            }
        } else if (cell.getIsBomb()) {
            if (cell.getIsFlagged()) {
                createFlaggedCell();
            } else {
                createBombCell();
            }
        } else {
            if (cell.getIsFlagged()) {
                createNotBombCell();
            } else {
                createUnclearedCell();
            }
        }
    }

    // EFFECTS: determines label graphics to be used when game is in progress based on cell
    private void determineInProgressLabel() {
        if (cell.getIsClear()) {
            if (cell.getIsBomb()) {
                createClearedBombCell();
            } else {
                createClearedCell();
            }
        } else if (cell.getIsFlagged()) {
            createFlaggedCell();
        } else {
            createUnclearedCell();
        }
    }

//####################################################################
// Label Graphics Creation
//####################################################################

    // MODIFIES: this
    // EFFECTS: creates label graphics for an uncleared cell
    private void createUnclearedCell() {
        setBackground(lightGrey);
        setBorder(new CellBevelBorder(highlight, shadow, shadow, highlight, 3));
        setOpaque(true);
    }

    // MODIFIES: this
    // EFFECTS: creates label graphics for a flagged cell
    private void createFlaggedCell() {
        setBackground(lightGrey);
        setBorder(new CellBevelBorder(highlight, shadow, shadow, highlight, 3));
        setOpaque(true);
        setIcon(new ImageIcon(new ImageIcon("./images/Flag.png").getImage()
                .getScaledInstance(cellSideLength - 8, cellSideLength - 8, Image.SCALE_AREA_AVERAGING)));
    }

    // MODIFIES: this
    // EFFECTS: creates label graphics for a standard cleared cell
    private void createClearedCell() {
        setBackground(lightGrey);
        setBorder(BorderFactory.createLineBorder(shadow, 1, false));
        setOpaque(true);
        setText(getCellText());
        setFont(new Font("Dialog", Font.BOLD, cellSideLength - 4));
        setForeground(getTextColor());
    }

    // MODIFIES: this
    // EFFECTS: creates label graphics for a cleared bomb cell, which indicates why the game has ended
    private void createClearedBombCell() {
        setBackground(endRed);
        setBorder(BorderFactory.createLineBorder(shadow, 1, false));
        setOpaque(true);
        setIcon(new ImageIcon(new ImageIcon("./images/Mine.png").getImage()
                .getScaledInstance(cellSideLength - 5, cellSideLength - 5, Image.SCALE_AREA_AVERAGING)));

    }

    // MODIFIES: this
    // EFFECTS: creates label graphics for a bomb cell to be viewed when the game has ended
    private void createBombCell() {
        setBackground(lightGrey);
        setBorder(BorderFactory.createLineBorder(shadow, 1, false));
        setOpaque(true);
        setIcon(new ImageIcon(new ImageIcon("./images/Mine.png").getImage()
                .getScaledInstance(cellSideLength - 5, cellSideLength - 5, Image.SCALE_AREA_AVERAGING)));
    }

    // MODIFIES: this
    // EFFECTS: creates label graphics for an incorrectly flagged cell to be viewed when the game has ended
    private void createNotBombCell() {
        setBackground(lightGrey);
        setBorder(BorderFactory.createLineBorder(shadow, 1, false));
        setOpaque(true);
        setIcon(new ImageIcon(new ImageIcon("./images/Not Mine.png").getImage()
                .getScaledInstance(cellSideLength - 5, cellSideLength - 5, Image.SCALE_AREA_AVERAGING)));
    }

    // EFFECTS: retrieves the String of the cell's InRadius field for graphical use
    private String getCellText() {
        int textInt = cell.getInRadius();
        if (textInt == 0) {
            return null;
        } else {
            return Integer.toString(textInt);
        }
    }

    // EFFECTS: retrieves the color of text based on cells InRadius field
    private Color getTextColor() {
        int c = cell.getInRadius();
        if (c == 0) {
            return null;
        } else if (c == 1) {
            return oneText;
        } else if (c == 2) {
            return twoText;
        } else if (c == 3) {
            return threeText;
        } else if (c == 4) {
            return fourText;
        } else if (c == 5) {
            return fiveText;
        } else if (c == 6) {
            return sixText;
        } else if (c == 7) {
            return sevenText;
        } else {
            return eightText;
        }
    }

//####################################################################
// Get Methods
//####################################################################

    public Cell getCell() {
        return cell;
    }

//####################################################################
// Mouse Listener
//####################################################################

    // EFFECTS: private class to handle mouse inputs
    private class MouseHandler extends MouseInputAdapter {

        // EFFECTS: handles occurrences of the mouse over CellLabels and uses it
        //      to set game position for clearing purposes
        @Override
        public void mouseMoved(MouseEvent e) {
            bp.getGame().setPosition(row, col);
        }

        // EFFECTS: first ensures game position is correct then attempts to clear through the BoardPanel
        @Override
        public void mouseClicked(MouseEvent e) {
            bp.getGame().setPosition(row, col);
            bp.attemptClear(row, col);
        }
    }
}

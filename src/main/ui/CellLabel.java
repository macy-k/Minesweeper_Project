package ui;

import model.Cell;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.awt.*;

import static ui.BoardPanel.cellSideLength;
import static ui.EngineSwing.*;


public class CellLabel extends JLabel {
    static final Color oneText = new Color(0,0,255);
    static final Color twoText = new Color(0,123,0);
    static final Color threeText = new Color(255,0,0);
    static final Color fourText = new Color(0,0,123);
    static final Color fiveText = new Color(123, 0, 0);
    static final Color sixText = new Color(0,123,123);
    static final Color sevenText = new Color(123,0,123);
    static final Color eightText = new Color(123, 123, 123);
    private final Dimension size = new Dimension(cellSideLength, cellSideLength);
    private Cell cell;

    public CellLabel(Cell cell) {
        this.cell = cell;
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);
        setBackground(lightGrey);
        setHorizontalAlignment(JLabel.CENTER);
        setVerticalAlignment(JLabel.CENTER);
        determineLabel();
    }

    private void determineLabel() {
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

    private void createUnclearedCell() {
        setBackground(lightGrey);
        setBorder(new CellBevelBorder(highlight, shadow, shadow, highlight, 3));
        setOpaque(true);
    }

    private void createFlaggedCell() {
        setBackground(lightGrey);
        setBorder(new CellBevelBorder(highlight, shadow, shadow, highlight, 3));
        setOpaque(true);
        setIcon(new ImageIcon(new ImageIcon("./images/Flag.png").getImage()
                .getScaledInstance(cellSideLength - 8, cellSideLength - 8, Image.SCALE_AREA_AVERAGING)));
    }

    private void createClearedCell() {
        setBackground(lightGrey);
        setBorder(BorderFactory.createLineBorder(shadow, 1, false));
        setOpaque(true);
        setText(getCellText());
        setFont(new Font("Dialog", Font.BOLD, cellSideLength - 4));
        setHorizontalTextPosition(JLabel.CENTER);
        setVerticalTextPosition(JLabel.CENTER);
        setForeground(getTextColor());
    }

    private void createClearedBombCell() {
        setBackground(endRed);
        setBorder(BorderFactory.createLineBorder(shadow, 1, false));
        setOpaque(true);
        setIcon(new ImageIcon(new ImageIcon("./images/Mine.png").getImage()
                .getScaledInstance(cellSideLength - 5, cellSideLength - 5, Image.SCALE_AREA_AVERAGING)));

    }

    private String getCellText() {
        Integer textInt = cell.getInRadius();
        if (textInt == 0) {
            return null;
        } else {
            return textInt.toString();
        }
    }

    private Color getTextColor() {
        Integer c = cell.getInRadius();
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
}

package ui;

import model.Game;

import javax.swing.*;
import java.awt.*;

// class used to represent board component
public class BoardPanel extends JPanel implements Scrollable {
    private final Integer moveSpeed = 5;
    private final Integer cellSideLength = 20;
    private Game game;

    private Integer width;
    private Integer height;


    public BoardPanel(Game game) {
        this.game = game;
        width = game.getBoard().getWidth() * cellSideLength;
        height = game.getBoard().getHeight() * cellSideLength;
        setMinimumSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLUE);
        setLayout(new GridLayout(height / cellSideLength, width / cellSideLength));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);

        if (game.isEnded()) {
            gameOverBoard(g);
        }
    }

    private void drawBoard(Graphics g) {
        // stub
    }

    private void gameOverBoard(Graphics g) {
        //stub
    }

    public void newGame(Game game) {
        this.game = game;
    }

    public Integer getBPWidth() {
        return width;
    }

    public Integer getBPHeight() {
        return height;
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
        return cellSideLength;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return cellSideLength * 3;
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

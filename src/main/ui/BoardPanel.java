package ui;

import model.Cell;
import model.Game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static ui.EngineSwing.lightGrey;

// class used to represent board component
public class BoardPanel extends JPanel implements Scrollable {
    static final Integer cellSideLength = 27;
    private Game game;

    private GridLayout layout;


    public BoardPanel(Game game) {
        this.game = game;
        Integer width = game.getBoard().getWidth() * cellSideLength;
        Integer height = game.getBoard().getHeight() * cellSideLength;
        setMinimumSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));
        setBackground(lightGrey); // Use to look for errors
        layout = new GridLayout(game.getBoard().getHeight(), game.getBoard().getWidth());
        layout.setHgap(0);
        layout.setVgap(0);
        setLayout(layout);
        drawBoard();
    }

    public void update() {
        removeAll();
        if (game.isEnded()) {
            drawGameOverBoard();
        } else {
            // shouldn't do something each tick, should only change when action occurs
            drawBoard();
        }
        revalidate();
    }

//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        drawBoard(g);
//
//        if (game.isEnded()) {
//            gameOverBoard(g);
//        }
//    }

    private void drawBoard() {
        for (List<Cell> row : game.getBoard().getLayout()) {
            for (Cell cell : row) {
                add(new CellLabel(cell));
            }
        }
    }

    private void drawGameOverBoard() {
        //stub
    }

    public void newGame(Game game) {
        this.game = game;
        layout.setRows(game.getBoard().getHeight());
        layout.setColumns(game.getBoard().getWidth());
        // probably more
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

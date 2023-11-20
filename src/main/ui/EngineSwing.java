// Inspiration for the design of gui taken from SpaceInvadersBase

package ui;

import model.Board;
import model.Cell;
import model.Game;
import model.Log;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

// Operates the game GUI with Swing, handles user inputs and handles ticks
public class EngineSwing extends JFrame implements Engine, WindowListener {
    private static final String JSON_STORE_GAME = "./data/savedGame.json";
    static final Color backgroundGrey = new Color(51, 51, 51);
    static final Color lightGrey = new Color(188,188, 188);
    static final Color shadowGrey = new Color(116, 116, 116);
    static final Color endRed = new Color(252, 4, 4);

    private MineSweeper top;
    private Board board;
    private Game game;
    private JsonWriter jsonWriterGame;

    private JScrollPane scrollPane;
    private BoardPanel bp;
    private MenuPanel mp;


    // EFFECTS: Initiates EngineSwing with given Board
    public EngineSwing(MineSweeper top, Board board) {
        super("Minesweeper");
        this.board = board;
        this.top = top;
        jsonWriterGame = new JsonWriter(JSON_STORE_GAME);
        game = new Game(board);
    }

    // EFFECTS: Initiates EngineSwing with given Game
    public EngineSwing(MineSweeper top, Game game) {
        super("Minesweeper");
        this.board = game.getBoard();
        this.top = top;
        jsonWriterGame = new JsonWriter(JSON_STORE_GAME);
        this.game = game;
    }

    // EFFECTS: specifies terminal of size needed for given board and creates screen. Once game is over returns
    // a log of the game
    public Log start() {
        addWindowListener(this);
        setUndecorated(false);
        setIconImage(new ImageIcon("./images/Mine.png").getImage());
        setSize(700, 600);
        setMinimumSize(new Dimension(600, 400));
        setLayout(new BorderLayout());
        getContentPane().setBackground(backgroundGrey);

        bp = new BoardPanel(game);
        mp = new MenuPanel(this, game);
        mp.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, shadowGrey));

        createScrollPane();
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(mp, BorderLayout.NORTH);
        pack();

        addKeyListener(new KeyHandler());
        centreOnScreen();
        setVisible(true);
        tick();
        return new Log(game.isIncomplete(), game.isWon(), board.getCorrectlyFlaggedBombs(), game.getTime());
    }

    private void createScrollPane() {
        JPanel centeredBpHolder = new JPanel();
        centeredBpHolder.setBackground(backgroundGrey);
        centeredBpHolder.setLayout(new GridBagLayout());
        centeredBpHolder.add(bp);
        scrollPane = new JScrollPane(centeredBpHolder);
        scrollPane.setBorder(BorderFactory.createLineBorder(backgroundGrey, 0, false));
    }

    // EFFECTS: Begins game cycle with a certain amount of TICKS_PER_SECOND. Continues till game is over.
    private void tick() {
        Timer t = new Timer(Game.TICKS_PER_SECOND, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                game.tick();
//                gp.repaint();
                mp.update();

            }
        });

        t.start();
    }

    // MODIFIES: this
    // EFFECTS:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
    }

//####################################################################
// ???
//####################################################################

    public String getGameLogs() {
        return top.getGameLogs();
    }

    public void changeBoard(Board board) {
        this.board = board;
    }

//####################################################################
// Keyboard Listener
//####################################################################

    // private class to handle keyboard inputs
    private class KeyHandler extends KeyAdapter {
        // MODIFIES: game
        // EFFECTS: Handles user keystrokes used to operate game. Responsible for clearing commands
        @Override
        public void keyTyped(KeyEvent e) {
            char stroke = e.getKeyChar();
            if (stroke == ' ') {
                //game.setPosition(mouse Y(row), mouse X(column);
                determineSpaceAction();
            } else if (stroke == 'c') {
                //game.setPosition(mouse Y(row), mouse X(column);
                game.attemptClear(game.getY(), game.getX());
            }
        }

        // MODIFIES: board, cell
        // EFFECTS: determines what action the space key should do based on cursor position, be it either toggling
        // the flag of an uncleared cell or clearing the radius of cleared cell
        private void determineSpaceAction() {
            Cell cell = board.getCell(game.getY(), game.getX());
            if (!cell.getIsClear()) {
                board.changeUnflaggedBombs(cell.toggleFlag());
            } else if (board.getFlaggedInRadius(game.getY(), game.getX()) == cell.getInRadius()) {
                game.clearInRadius(game.getY(), game.getX());
            }
        }
    }

//####################################################################
// Window Listener
//####################################################################

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("CLOSED :)");
//        try {
//            top.writeGameToLogs(new Log(game.isIncomplete(),
//                    game.isWon(),
//                    board.getCorrectlyFlaggedBombs(),
//                    game.getTime()));
//        } catch (IOException | InterruptedException ex) {
//            //game is already closing, do nothing
//        } finally {
//            System.exit(0);
//        }
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}

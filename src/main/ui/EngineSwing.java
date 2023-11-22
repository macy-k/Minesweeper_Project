// Inspiration for the design of gui taken from SpaceInvadersBase

package ui;

import model.Cell;
import model.Game;
import model.Log;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

// Operates the game GUI with Swing, handles user inputs and handles ticks
public class EngineSwing extends JFrame implements Engine, WindowListener {
    private static final String JSON_STORE_GAME = "./data/savedGame.json";
    static final Color backgroundGrey = new Color(51, 51, 51);
    static final Color lightGrey = new Color(188,188, 188);
    static final Color highlight = new Color(255,255,255);
    static final Color shadow = new Color(116, 116, 116);
    static final Color endRed = new Color(252, 4, 4);

    private MineSweeper top;
    private Game game;
    private JsonWriter jsonWriterGame;
    private Timer timer;

    private Container contentPane;
    private JPanel centeredBpHolder;
    private JScrollPane scrollPane;
    private BoardPanel bp;
    private MenuPanel mp;


    // EFFECTS: Initiates EngineSwing with given Game
    public EngineSwing(MineSweeper top, Game game) {
        super("Minesweeper");
        this.top = top;
        jsonWriterGame = new JsonWriter(JSON_STORE_GAME);
        this.game = game;
    }

    // MODIFIES: this
    // EFFECTS: specifies terminal for given board and organizes top-level specifics and child components.
    public void start() throws IOException, InterruptedException {
        addWindowListener(this);
        setUndecorated(false);
        setIconImage(new ImageIcon("./images/Mine.png").getImage());
        setMinimumSize(new Dimension(1050, 600));
        setPreferredSize(new Dimension(800, 600));
        setSize(new Dimension(800, 600));
        setLayout(new BorderLayout());
        contentPane = getContentPane();
        contentPane.setBackground(backgroundGrey);

        bp = new BoardPanel(game);
        mp = new MenuPanel(this);
        bp.setBorder(BorderFactory.createLineBorder(shadow, 3, false));
        mp.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, shadow));

        createScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(mp, BorderLayout.NORTH);
        pack();

        addKeyListener(new KeyHandler());
        centreOnScreen();
        setVisible(true);
        tick();
    }

    // MODIFIES: this
    // EFFECTS: initiates scrollPane used for BoardPanel
    private void createScrollPane() {
        centeredBpHolder = new JPanel();
        centeredBpHolder.setBackground(backgroundGrey);
        centeredBpHolder.setLayout(new GridBagLayout());
        centeredBpHolder.add(bp);
        scrollPane = new JScrollPane(centeredBpHolder);
        scrollPane.setBorder(BorderFactory.createLineBorder(backgroundGrey, 0, false));
    }

    // MODIFIES: this, game, BoardPanel, MenuPanel
    // EFFECTS: Begins game cycle with a certain amount of TICKS_PER_SECOND. Continues till game is over.
    private void tick() {
        timer = new Timer(Game.TICKS_PER_SECOND, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                game.tick();
                bp.update();
                mp.update();

            }
        });

        timer.start();
    }

    // MODIFIES: this
    // EFFECTS:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
    }

//####################################################################
// Game Methods
//####################################################################

    // MODIFIES: this, game, gamelogs, BoardPanel, MenuPanel
    // EFFECTS: saves previous game to logs if it was started and starts a new game
    public void newGame(Game game) throws IOException, InterruptedException {
        if (this.game.isStarted()) {
            top.getGameLogs().addLog(new Log(this.game.isIncomplete(), this.game.isWon(),
                    this.game.getBoard().getCorrectlyFlaggedBombs(), this.game.getTime()));
        }
        timer.stop();
        this.game = game;
        contentPane.removeAll();

        bp = new BoardPanel(game);
        mp = new MenuPanel(this);
        bp.setBorder(BorderFactory.createLineBorder(shadow, 3, false));
        mp.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, shadow));

        createScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(mp, BorderLayout.NORTH);
        pack();
        tick();
    }

    // MODIFIES: this, game, gamelogs, BoardPanel, MenuPanel
    // EFFECTS: saves previous game to logs if it was started with a given score and starts a new game
    public void newGame(Game game, Integer score) throws IOException, InterruptedException {
        if (this.game.isStarted()) {
            top.getGameLogs().addLog(new Log(this.game.isIncomplete(), this.game.isWon(),
                    score, this.game.getTime()));
        }
        timer.stop();
        this.game = game;
        contentPane.removeAll();

        bp = new BoardPanel(game);
        mp = new MenuPanel(this);
        bp.setBorder(BorderFactory.createLineBorder(shadow, 3, false));
        mp.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, shadow));

        createScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(mp, BorderLayout.NORTH);
        pack();
        tick();
    }

    // MODIFIES: json files
    // EFFECTS: saves game if not started
    public void saveGame() {
        if (!game.isStarted()) {
            //for future: create WarningDialog popup with "Cannot save un-started game"
            return;
        }
        try {
            jsonWriterGame.open();
            jsonWriterGame.write(game);
            jsonWriterGame.close();
        } catch (FileNotFoundException e) {
            //for future: create WarningDialog popup with "Unable to write to file"
        }
    }

    // EFFECTS: gets gameLogs String from toplevel Minesweeper (which holds the game logs)
    public String getGameLogs() {
        return top.getGameLogString();
    }

    public Game getGame() {
        return game;
    }

    public MineSweeper getTop() {
        return top;
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
                determineSpaceAction();
            }
        }

        // MODIFIES: board, CellLabel, cell
        // EFFECTS: determines what action the space key should do based on cursor position, be it either toggling
        //      the flag of an uncleared cell or clearing the radius of cleared cell. Also funnels through BoardPanel
        //      methods to ensure CellLabel graphics update
        private void determineSpaceAction() {
            if (!game.isEnded()) {
                Cell cell = game.getBoard().getCell(game.getY(), game.getX());
                if (!cell.getIsClear()) {
                    game.getBoard().changeUnflaggedBombs(cell.toggleFlag());
                    bp.getCellLabel(game.getY(), game.getX()).updateLabelType();
                } else if (game.getBoard().getFlaggedInRadius(game.getY(), game.getX()) == cell.getInRadius()) {
                    bp.clearInRadius(game.getY(), game.getX());
                }
            }
        }
    }

//####################################################################
// Window Listener
//####################################################################

    @Override
    public void windowOpened(WindowEvent e) {

    }

    // MODIFIES: game, GameLogs
    // EFFECTS: when window is closed, save current game to gameLogs if it has been started
    @Override
    public void windowClosing(WindowEvent e) {
        try {
            if (game.isStarted()) {
                if (!game.isEnded()) {
                    game.incomplete();
                }
                top.writeGameToLogs(new Log(game.isIncomplete(),
                        game.isWon(),
                        game.getBoard().getCorrectlyFlaggedBombs(),
                        game.getTime()));
            }
        } catch (IOException | InterruptedException ex) {
            //game is already closing, do nothing
        } finally {
            System.exit(0);
        }
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

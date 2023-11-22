package ui;

import model.Board;
import model.Game;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static ui.EngineSwing.*;

// handles the dialog responsible for setting new Board dimensions
public class DimensionDialog extends JDialog {
    private EngineSwing engine;
    private Container contentPane;
    private JPanel selectorPanel;
    private JPanel messagePanel;

    private JSpinner rowSelect;
    private JSpinner colSelect;
    private JLabel rowText;
    private JLabel colText;
    private JPanel buttonPanel;


    public DimensionDialog(EngineSwing owner, String title, ModalityType modality) {
        super(owner, title, modality);
        engine = owner;
        contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setSize(new Dimension(250, 200));
        setResizable(false);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
    }

    // MODIFIES: this
    // EFFECTS: sets up components for dialog and makes it visible
    public void start() {
        selectorPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        selectorPanel.setLayout(layout);

        rowSelect = new Selector();
        colSelect = new Selector();
        rowSelect.setValue(16);
        colSelect.setValue(30);

        rowText = new JLabel("Rows: ");
        rowText.setFont(new Font("Dialog", Font.BOLD, 11));
        colText = new JLabel("Columns: ");
        colText.setFont(new Font("Dialog", Font.BOLD, 11));

        selectorPanel.add(rowSelect);
        selectorPanel.add(colSelect);
        selectorPanel.add(rowText);
        selectorPanel.add(colText);

        handleConstraints(layout);
        createSetButton();
        createMessagePanel();

        contentPane.add(selectorPanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        contentPane.add(messagePanel, BorderLayout.NORTH);
        setVisible(true);
    }

    // handles constraints used for the SpringLayout of the selectorPanel
    private void handleConstraints(SpringLayout layout) {
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, rowSelect, 30,
                SpringLayout.VERTICAL_CENTER, selectorPanel);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, colSelect, -30,
                SpringLayout.VERTICAL_CENTER, selectorPanel);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, rowSelect, 30,
                SpringLayout.HORIZONTAL_CENTER, selectorPanel);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, colSelect, 30,
                SpringLayout.HORIZONTAL_CENTER, selectorPanel);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, rowText, 30,
                SpringLayout.VERTICAL_CENTER, selectorPanel);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, colText, -30,
                SpringLayout.VERTICAL_CENTER, selectorPanel);
        layout.putConstraint(SpringLayout.EAST, rowText, -15,
                SpringLayout.WEST, rowSelect);
        layout.putConstraint(SpringLayout.EAST, colText, -15,
                SpringLayout.WEST, colSelect);
    }

    // EFFECTS: creates button for setting the dimensions and the panel that holds it
    private void createSetButton() {
        buttonPanel = new JPanel();
        JButton setButton = new JButton("Set");
        setButton.setPreferredSize(new Dimension(50, 25));
        setButton.setMaximumSize(new Dimension(50, 25));
        setButton.setBackground(lightGrey);
        setButton.setFocusable(false);
        setButton.setBorder(new CellBevelBorder(highlight, shadow, shadow, highlight, 3));
        setButton.addActionListener(e -> {
            try {
                pressedSetOkButton();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
        buttonPanel.add(setButton, BorderLayout.CENTER);
    }

    // EFFECTS: creates the label used to deliver the dialog message and the panel that holds it
    private void createMessagePanel() {
        messagePanel = new JPanel();
        JLabel message = new JLabel("Select dimensions for a new game.");
        message.setPreferredSize(new Dimension(220, 18));
        message.setFont(new Font("Dialog", Font.BOLD, 11));
        messagePanel.add(message, BorderLayout.WEST);
    }

    // MODIFIES: game, engine
    // EFFECTS: set the dimensions requested in a new board and starts a new game with the new dimensions
    private void pressedSetOkButton() throws IOException, InterruptedException {
        int rows = (int) rowSelect.getValue();
        int col = (int) colSelect.getValue();

        engine.getGame().incomplete();

        Board board = new Board();
        board.setWidth(col);
        board.setHeight(rows);
        board.generateLayout();
        engine.newGame(new Game(board));

    }

    // private class used to create JSpinner instances with a set minimum number and size
    private static class Selector extends JSpinner {

        public Selector() {
            super(new SpinnerNumberModel());
            SpinnerModel model = getModel();
            if (model instanceof SpinnerNumberModel) {
                SpinnerNumberModel numModel = (SpinnerNumberModel) model;
                numModel.setMinimum(1);
            }
            setPreferredSize(new Dimension(80,25));

        }
    }
}

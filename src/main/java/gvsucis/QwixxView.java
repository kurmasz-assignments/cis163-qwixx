/////////////////////////////////////////////////////////////////////////////////////
//
// QwixxView.java
//
// The view for a game of Qwixx (https://gamewright.com/product/Qwixx)
//
// Starter code (c) 2020 Zachary Kurmas
//
// Completed by:
//
///////////////////////////////////////////////////////////////////////////////////

package gvsucis;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.ActionListener;
import java.util.function.BiConsumer;

public class QwixxView {

  private static final Color[] rowColors = { Color.red, Color.yellow, Color.green, new Color(49, 140, 231) };
  private static final Border blackBorder = BorderFactory.createLineBorder(Color.black);

  private ReadOnlyQwixxModel model;

  private JFrame frame;

  private JButton rollButton;
  private JButton passWhite;
  private JButton passColor;

  private JButton[][] numberLabels;
  private JLabel[] diceLabels;
  private JLabel scoreLabels[];
  private JLabel penaltyLabels[];
  private JLabel statusMessageLabel;

  /**
   * The user interface (i.e., "View") for a Qwixx game.
   * 
   * (By using a read-only interface, the compiler will complain if the programmer
   * attempts to modify the model directly from the view, instead of going through
   * the controller.)
   * 
   * @param model a read-only view of the game model.
   * 
   */
  public QwixxView(ReadOnlyQwixxModel model) {

    this.model = model;

    //
    // Number Panel
    //
    int USE_LOCK = 0; // Whether to include the "lock" column that is used in two-player games.
    // 

    JPanel numberPanel = new JPanel();
    numberPanel.setLayout(new GridLayout(model.numRows(), model.numColumns() + USE_LOCK, 5, 5));

    numberLabels = new JButton[model.numRows()][model.numColumns() + USE_LOCK];
    for (int row = 0; row < model.numRows(); row++) {
      for (int col = 0; col < model.numColumns(); col++) {
        JButton l = new JButton("");
        l.setBackground(rowColors[row]);
        l.setOpaque(true);
        l.setBorder(blackBorder);
        numberPanel.add(l);
        numberLabels[row][col] = l;
      }

      // 

      numberPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
    }

    //
    // Dice Panel
    //
    JPanel dicePanel = new JPanel();

    diceLabels = new JLabel[model.diceValues().length];
    dicePanel.setLayout(new FlowLayout());
    for (int i = 0; i < diceLabels.length; i++) {
      diceLabels[i] = new JLabel(model.diceValues()[i], SwingConstants.CENTER);
      JLabel l = diceLabels[i];
      l.setMinimumSize(new Dimension(25, 25));
      l.setPreferredSize(new Dimension(25, 25));
      l.setMaximumSize(new Dimension(25, 25));

      if (i >= 2) {
        l.setBackground(rowColors[i - 2]);
        l.setOpaque(true);
      }
      l.setBorder(blackBorder);
      dicePanel.add(l);
    }

    JLabel p = new JLabel("Penalties: ");
    p.setBorder(new EmptyBorder(0, 10, 0, 0));
    dicePanel.add(p);

    penaltyLabels = new JLabel[model.maxPenalties()];

    for (int i = 0; i < model.maxPenalties(); ++i) {
      JLabel l = new JLabel(" ", SwingConstants.CENTER);
      l.setBorder(blackBorder);
      l.setMinimumSize(new Dimension(25, 25));
      l.setPreferredSize(new Dimension(25, 25));
      l.setMaximumSize(new Dimension(25, 25));
      dicePanel.add(l);
      penaltyLabels[i] = l;
    }

    //
    // Score Panel
    //
    JPanel scorePanel = new JPanel();
    scorePanel.setLayout(new FlowLayout());
    scoreLabels = new JLabel[model.numRows() + 2];
    for (int i = 0; i < model.numRows(); ++i) {
      JLabel l = new JLabel("0", SwingConstants.CENTER);
      scoreLabels[i] = l;
      l.setMinimumSize(new Dimension(40, 25));
      l.setPreferredSize(new Dimension(40, 25));
      l.setMaximumSize(new Dimension(40, 25));
      l.setBackground(rowColors[i]);
      l.setOpaque(true);
      l.setBorder(blackBorder);
      scorePanel.add(l);

      String symbol = i == model.numRows() - 1 ? "-" : "+";
      scorePanel.add(new JLabel(symbol));
    }

    JLabel minusLabel = new JLabel("0", SwingConstants.CENTER);
    minusLabel.setMinimumSize(new Dimension(40, 25));
    minusLabel.setPreferredSize(new Dimension(40, 25));
    minusLabel.setMaximumSize(new Dimension(40, 25));
    minusLabel.setBackground(Color.white);
    minusLabel.setOpaque(true);
    minusLabel.setBorder(blackBorder);
    scorePanel.add(minusLabel);
    scoreLabels[model.numRows()] = minusLabel;

    scorePanel.add(new JLabel("="));

    JLabel totalLabel = new JLabel("0", SwingConstants.CENTER);
    totalLabel.setMinimumSize(new Dimension(40, 25));
    totalLabel.setPreferredSize(new Dimension(40, 25));
    totalLabel.setMaximumSize(new Dimension(40, 25));
    totalLabel.setBackground(Color.white);
    totalLabel.setOpaque(true);
    totalLabel.setBorder(blackBorder);
    scorePanel.add(totalLabel);
    scoreLabels[model.numRows() + 1] = totalLabel;

    scorePanel.setBorder(new EmptyBorder(5, 5, 5, 5));

    //
    // Button Panel
    //
    JPanel buttonPanel = new JPanel();
    rollButton = new JButton("Roll");
    passWhite = new JButton("Pass White");
    passColor = new JButton("Pass Color");
    buttonPanel.add(rollButton);
    buttonPanel.add(passWhite);
    buttonPanel.add(passColor);

    // Status panel
    JPanel statusMessagePanel = new JPanel();
    statusMessageLabel = new JLabel("Begin!");
    statusMessagePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    statusMessagePanel.add(statusMessageLabel);

    //
    // Main panel
    //
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.add(numberPanel);
    panel.add(dicePanel);
    panel.add(scorePanel);
    panel.add(buttonPanel);
    panel.add(statusMessagePanel);

    //
    // Frame
    //
    frame = new JFrame("Qwixx");
    frame.add(panel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.update();
    frame.pack();
    frame.setVisible(true);
  }

  /**
   * Add the given listener to the roll button.
   * 
   * @param listener a click listener
   */
  public void addRollButtonListener(ActionListener listener) {
    rollButton.addActionListener(listener);
  }

  /**
   * Add the given listener to the "pass white" button
   * 
   * @param listener a click listener
   */
  public void addWhiteButtonListener(ActionListener l) {
    passWhite.addActionListener(l);
  }

  /**
   * Add the given listener to the "pass color" button
   * 
   * @param listener a click listener
   */
  public void addColorButtonListener(ActionListener l) {
    passColor.addActionListener(l);
  }

  /**
   * Adds a listener to each number box. When any box is clicked, the listener
   * will invoke the given lambda with the coordinates (row, column) of the box
   * that was clicked.
   * 
   * @param lambda the code invoked when the box is clicked.
   */
  public void addNumberButtonListener(BiConsumer<Integer, Integer> lambda) {
    for (int row = 0; row < model.numRows(); ++row) {
      for (int col = 0; col < model.numColumns(); ++col) {

        // externally declared variables used in lambdas must be constant.
        // Therefore, we create constant copies of row and column so they can be
        // referenced in the lambda below.
        final int localRow = row;
        final int localColumn = col;
        numberLabels[row][col].addActionListener(e -> {
          System.out.printf("In view.  Calling lambda with (%d, %d)\n", localRow, localColumn);
          lambda.accept(localRow, localColumn);
        });
      }
    }
  }

  /**
   * Update the view based on the current state of the model.
   */
  public void update() {
    
    // update the dice values.
    for (int i = 0; i < model.diceValues().length; ++i) {
      diceLabels[i].setText(model.diceValues()[i]);
    }

    // update the number boxes
    for (int r = 0; r < model.numberValues().length; ++r) {
      for (int c = 0; c < model.numberValues()[r].length; ++c) {
        numberLabels[r][c].setText(model.numberValues()[r][c]);
      }
    }

    // update the scores
    for (int i = 0; i < model.scoreValues().length; ++i) {
      scoreLabels[i].setText(model.scoreValues()[i]);
    }

    // update the penalties
    for (int i = 0; i < model.timesPassed(); ++i) {
      penaltyLabels[i].setText("-5");
    }

    // enable/disable the buttons based on the current state of the game.
    rollButton.setEnabled(model.canRoll());
    passWhite.setEnabled(model.canPassWhite());
    passColor.setEnabled(model.canPassColor());

    // update the status message
    statusMessageLabel.setText(model.statusMessage());
  }

  /**
   * Display a modal dialog box with an error message.
   * 
   * @param string The error message.
   */
  public void displayError(String string) {
    JOptionPane.showMessageDialog(frame, string, "You can't do that!", JOptionPane.ERROR_MESSAGE);
  }

 // 

}

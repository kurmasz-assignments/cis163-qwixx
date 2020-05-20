/////////////////////////////////////////////////////////////////////////////////////
//
// QwixxModel.java
//
// The model for a game of Qwixx (https://gamewright.com/product/Qwixx)
//
// Starter code (c) 2020 Zachary Kurmas
//
// Completed by:
//
///////////////////////////////////////////////////////////////////////////////////

package gvsucis;

import java.util.Arrays;
import java.util.Random;

/**
 * The game logic for Qwixx
 */
public class QwixxModel implements ReadOnlyQwixxModel {

  private final int MAX_PENALTIES = 4;
  private final int MIN_COLUMN = 2;

  public enum StatusCode {
    VALID, // valid move (i.e., can select that number box)
    DICE_DONT_MATCH, // Can't select this number box because the die values don't sum correctly.
    EXCLUDED, // Can't select this number box because you've already selected a box to the
              // right.
    NEED5, // Can't select this 12 or 2 because you don't have 10 boxes selected yet. (This
           // only applies to the two-player version.  It's not used in the base project.)
    MUST_ROLL // You can't select any number boxes because you haven't rolled the dice.
  }

  private int numRows;
  private int numCols;
  private int numWhiteDice;

  // Note: You will need additional instance variables.

  // 

  /**
   * Constructor
   * 
   * @param numRows      the number of rows (a standard game uses 4)
   * @param maxCol       the number of numbered columns (11 for a standard game)
   * @param numWhiteDice the number of white dice used (2 for a standard game)
   */
  QwixxModel(int numRows, int numCols, int numWhiteDice) {

    this.numRows = numRows;
    this.numCols = numCols;
    this.numWhiteDice = numWhiteDice;

    // 
  }

  /**
   * Constructor
   */
  QwixxModel() {
    this(4, 11, 2);
  }

  // from ReadOnlyQwixxModel
  public int numRows() {
    return numRows;
  }

  // from ReadOnlyQwixxModel
  public int numColumns() {
    return numCols;
  }

  // from ReadOnlyQwixxModel
  public int minColumn() {
    return MIN_COLUMN;
  }

  // from ReadOnlyQwixxModel
  public int numWhiteDice() {
    return numWhiteDice;
  }

  // from ReadOnlyQwixxModel
  public int timesPassed() {

    int timesPassed = 0;

    // 

    return timesPassed;
  }

  // from ReadOnlyQwixxModel
  public int maxPenalties() {
    return MAX_PENALTIES;
  }

  // from ReadOnlyQwixxModel
  public String[] diceValues() {

    // Remember: The instance variable that contains the dice values
    // should have type int. (Don't make it a String just to simplify this method.)

    String[] answer = { "0", "0", "0", "0", "0", "0" };

    // 

    return answer;

  }

  // from ReadOnlyQwixxModel
  public String[][] numberValues() {

    // Remember: This method must be read-only. Don't return an instance variable.
    // Doing so will allow the view to modify the model.

    // Just a dummy set of values to get you started.
    String[][] answer = { { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k" },
        { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k" },
        { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k" },
        { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k" } };

    // 

    return answer;
  }

  // From ReadOnlyQwixxModel
  public String[] scoreValues() {

    // Remember: The instance variable that contains the score values
    // should have type int. (Don't make it a String just to simplify this method.)

    // This is just dummy data so the starter code will run.
    // Remove this.
    String[] answer = { "8", "6", "7", "5", "3", "fred" };

    // 

    return answer;
  }

  // from ReadOnlyQwixxModel
  public boolean canRoll() {
    boolean answer = true;

    // 

    return answer;
  }

  // from ReadOnlyQwixxModel
  public boolean canSelect() {

    boolean answer = true;

    // 

    return answer;
  }

  // from ReadOnlyQwixxModel
  public boolean canPassWhite() {

    boolean answer = true;

    // 

    return answer;
  }

  // from ReadOnlyQwixxModel
  public boolean canPassColor() {

    boolean answer = true;

    // 

    return answer;
  }

  // from ReadOnlyQwixxModel
  public boolean gameOver() {
    boolean answer = false;

    // 

    return answer;
  }

  // from ReadOnlyQwixxModel
  public String statusMessage() {

    String status = "The Status";

    // 

    return status;
  }

  /**
   * "Roll" the dice by assigning the dice to a set of given values. This method
   * is used by unit tests (so they know what to expect).
   * 
   * @param values the array of values for each die in this order: White, White,
   *               Red, Yellow, Green, Blue
   */
  public void rollDice(int[] values) {
    // IMPORTANT: My tests will only work if you set the dice correctly. See the
    // comment above for the proper order.

    // 
  }

  /**
   * Randomly roll the dice
   */
  public void rollDice() {
    // 
  }

  /**
   * Called when a user selects one of the numbers in the grid.
   * 
   * (You may assume that this method is only called when canSelect returns true)
   * 
   * @param row the number of the row selected
   * @param col the index of the column selected. Note: This is the *position* of
   *            the column, *not* the number that appears int the box.
   * @return a {@code StatusCode} indicating the result of the attempt.
   */
  public StatusCode numberSelected(int row, int col) {

    StatusCode status = StatusCode.VALID;

    // 

    return status;
  }

  // 

  /**
   * Called when the user elects not to select a number box based on the white
   * dice. (You may assume that this method is only called when canPassWhite
   * returns true)
   */
  public void passWhite() {
    // 
  }

  /**
   * Called when the user elects not to select a number box based on the colored
   * dice. (You may assume that this method is only called when canPassColor
   * returns true)
   */
  public void passColor() {
    // 
  }

}

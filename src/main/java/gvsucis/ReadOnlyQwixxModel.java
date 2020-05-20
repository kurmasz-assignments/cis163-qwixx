/////////////////////////////////////////////////////////////////////////////////////
//
// ReadOnlyQwixxModel.java
//
// The model for a game of Qwixx (https://gamewright.com/product/Qwixx)
//
// Starter code (c) 2020 Zachary Kurmas
//
// Completed by:
//
///////////////////////////////////////////////////////////////////////////////////

package gvsucis;

/**
 * A read-only view of a Qwixx game. Users of this interface can query the state
 * of a Qwixx game; but, cannot modify the state of the game.
 * 
 * (The idea is for QwixxView to have an instance variable of this type rather
 * than a QwixxModel variable. That way, the compiler will complain if the
 * programmer attempts to modify the model directly from the view.)
 */
public interface ReadOnlyQwixxModel {

  /**
   * The number of rows (4 for a standard game)
   * 
   * @return the number of rows
   */
  public int numRows();

  /**
   * The number of numbered columns (11 for a standard game). (Does not count the
   * "Lock" column)
   * 
   * @return number of numbered column
   */
  public int numColumns();

  /**
   * The number of the minimum column. (2 for a standard game)
   * 
   * @return
   */
  public int minColumn();

  /**
   * The number of white dice (2 for a standard game)
   * 
   * @return the number of white dice
   */
  public int numWhiteDice();

  /**
   * The maximum number of penalties a player may take before the game ends (4 for
   * a standard game)
   * 
   * @return the maximum number of penalties a player may take
   */
  public int maxPenalties();

  /**
   * Whether the player is allowed to roll the dice.
   * 
   * @return {@code true} if the player may roll the dice.
   */
  public boolean canRoll();

  /**
   * Whether the player is allowed to select a number to cross out.
   * 
   * @return {@code true} if the player is allowed to select a number.
   */
  public boolean canSelect();

  /**
   * Whether the player is allowed to pass on selecting a number based on the
   * white dice.
   * 
   * @return {@code true} if the player may pass the selection of the white dice.
   */
  public boolean canPassWhite();

  /**
   * Whether the player is allowed to pass on selecting a number based on the
   * colored dice. (Doing so always results in a penalty.)
   * 
   * @return {@code true} if the player may pass the selection based on the
   *         colored dice.
   */
  public boolean canPassColor();

  /**
   * Whether the game has ended.
   * 
   * @return {@code true} when the game has ended.
   */
  public boolean gameOver();

  /**
   * The status message
   * 
   * @return the status message
   */
  public String statusMessage();

  /**
   * The current values of the dice
   * 
   * @return The current values of the dice
   */
  public String[] diceValues();

  /**
   * A view of the grid of numbers. Numbers that have been crossed out have a
   * value of "X". Make sure that (1) the 2D array you return is the size of the
   * board. Extra rows and/or columns will cause my tests to fail. (2) You use an
   * "X" to mark numbers that have been used. Using a different character will
   * cause my tests to fail.
   * 
   * @return a view of the currently visible numbers.
   */
  public String[][] numberValues();

  /**
   * A view of the current score. The first four values in the array contain the
   * score for each corresponding color row. The next value is the total penalty
   * (-5 x penalties taken). The final value is the total score.
   * 
   * @return a view of the current score.
   */
  public String[] scoreValues();

  /**
   * The number of times the player has elected to take a penalty and not cross
   * out a number based on the colored dice.
   * 
   * @return the number of penalties the player has taken.
   */
  public int timesPassed();

}

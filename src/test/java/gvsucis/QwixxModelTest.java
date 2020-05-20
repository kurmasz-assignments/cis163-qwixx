package gvsucis;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

public class QwixxModelTest {

  // Create a default object to test.
  // (This saves the trouble of creating it in each method.)
  private QwixxModel model;
  private ReadOnlyQwixxModel imodel;

  // The view of the board should place an "X" in squares that are marked off.
  private static String MARK = "X";

  // Use @BeforeEach to make sure you have a new, "clean"
  // object for each test.
  @BeforeEach
  public void before() {
    model = new QwixxModel();
    imodel = model;
  }

  // helper methods like this can make tests easier to write.
  public static String[] rollDiceHelper(QwixxModel model, int... values) {
    if (values.length != 6) {
      throw new IllegalArgumentException("Need six values");
    }

    model.rollDice(values);

    // Use Java streams to generate the expected output.
    return java.util.Arrays.stream(values).mapToObj(i -> Integer.toString(i)).toArray(String[]::new);
  }

  // convert the number you see in the board to the array index.
  // (Assumes a standard board)
  public static int colInMatrix(int row, int col) {
    return row < 2 ? col - 2 : (12 - col);
  }

  @Test
  @DisplayName("#default constructor")
  public void testDefaultConstructor() {
    QwixxModel m = new QwixxModel();
    assertEquals(4, m.numRows());
    assertEquals(11, m.numColumns());
    assertEquals(2, m.numWhiteDice());

    assertEquals(2, m.minColumn());
    assertEquals(4, m.maxPenalties());
  }

  @Test
  @DisplayName("#constructor score is initially zero")
  public void scoreInitiallyZero() {
    assertArrayEquals(new String[] { "0", "0", "0", "0", "0", "0" }, imodel.scoreValues());
  }

  @Test
  @DisplayName("#constructor game is not initially over")
  public void gameNotInitiallyOver() {
    assertFalse(imodel.gameOver(), "Game should not begin over");
  }

  @Test
  @DisplayName("#constructor can initially roll")
  public void canInitiallyRoll() {
    assertTrue(imodel.canRoll(), "Should be able to roll as soon as game begins.");
  }

  // ------------------------------------------------------
  //
  // Test interface
  //
  // * Make sure view arrays are the right size
  // * Make sure interface can't modify model
  //
  // ------------------------------------------------------

  @Test
  @DisplayName("number values array is the correct size")
  public void numberValuesArrayIsCorrectSize() {
    assertEquals(imodel.numRows(), imodel.numberValues().length);
    for (String[] row : imodel.numberValues()) {
      // Note: may fail if "Lock" included
      assertEquals(imodel.numColumns(), row.length);
    }
  }

  @Test
  @DisplayName("dice values array is the correct size")
  public void diceValuesArrayIsCorrectSize() {
    assertEquals(imodel.numRows() + imodel.numWhiteDice(), model.diceValues().length);
  }

  @Test
  @DisplayName("Changing numberValues doesn't change model")
  public void changingNumberViewDoesNotChangeModel() {
    String[][] numberValues = imodel.numberValues();
    String[][] numberValues1 = new String[numberValues.length][numberValues[0].length];
    for (int row = 0; row < numberValues1.length; ++row) {
      for (int col = 0; col < numberValues1[row].length; ++col) {
        numberValues1[row][col] = new String(numberValues[row][col]);
      }
    }
    numberValues[0][0] = "some unlikely random string 298742398472";

    numberValues[numberValues.length / 2][0] = "another unlikely random string 348734978393";

    int lastRow = numberValues.length - 1;
    numberValues[lastRow][numberValues[lastRow].length - 1] = "a third unlikely random string 983847334";

    String[][] numberValues2 = imodel.numberValues();
    assertArrayEquals(numberValues1, numberValues2);
  }

  @Test
  @DisplayName("Changing diceValues doesn't change model")
  public void changingDiceValuesDoesNotChangeModel() {
    String[] diceValues = imodel.diceValues();
    String[] diceValues1 = new String[diceValues.length];
    for (int i = 0; i < diceValues.length; ++i) {
      diceValues1[i] = new String(diceValues[i]);
      diceValues[i] = diceValues[i] + " ** " + diceValues[i];
    }
    assertArrayEquals(diceValues1, imodel.diceValues());
  }

  @Test
  @DisplayName("Changing scoreValues doesn't change model")
  public void changingScoreValuesDoesNotChangeModel() {
    String[] scoreValues = imodel.scoreValues();
    String[] scoreValues1 = new String[scoreValues.length];
    for (int i = 0; i < scoreValues.length; ++i) {
      scoreValues1[i] = new String(scoreValues[i]);
      scoreValues[i] = scoreValues[i] + " ** " + scoreValues[i];
    }
    assertArrayEquals(scoreValues1, imodel.diceValues());
  }

  // ------------------------------------------------------
  //
  // Rolling Dice
  //
  // ------------------------------------------------------

  @Test
  @DisplayName("#rollDice sets dice (1)")
  public void rollDiceSetsDice1() {
    String[] expected = rollDiceHelper(model, 2, 3, 4, 2, 6, 1);
    assertArrayEquals(expected, imodel.diceValues());
  }

  // ------------------------------------------------------
  //
  // Selecting White
  //
  // ------------------------------------------------------

  @ParameterizedTest
  @DisplayName("#numberSelected white, initial roll, valid (1)")
  @ValueSource(ints = { 0, 1, 2, 3 })
  public void selectFromWhiteDice_a(int row) {
    rollDiceHelper(model, 1, 2, 4, 5, 4, 5);

    int columnToSelect = colInMatrix(row, 3);

    // Correct return value
    assertEquals(QwixxModel.StatusCode.VALID, model.numberSelected(row, columnToSelect), "Failed for Row = " + row);

    // Board updated
    // (Think about how to abstract this check into a helper method.)
    String[][] boardView = imodel.numberValues();
    for (int r = 0; r < boardView.length; ++r) {
      for (int c = 0; c < boardView[r].length; ++c) {

        if (r == row && c == columnToSelect) {
          String message = String.format("Expected an 'X' at [%d, %d]", r, c);
          assertEquals(MARK, boardView[r][c], message);
        } else {
          String message = String.format("Unexpected '%s' found at [%d, %d]", MARK, r, c);
          assertNotEquals(MARK, boardView[r][c], message);
        }
      }
    }

    // Score updated
    String[] expectedScore = { "0", "0", "0", "0", "0", "1" };
    expectedScore[row] = "1";

    assertArrayEquals(expectedScore, imodel.scoreValues());
  }

}
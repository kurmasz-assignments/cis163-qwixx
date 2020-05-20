/////////////////////////////////////////////////////////////////////////////////////
//
// QwixxController.java
//
// The controller for a game of Qwixx (https://gamewright.com/product/Qwixx)
//
// Starter code (c) 2020 Zachary Kurmas
//
// Completed by:
//
///////////////////////////////////////////////////////////////////////////////////

package gvsucis;

public class QwixxController {

  public QwixxController(QwixxView view, QwixxModel model) {

    // Lambdas were introduced in Java 8. They provide a mechanism for passing code
    // as a parameter to a method.
    //
    // Before Java 8, the only way to "pass code as a parameter" was to write a
    // method in a class, then pass an instance of that class as a parameter.
    //
    // This demonstrates one way programmers using older versions of Java could
    // specify the code that should run when a button was clicked.
    //
    // (You can delete this once you understand how it works.)
    view.addRollButtonListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent e) {
        System.out.println("Pre-lambda callback for roll button.");
      }
    });

    // Now that Java supports lambdas, we can pass the code to run directly as a
    // parameter: No need to explicitly bundle it inside an object.
    view.addRollButtonListener(e -> {
      System.out.println("Roll button clicked."); // delete this once you understand how it works.
      model.rollDice();
      view.update();
    });

    view.addWhiteButtonListener(e -> {
      System.out.println("'Pass white dice' button clicked."); // delete this once you understand how it works.
      model.passWhite();
      view.update();
    });

    view.addColorButtonListener(e -> {
      System.out.println("'Pass colored dice' button clicked."); // delete this once you understand how it works.
      model.passColor();
      view.update();
    });

    view.addNumberButtonListener((row, col) -> {
      System.out.printf("Number box (%d, %d) clicked.\n", row, col);

      // TODO: Call the necessary QwixxModel methods.

      // 
      view.update();
    });
  }

  public static void main(String[] args) {

    QwixxModel model = new QwixxModel();
    QwixxView view = new QwixxView(model);
    view.update();

    new QwixxController(view, model);
  }
}

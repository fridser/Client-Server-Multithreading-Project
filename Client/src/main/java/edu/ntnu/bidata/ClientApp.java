package edu.ntnu.bidata;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Responsible for the graphical user interface of the client application. The view contains buttons
 * which control the calculator.
 */
public class ClientApp extends Application {
  @Override
  public void start(Stage stage) throws Exception {
    BorderPane rootNode = new BorderPane();
    // VBox for separating the sections of the calculator.
    VBox vbox = new VBox();
    rootNode.setCenter(vbox);

    // Add the number buttons grid to the vbox.
    vbox.getChildren().add(createNumberButtons());

    Scene scene = new Scene(rootNode, 320, 600);
    stage.setScene(scene);
    stage.setTitle("Calculator Client");
    stage.show();
  }

  /**
   * Create a grid of numbered buttons for the calculator.
   *
   * @return a GridPane containing the buttons for the calculator.
   */
  public GridPane createNumberButtons() {
    GridPane grid = new GridPane();
    for (int i = 1; i <= 9; i++) {
      Button button = new Button(Integer.toString(i));
      grid.add(button, (i - 1) % 3, (i - 1) / 3);
    }
    return grid;
  }

  /**
   * Launch the JavaFX application. This method is called from the main method in ClientMain.
   * The main method is used to start the application, and this method is used to launch the JavaFX application.
   *
   * @param args the command line arguments passed to the application.
   */
  public static void appMain(String[] args) {
    launch(args);
  }
}

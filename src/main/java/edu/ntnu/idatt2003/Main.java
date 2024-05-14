package edu.ntnu.idatt2003;

import edu.ntnu.idatt2003.controller.MainPageController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The main class for the ChaosGame application.
 * This class is responsible for starting the application
 * and initializing the main view.
 */
public class Main extends Application {

  /**
   * The main method is the entry point for the ChaosGame application.
   * The method launches the application.
   *
   * @param args Command-line arguments.
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * The starts the application, by creating a MainPageController, and sets the Scene
   * to its view.
   *
   * @param primaryStage The primary stage for this application,
   *                     onto which the application scene can be set.
   */
  @Override
  public void start(Stage primaryStage) {
    primaryStage.setScene(new MainPageController().getView());
    primaryStage.show();
  }
}
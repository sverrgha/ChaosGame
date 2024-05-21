package edu.ntnu.idatt2003.view;

import static edu.ntnu.idatt2003.view.TextRenderer.READ_FILE;
import static edu.ntnu.idatt2003.view.TextRenderer.EXIT;
import static edu.ntnu.idatt2003.view.TextRenderer.RUN_ITERATIONS;
import static edu.ntnu.idatt2003.view.TextRenderer.SHOW_CANVAS;
import static edu.ntnu.idatt2003.view.TextRenderer.WRITE_FILE;

import edu.ntnu.idatt2003.model.ChaosGame;
import edu.ntnu.idatt2003.model.ChaosGameDescription;
import edu.ntnu.idatt2003.model.ChaosGameFileHandler;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Represents a user interface.
 * Contains methods for starting the UI, reading a description from a file, writing a description to a file,
 * running iterations and showing the canvas.
 * Includes a scanner for user input, and instances of TextRenderer, ChaosGameFileHandler, ChaosGame and ChaosGameDescription.
 * Goal: act as a view for a user interface.
 */

public class UI {
  TextRenderer textRenderer = new TextRenderer();
  ChaosGameDescription description;
  ChaosGame chaosGame;
  private final Scanner scanner = new Scanner(System.in);

  /**
   * Starts the user interface. Asks the user for input and performs the corresponding action.
   * Breaks the loop when the user chooses to exit, which is when the user inputs "5".
   * Invalid input will result in a message.
   */

  public void start() {
    String choice;
    do {
      textRenderer.showMenu();
      choice = getUserChoice();
      switch (choice) {
        case READ_FILE:
          readDescriptionFromFile();
          break;
        case WRITE_FILE:
          writeDescriptionToFile();
          break;
        case RUN_ITERATIONS:
          runIterations();
          break;
        case SHOW_CANVAS:
          showCanvas();
          break;
        default:
          System.out.println("Invalid choice");
          break;
      }
    } while (!choice.equals(EXIT));
  }

  /**
   * Gets the user's choice.
   *
   * @return The user's choice.
   */

  public static String getUserChoice() {
    Scanner scanner = new Scanner(System.in);
    return scanner.nextLine();
  }

  /**
   * Gets the user's input, and checks if it is empty.
   *
   * @return The user's input.
   */

  public String textInput() {
    String text;
    do {
      text = scanner.nextLine();
      if (text.isEmpty()) {
        System.out.println("Input cannot be empty. Please try again.");
      }
    } while (text.isEmpty());
    return text;
  }

  /**
   * Gets the user's input, and checks if it is a number and whether it is empty.
   *
   * @return The user's input.
   */


  public int numberInput() {
    int number;
    while (true) {
      String input = scanner.nextLine();
      if (!input.isEmpty()) {
        try {
          number = Integer.parseInt(input);
          break;
        } catch (NumberFormatException e) {
          System.out.println("Invalid input. Please enter a valid number.");
        }
      } else {
        System.out.println("Input cannot be empty. Please try again.");
      }
    }
    return number;
  }

  /**
   * Reads a description from a file.
   * Asks the user for the path to the file, and reads the already existing description from the file.
   * Sets the description and chaosGame to the new description.
   * If the file is not found, a message will be shown.
   */


  public void readDescriptionFromFile(){
    textRenderer.enterPath();
    String pathToFile = textInput();
    ChaosGameDescription newDescription;
    try {
      newDescription = ChaosGameFileHandler.readFromFile(pathToFile);
      System.out.println("file read successfully");
    } catch (FileNotFoundException e) {
      throw new RuntimeException("File '" + pathToFile + "' not found." + e.getMessage());
    }
    this.description = newDescription;
    chaosGame = new ChaosGame(description, 30, 30);
    System.out.println("description set successfully");
  }

  /**
   * Writes a description to a file.
   * Asks the user for the path to the file, and writes the description to the file.
   * If the file is not found, a message will be shown.
   */

  public void writeDescriptionToFile() {
    textRenderer.enterPath();
    String pathToFile = textInput();
    ChaosGameFileHandler.writeToFile(description, pathToFile);
  }

  /**
   * Runs iterations.
   * Asks the user for the number of steps to run the simulation.
   * Runs the simulation for the specified number of steps.
   */

  public void runIterations() {
    if(chaosGame == null || description == null) {
      System.out.println("description or chaosGame is null");
      return;
    }
    textRenderer.enterSteps();
    int steps = numberInput();
    chaosGame.runStepsAndUpdateTotal(steps);
  }

  /**
   * Shows the canvas, and ensures that chaosGame is not null.
   */

  public void showCanvas() {
    if(chaosGame == null) {
      System.out.println("chaosGame is null");
      return;
    }
    chaosGame.getCanvas().showCanvas();
  }

}

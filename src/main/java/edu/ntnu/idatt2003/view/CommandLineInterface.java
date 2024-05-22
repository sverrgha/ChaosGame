package edu.ntnu.idatt2003.view;

import static edu.ntnu.idatt2003.view.CommandLineMenuRenderer.READ_FILE;
import static edu.ntnu.idatt2003.view.CommandLineMenuRenderer.EXIT;
import static edu.ntnu.idatt2003.view.CommandLineMenuRenderer.RUN_ITERATIONS;
import static edu.ntnu.idatt2003.view.CommandLineMenuRenderer.SHOW_CANVAS;
import static edu.ntnu.idatt2003.view.CommandLineMenuRenderer.WRITE_FILE;
import static edu.ntnu.idatt2003.view.CommandLineMenuRenderer.WRITE_NEW_DESCRIPTION;

import edu.ntnu.idatt2003.model.AffineTransform2D;
import edu.ntnu.idatt2003.model.ChaosGame;
import edu.ntnu.idatt2003.model.ChaosGameDescription;
import edu.ntnu.idatt2003.model.ChaosGameFileHandler;
import edu.ntnu.idatt2003.model.Complex;
import edu.ntnu.idatt2003.model.JuliaTransform;
import edu.ntnu.idatt2003.model.Matrix2x2;
import edu.ntnu.idatt2003.model.Transform2D;
import edu.ntnu.idatt2003.model.Vector2d;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a command-line interface for the ChaosGame application.
 * This class handles user interactions through the console, allowing
 * the user to read and write descriptions from/to files, run iterations,
 * show the canvas, and create new descriptions.
 */
public class CommandLineInterface {
  CommandLineMenuRenderer menuRenderer = new CommandLineMenuRenderer();
  ChaosGameDescription description;
  ChaosGame chaosGame;
  private final Scanner scanner = new Scanner(System.in);

  /**
   * Starts the user interface. Asks the user for input and performs the corresponding action.
   * Breaks the loop when the user chooses to exit, which is when the user inputs "6".
   * Invalid input will result in a message.
   */
  public void start() {
    String choice;
    do {
      menuRenderer.showMenu();
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
        case WRITE_NEW_DESCRIPTION:
          createNewDescription();
          break;
        default:
          System.out.println("Invalid choice");
          break;
      }
    } while (!choice.equals(EXIT));
  }

  /**
   * Gets the user's choice from the console.
   *
   * @return The user's choice as a String.
   */
  public static String getUserChoice() {
    Scanner scanner = new Scanner(System.in);
    return scanner.nextLine();
  }

  /**
   * Gets the user's input from the console, ensuring it is not empty.
   *
   * @return The user's input as a String.
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
   * Gets the user's numeric input from the console, ensuring it is a valid number and not empty.
   *
   * @return The user's input as an int.
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
   * Gets the user's vector input from the console, ensuring it contains two valid numbers.
   *
   * @return The user's input as a Vector2d.
   */
  public Vector2d vectorInput() {
    while (true) {
      String input = scanner.nextLine();
      if (!input.isEmpty()) {
        String[] parts = input.split(" ");
        if (parts.length == 2) {
          try {
            double x = Double.parseDouble(parts[0]);
            double y = Double.parseDouble(parts[1]);
            return new Vector2d(x, y);
          } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter two valid numbers separated by a space.");
          }
        } else {
          System.out.println("Invalid input. Please enter exactly two numbers separated by a space.");
        }
      } else {
        System.out.println("Input cannot be empty. Please try again.");
      }
    }
  }

  public Matrix2x2 matrixInput() {
    while (true) {
      System.out.println("Enter matrix elements (a b c d):");
      String input = scanner.nextLine();
      if (!input.isEmpty()) {
        String[] parts = input.split(" ");
        if (parts.length == 4) {
          try {
            double a = Double.parseDouble(parts[0]);
            double b = Double.parseDouble(parts[1]);
            double c = Double.parseDouble(parts[2]);
            double d = Double.parseDouble(parts[3]);
            return new Matrix2x2(a, b, c, d);
          } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter four valid numbers separated by spaces.");
          }
        } else {
          System.out.println("Invalid input. Please enter exactly four numbers separated by spaces.");
        }
      } else {
        System.out.println("Input cannot be empty. Please try again.");
      }
    }
  }

  /**
   * Reads a ChaosGameDescription from a file specified by the user.
   * Sets the description and initializes the chaosGame with the new description.
   */
  public void readDescriptionFromFile() {
    menuRenderer.enterPath();
    String pathToFile = textInput();
    try {
      ChaosGameDescription newDescription = ChaosGameFileHandler.readFromFile(pathToFile);
      this.description = newDescription;
      this.chaosGame = new ChaosGame(description, 30, 30);
      System.out.println("File read and description set successfully");
    } catch (FileNotFoundException e) {
      System.out.println("File not found: " + pathToFile);
    }
  }

  /**
   * Writes the current ChaosGameDescription to a file specified by the user.
   */
  public void writeDescriptionToFile() {
    menuRenderer.enterPath();
    String pathToFile = textInput();
    ChaosGameFileHandler.writeToFile(description, pathToFile);
  }

  /**
   * Runs iterations for the chaos game. Asks the user for the number of steps to run the simulation.
   */
  public void runIterations() {
    if (chaosGame == null || description == null) {
      System.out.println("Description or chaosGame is null");
      return;
    }
    menuRenderer.enterSteps();
    int steps = numberInput();
    chaosGame.runStepsAndUpdateTotal(steps);
  }

  /**
   * Displays the canvas for the current chaos game.
   */
  public void showCanvas() {
    if (chaosGame == null) {
      System.out.println("ChaosGame is null");
      return;
    }
    chaosGame.getCanvas().showCanvas();
  }

  /**
   * Creates a new ChaosGameDescription based on user input.
   */
  public void createNewDescription() {
    System.out.println("Enter minimum coordinates (x y):");
    Vector2d minCoords = vectorInput();

    System.out.println("Enter maximum coordinates (x y):");
    Vector2d maxCoords = vectorInput();

    List<Transform2D> transformations = new ArrayList<>();
    System.out.println("Enter the number of transformations:");
    int numTransformations = numberInput();

    for (int i = 0; i < numTransformations; i++) {
      System.out.println("Enter transformation type (affine/julia):");
      String type = textInput();
      if (type.equalsIgnoreCase("affine")) {
        Matrix2x2 matrix = matrixInput();
        System.out.println("Enter vector elements (e f):");
        Vector2d vector = vectorInput();
        transformations.add(new AffineTransform2D(matrix, vector));
      } else if (type.equalsIgnoreCase("julia")) {
        System.out.println("Enter real and imaginary parts of the complex number:");
        double real = numberInput();
        double imaginary = numberInput();
        transformations.add(new JuliaTransform(new Complex(real, imaginary), 1));
        transformations.add(new JuliaTransform(new Complex(real, imaginary), -1));
      } else {
        System.out.println("Invalid transformation type. Please enter 'affine' or 'julia'.");
        i--;
      }
    }


    ChaosGameDescription newDescription = new ChaosGameDescription(minCoords, maxCoords, transformations);
    this.description = newDescription;
    this.chaosGame = new ChaosGame(description, 30, 30);
    System.out.println("New description created successfully");
  }

  /**
   * The main method to run the command-line interface.
   *
   * @param args Command line arguments
   */
  public static void main(String[] args) {
    CommandLineInterface cli = new CommandLineInterface();
    cli.start();
  }
}
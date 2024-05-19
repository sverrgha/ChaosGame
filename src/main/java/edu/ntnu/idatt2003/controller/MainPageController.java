package edu.ntnu.idatt2003.controller;

import edu.ntnu.idatt2003.model.ChaosGame;
import edu.ntnu.idatt2003.model.ChaosGameDescription;
import edu.ntnu.idatt2003.model.ChaosGameDescriptionFactory;
import edu.ntnu.idatt2003.model.ChaosGameFileHandler;
import edu.ntnu.idatt2003.model.Complex;
import edu.ntnu.idatt2003.model.JuliaTransform;
import edu.ntnu.idatt2003.model.Transform2D;
import edu.ntnu.idatt2003.model.Vector2d;
import edu.ntnu.idatt2003.view.MainPageView;
import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.InputMismatchException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * The controller class for the main page of the ChaosGame application.
 * This class is responsible for initializing the game and the view,
 * and handling events from the view.
 */
public class MainPageController {
  private final ChaosGame game;
  private final MainPageView view;
  private List<String> customTransformations = new ArrayList<>();
  private static final String TRANSFORMATIONS_PATH = "src/main/resources/transformations/";
  private static final String SERIALIZED_GAME_PATH = "src/main/resources/savedTransformation.ser";
  private static final Logger LOGGER = Logger.getLogger(MainPageController.class.getName());
  private int stepsCounter;

  static {
    try {
      // Ensure the logs directory exists
      new File("logs").mkdirs();

      FileHandler fileHandler = new FileHandler("logs/application.log", false);
      fileHandler.setFormatter(new SimpleFormatter());
      fileHandler.setLevel(Level.WARNING);
      LOGGER.addHandler(fileHandler);
      LOGGER.setLevel(Level.ALL);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * The constructor for the MainPageController class.
   * The constructor initializes the game and the view,
   * and renders the view.
   */
  public MainPageController() {
    this.game = loadGameState();
    this.view = new MainPageView(this);
    this.game.registerObserver(view);
    this.view.render();
    Runtime.getRuntime().addShutdownHook(new Thread(this::saveGameState));
    LOGGER.log(Level.INFO, "MainPageController initialized successfully.");
  }

  /**
   * Get the view of the main page.
   *
   * @return the view of the main page.
   */
  public MainPageView getView() {
    return view;
  }

  /**
   * Get the game of the main page.
   *
   * @return the game of the main page.
   */
  public ChaosGame getGame() {
    return game;
  }

  /**
   * Run the chaos game simulation for the specified number of steps. If
   * the number of steps is negative, the canvas will be cleared.
   *
   * @param steps The number of steps to run the simulation.
   */
  public void runSteps(int steps) {
    game.runSteps(steps);
    stepsCounter += steps;
    LOGGER.log(Level.INFO, "Chaos game simulation ran {0} steps successfully.", steps);
  }

  /**
   * Validates the file that is uploaded., and calls storeFile if the
   * file exists and is formatted correctly.
   *
   * @param file The file to upload.
   * @throws InputMismatchException If the file is not found or the input is invalid.
   */
  public void uploadFile(File file) {
    LOGGER.log(Level.INFO, "Uploading file: {0}", file.getName());
    validateFile(file);
    if (!Files.exists(Path.of(TRANSFORMATIONS_PATH + file.getName()))
            || view.askConfirmation("File already exists. Do you want to overwrite it?")) {
      storeFile(file);
    }

  }

  private void validateFile(File file) {
    try {
      new ChaosGameFileHandler().readFromFile(file);
    } catch (InputMismatchException | FileNotFoundException e) {
      view.showAlert(e.getMessage());
      LOGGER.log(Level.WARNING, "Error uploading file. File was not uploaded.");
    }
  }

  /**
   * Stores the file in the resources/transformations folder of the project.
   *
   * @param file The file to store.
   */
  private void storeFile(File file) {
    try {
      String projectPath = System.getProperty("user.dir");
      String destinationPath = projectPath + File.separator
              + TRANSFORMATIONS_PATH + file.getName();
      Files.copy(file.toPath(), Path.of(destinationPath), StandardCopyOption.REPLACE_EXISTING);
      LOGGER.log(Level.INFO, "File stored successfully in {0}", destinationPath);
    } catch (IOException e) {
      view.showAlert("Error storing file. Please try again.");
      LOGGER.log(Level.WARNING, "Error storing file. File was not stored.");
    }
  }

  /**
   * Change the transformation-type of the chaos game.
   *
   * @param descriptionType The type of fractal description to retrieve.
   */
  public void changeTransformation(ChaosGameDescriptionFactory
                                           .descriptionTypeEnum descriptionType) {
    game.changeTransformation(descriptionType);
    LOGGER.log(Level.INFO, "Transformation was changed successfully to {0}",
        descriptionType);
  }

  private void saveGameState() {
    LOGGER.log(Level.INFO, "Saving game state.");
    game.removeObserver(view);
    try (ObjectOutputStream oos = new ObjectOutputStream(
        new FileOutputStream(SERIALIZED_GAME_PATH))) {
      oos.writeObject(game);
      LOGGER.log(Level.INFO, "Game state saved successfully in {0}", SERIALIZED_GAME_PATH);
    } catch (IOException e) {
      LOGGER.log(Level.WARNING, "Failed to save game state. Next time the application is"
              + " started, the game will be launched in same game state as this time.");
    }
  }

  public ChaosGame loadGameState() {
    LOGGER.log(Level.INFO, "Loading game state.");
    File file = new File(SERIALIZED_GAME_PATH);
    if (file.exists()) {
      try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
        ChaosGame loadedGame = (ChaosGame) ois.readObject();
        LOGGER.log(Level.INFO, "Game state loaded successfully.");
        return loadedGame;
      } catch (IOException | ClassNotFoundException e) {
        LOGGER.log(Level.WARNING, "Failed to load game state. Creating new game.");
      }
    } else {
      LOGGER.log(Level.WARNING, "No saved game state found. Creating new game.");
    }
    return new ChaosGame(ChaosGameDescriptionFactory
            .get(ChaosGameDescriptionFactory.descriptionTypeEnum.SIERPINSKI_TRIANGLE),
            600, 600);
  }

  /**
   * Changes the current custom transformation based on the given custom name.
   *
   * @param customName the name of the custom transformation to be applied
   */

  public void changeCustomTransformation(String customName) {
    ChaosGameDescription chaosGameDescription = customNameHandle(customName);
    game.changeCustomTransformation(chaosGameDescription);
  }

  /**
   * Retrieves the ChaosGameDescription associated with the given custom name.
   *
   * @param customName the name of the custom transformation
   * @return the ChaosGameDescription corresponding to the custom name
   */

  public ChaosGameDescription customNameHandle(String customName) {
    return ChaosGameDescriptionFactory.getCustom(customName);
  }

  /**
   * Adds a new custom transformation with the specified parameters and writes it to a file.
   *
   * @param minCoords the minimum coordinates for the transformation
   * @param maxCoords the maximum coordinates for the transformation
   * @param transform the list of 2D transformations to be applied
   * @param transformationName the name of the custom transformation
   */

  public void addCustomTransformation(Vector2d minCoords, Vector2d maxCoords,
      List<Transform2D> transform, String transformationName) {
    ChaosGameFileHandler chaosGameFileHandler = new ChaosGameFileHandler();
    ChaosGameDescription newChaosGameDescription =
        new ChaosGameDescription(minCoords, maxCoords, transform);
    chaosGameFileHandler
        .writeToFile(newChaosGameDescription, TRANSFORMATIONS_PATH + transformationName + ".txt");
    customTransformations.add(transformationName);
    view.render();
  }

  /**
   * Retrieves the list of custom transformation names.
   *
   * @return the list of custom transformation names
   */

  public List<String> getCustomTransformation() {
    return customTransformations;
  }

  /**
   * Dynamically changes the Julia set transformation based on the provided
   * normalized coordinates.
   *
   * @param x the normalized x-coordinate for the Julia transformation
   * @param y the normalized y-coordinate for the Julia transformation
   */
  public void changeJuliaTransformationDynamic(double x, double y) {
    ChaosGameDescription description = game.getDescription();
    Vector2d max = description.getMaxCoords();
    Vector2d min = description.getMinCoords();
    List<Transform2D> list = new ArrayList<>();
    Complex complex = new Complex(x, y);
    list.add(new JuliaTransform(complex, 1));
    list.add(new JuliaTransform(complex, -1));
    ChaosGameDescription chaosGameDescription = new ChaosGameDescription(min, max, list);
    game.setDescription(chaosGameDescription);
    game.runSteps(stepsCounter);
  }

}




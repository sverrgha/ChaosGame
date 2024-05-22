package edu.ntnu.idatt2003.controller;

import edu.ntnu.idatt2003.model.ChaosGame;
import edu.ntnu.idatt2003.model.ChaosGameDescription;
import edu.ntnu.idatt2003.model.ChaosGameDescriptionFactory;
import edu.ntnu.idatt2003.model.ChaosGameFileHandler;
import edu.ntnu.idatt2003.model.Complex;
import edu.ntnu.idatt2003.model.JuliaTransform;
import edu.ntnu.idatt2003.model.Transform2D;
import edu.ntnu.idatt2003.model.Vector2d;
import edu.ntnu.idatt2003.utils.LoggerUtil;
import edu.ntnu.idatt2003.utils.TransformationParser;
import edu.ntnu.idatt2003.view.MainPageView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * The controller class for the main page of the ChaosGame application.
 * This class is responsible for initializing the game and the view,
 * and handling events from the view.
 */
public class MainPageController {
  private final ChaosGame game;
  private final MainPageView view;
  private final List<String> customFractalNames;
  private boolean addingCustomFractal;
  private static final String FRACTAL_PATH = "src/main/resources/fractals/";
  private static final Logger LOGGER = LoggerUtil.setupLogger(MainPageController.class.getName());


  /**
   * The constructor for the MainPageController class.
   * The constructor initializes the game and the view,
   * and renders the view.
   */
  public MainPageController() {
    this.game = GameStateManager.loadGameState();
    this.view = new MainPageView(this);
    this.game.registerObserver(view);
    this.customFractalNames = new ArrayList<>(getAllCustomFractalsNames());
    this.addingCustomFractal = false;
    this.view.render();
    Runtime.getRuntime().addShutdownHook(new Thread(() -> GameStateManager.saveGameState(game)));
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
   * Get the ChaosGame of the main page.
   *
   * @return the ChaosGame of the main page.
   */
  public ChaosGame getGame() {
    return game;
  }

  /**
   * Get the minimum coordinates of the game.
   *
   * @return the minimum coordinates of the game.
   */
  public double[] getMinCoordsX() {
    return game.getMinCoordsList();
  }

  /**
   * Get the maximum coordinates of the game.
   *
   * @return the maximum coordinates of the game.
   */
  public double[] getMaxCoordsX() {
    return game.getMaxCoordsList();
  }

  /**
   * Get the name of the current fractal.
   *
   * @return the name of the current fractal
   */
  public String getCurrentFractalName() {
    return game.getDescriptionName();
  }

  /**
   * Check if the user is adding a custom fractal.
   *
   * @return true if the user is adding a custom fractal, false otherwise.
   */
  public boolean isAddingCustomFractal() {
    return addingCustomFractal;
  }

  /**
   * Run the chaos game simulation for the specified number of steps. If
   * the number of steps is negative, the canvas will be cleared.
   *
   * @param steps The number of steps to run the simulation.
   */
  public void runSteps(int steps) {
    game.runStepsAndUpdateTotal(steps);
    LOGGER.log(Level.INFO, "Chaos game simulation ran {0} steps successfully.", steps);
  }

  /**
   * Run the chaos game simulation for the specified number of steps. If
   * the number of steps is negative, the canvas will be cleared. If the
   * input cant be converted to an integer, an alert will be shown. And
   * no steps will be run.
   *
   * @param steps The number of steps to run the simulation.
   */
  public void runCustomSteps(String steps) {
    try {
      int stepsInt = Integer.parseInt(steps);
      runSteps(stepsInt);
    } catch (NumberFormatException e) {
      view.showAlert("Invalid input. Please enter a valid integer.");
      LOGGER.log(Level.WARNING, "Invalid input. Chaos game simulation was not run.");
    }
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
    if (ChaosGameFileHandler.validateFile(file)
        && (!Files.exists(Path.of(ChaosGameFileHandler.FRACTAL_PATH + file.getName()))
        || view.askConfirmation("File already exists. Do you want to overwrite it?"))) {
      try {
        ChaosGameFileHandler.storeFile(file);
        LOGGER.log(Level.INFO, "File {0} uploaded successfully.", file.getName());
        view.showAlert("File " + file.getName() + " uploaded successfully.");
      } catch (IOException e) {
        view.showAlert("Error storing file. Please try again.");
        LOGGER.log(Level.WARNING, "Error storing file. File was not stored.");
      }
    }
  }


  /**
   * Change the fractal-type of the chaos game.
   *
   * @param descriptionType The type of fractal description to retrieve.
   */
  public void changeFractal(ChaosGameDescriptionFactory.DescriptionTypeEnum descriptionType) {
    addingCustomFractal = false;
    game.changeFractal(ChaosGameDescriptionFactory.get(descriptionType),
            descriptionType.toString());

    LOGGER.log(Level.INFO, "Fractal was changed successfully to {0}",
            descriptionType);
  }

  /**
   * Changes the current fractal based on the given custom name.
   *
   * @param customName the name of the custom fractal to be applied
   */
  public void changeFractal(String customName) {
    if (customName.equalsIgnoreCase("add new")) {
      addingCustomFractal = true;
      this.view.render();
    } else {
      addingCustomFractal = false;
      try {
        game.changeFractal(ChaosGameDescriptionFactory.getCustom(customName), customName);
        LOGGER.log(Level.INFO, "Fractal was changed successfully to {0}",
                customName);
      } catch (FileNotFoundException e) {
        view.showAlert("File not found. Please try again.");
        LOGGER.log(Level.WARNING, "Error changing fractal. File not found.");
      }
    }
  }

  /**
   * Adds a new custom fractal by creating a ChaosGameDescription based on the
   * parameters which is written to a file in the fractals' directory.
   *
   * @param minCoords       the minimum coordinates for the transformation
   * @param maxCoords       the maximum coordinates for the transformation
   * @param transformations the list of 2D transformations to be applied
   * @param fractalName     the name of the custom fractal
   */

  public void addCustomFractal(String[] minCoords, String[] maxCoords,
                               List<String[]> transformations, String fractalName) {
    try {
      ChaosGameDescription newChaosGameDescription =
              new ChaosGameDescription(
                      TransformationParser.getVector2dFromStringList(minCoords),
                      TransformationParser.getVector2dFromStringList(maxCoords),
                      TransformationParser.getTransformListFromStringList(transformations)
              );
      if (!Files.exists(Path.of(FRACTAL_PATH + fractalName + ".txt"))
              || view.askConfirmation("A custom fractal with the same name already exists. "
              + "Do you want to overwrite it?")) {
        ChaosGameFileHandler.writeToFile(newChaosGameDescription,
                        FRACTAL_PATH + fractalName + ".txt");
        customFractalNames.add(fractalName);
        changeFractal(fractalName);
        view.render();
        view.showAlert("Custom fractal " + fractalName + " added successfully.");
      }

    } catch (IllegalArgumentException e) {
      view.showAlert(e.getMessage());
    }
  }

  /**
   * Saves the current transformation to a file in local directory.
   *
   * @param file the location to save the file.
   */
  public void saveToLocalDirectory(File file) {
    ChaosGameFileHandler.writeToFile(game.getDescription(), file.getAbsolutePath());
    view.showAlert("File saved successfully in " + file.getAbsolutePath());
    LOGGER.log(Level.INFO, "File saved successfully in {0}", file.getAbsolutePath());
  }


  /**
   * Retrieves a list of all custom fractal files in the fractals directory.
   *
   * @return the list of custom fractal file names.
   */
  public List<String> getAllCustomFractalsNames() {
    List<String> transformations = new ArrayList<>();
    Path transformationsPath = Paths.get(FRACTAL_PATH);

    if (Files.exists(transformationsPath) && Files.isDirectory(transformationsPath)) {
      try (Stream<Path> paths = Files.list(transformationsPath)) {
        transformations = paths
                .filter(Files::isRegularFile)
                .map(Path::getFileName)
                .map(Path::toString)
                .map(name -> name.replace(".txt", ""))
                .toList();
        LOGGER.log(Level.INFO, "All custom transformations retrieved successfully.");
      } catch (IOException e) {
        LOGGER.log(Level.WARNING, "Error retrieving custom transformation files.", e);
      }
    } else {
      LOGGER.log(Level.WARNING, "Fractal directory is not a directory.");
    }

    return transformations;
  }

  /**
   * Retrieves the list of custom fractal names.
   *
   * @return the list of custom transformation names
   */

  public List<String> getCustomFractalNames() {
    return customFractalNames;
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
    game.runStepsWithoutUpdatingTotal(game.getTotalSteps());
  }

}




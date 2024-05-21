package edu.ntnu.idatt2003.controller;

import edu.ntnu.idatt2003.model.AffineTransform2D;
import edu.ntnu.idatt2003.model.ChaosGame;
import edu.ntnu.idatt2003.model.ChaosGameDescription;
import edu.ntnu.idatt2003.model.ChaosGameDescriptionFactory;
import edu.ntnu.idatt2003.model.ChaosGameFileHandler;
import edu.ntnu.idatt2003.model.Complex;
import edu.ntnu.idatt2003.model.JuliaTransform;
import edu.ntnu.idatt2003.model.Matrix2x2;
import edu.ntnu.idatt2003.model.Transform2D;
import edu.ntnu.idatt2003.model.Vector2d;
import edu.ntnu.idatt2003.view.MainPageView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.DoubleStream;
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
  private static final String SERIALIZED_GAME_PATH = "src/main/resources/savedGameState.ser";
  private static final Logger LOGGER = Logger.getLogger(MainPageController.class.getName());

  static {
    try {
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
    this.customFractalNames = new ArrayList<>(getAllCustomFractalsNames());
    this.addingCustomFractal = false;
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
   * Get the list of coordinate-arrays of the game.
   *
   * @return the list of coordinate-arrays of the game.
   */
  public List<double[]> getTransformList() {
    if (fractalIsJulia()) {
      return getTransformListJulia();
    } else {
      return getTransformListAffine();
    }
  }

  private List<double[]> getTransformListJulia() {
    List<double[]> transformList = new ArrayList<>();
    transformList.add(((JuliaTransform) game.getTransformList().get(0)).getPointAsList());
    return transformList;
  }

  private List<double[]> getTransformListAffine() {
    List<double[]> transformList = new ArrayList<>();
    for (Transform2D transform : game.getTransformList()) {
      transformList.add(DoubleStream.concat(DoubleStream.of(((AffineTransform2D) transform)
              .getMatrixCoordsList()), DoubleStream.of(((AffineTransform2D) transform)
              .getVectorCoordsList())).toArray());
    }
    return transformList;
  }

  /**
   * Check if the current fractal is a Julia fractal. If it is,
   * return true and false otherwise.
   *
   * @return true if the fractal is a Julia fractal, false otherwise.
   */
  public boolean fractalIsJulia() {
    try {
      return game.getTransformList().get(0) instanceof JuliaTransform;
    } catch (IndexOutOfBoundsException e) {
      return false;
    }
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
    if (validateFile(file)
            && (!Files.exists(Path.of(FRACTAL_PATH + file.getName()))
            || view.askConfirmation("File already exists. Do you want to overwrite it?"))) {
      storeFile(file);
      LOGGER.log(Level.INFO, "File {0} uploaded successfully.", file.getName());
      view.showAlert("File " + file.getName() + " uploaded successfully.");
    }
  }

  private boolean validateFile(File file) {
    try {
      ChaosGameFileHandler.readFromFile(file);
      return true;
    } catch (InputMismatchException | FileNotFoundException e) {
      view.showAlert(e.getMessage());
      LOGGER.log(Level.WARNING, "Error uploading file. File was not uploaded.");
      return false;
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
              + FRACTAL_PATH + file.getName();
      Files.copy(file.toPath(), Path.of(destinationPath), StandardCopyOption.REPLACE_EXISTING);
      LOGGER.log(Level.INFO, "File stored successfully in {0}", destinationPath);
    } catch (IOException e) {
      view.showAlert("Error storing file. Please try again.");
      LOGGER.log(Level.WARNING, "Error storing file. File was not stored.");
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

  /**
   * Load the game state from the serialized file to restore progress.
   * If the file does not exist, a new game state is created.
   *
   * @return The loaded game state, or a new game state if the file does not exist.
   */
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
    ChaosGame newGame = new ChaosGame(ChaosGameDescriptionFactory
            .get(ChaosGameDescriptionFactory.DescriptionTypeEnum.SIERPINSKI_TRIANGLE),
            650, 650);
    newGame.setDescriptionName(ChaosGameDescriptionFactory.DescriptionTypeEnum
            .SIERPINSKI_TRIANGLE.toString());
    return newGame;
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
                      getVector2dFromStringList(minCoords),
                      getVector2dFromStringList(maxCoords),
                      getTransformListFromStringList(transformations)
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
   * Creates a Vector2d object from a string array, containing the x and y coordinates.
   *
   * @param vector the string array containing the x and y coordinates
   * @return the Vector2d object created from the string array
   */
  private Vector2d getVector2dFromStringList(String[] vector) {
    try {
      return new Vector2d(Double.parseDouble(vector[0]), Double.parseDouble(vector[1]));
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid coordinates. Please enter valid integers.");
    }
  }

  /**
   * Creates a list of Transform2D objects from a list of string arrays.
   *
   * @param transform the list of string arrays containing the transformation parameters
   * @return the list of Transform2D objects created from the string arrays
   */
  private List<Transform2D> getTransformListFromStringList(List<String[]> transform)
          throws NumberFormatException {
    try {
      List<Transform2D> transformList = new ArrayList<>();
      for (String[] transformation : transform) {
        if (transformation.length == 2) {
          transformList.addAll(parseJuliaTransform(transformation));
        } else if (transformation.length == 6) {
          transformList.add(parseAffineTransform(transformation));
        }
      }
      return transformList;
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid coordinates. Please "
              + "enter valid decimal numbers.");
    }

  }

  /**
   * Parses the Julia transformations and returns a List of Julia Transformations.
   *
   * @param transformation the string array containing the transformation parameters
   * @return the list of Julia Transformations
   */
  private List<Transform2D> parseJuliaTransform(String[] transformation) {
    return List.of(
            new JuliaTransform(
                    new Complex(
                            Double.parseDouble(transformation[0]),
                            Double.parseDouble(transformation[1])
                    ),
                    1),
            new JuliaTransform(
                    new Complex(
                            Double.parseDouble(transformation[0]),
                            Double.parseDouble(transformation[1])
                    ),
                    1)
    );
  }

  /**
   * Parses the Affine transformations and returns a List of Julia Transformations.
   *
   * @param transformation the string array containing the transformation parameters
   * @return the list of Affine Transformations
   */
  private AffineTransform2D parseAffineTransform(String[] transformation) {
    return new AffineTransform2D(
            new Matrix2x2(
                    Double.parseDouble(transformation[0]),
                    Double.parseDouble(transformation[1]),
                    Double.parseDouble(transformation[2]),
                    Double.parseDouble(transformation[3])
            ),
            new Vector2d(
                    Double.parseDouble(transformation[4]),
                    Double.parseDouble(transformation[5])
            ));

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




package edu.ntnu.idatt2003.controller;

import edu.ntnu.idatt2003.model.ChaosGame;
import edu.ntnu.idatt2003.model.ChaosGameDescription;
import edu.ntnu.idatt2003.model.ChaosGameDescriptionFactory;
import edu.ntnu.idatt2003.model.ChaosGameFileHandler;
import edu.ntnu.idatt2003.model.ChaosGameFileHandler;
import edu.ntnu.idatt2003.model.Transform2D;
import edu.ntnu.idatt2003.model.Vector2d;
import edu.ntnu.idatt2003.view.MainPageView;
import edu.ntnu.idatt2003.view.MainPageView.TransformationType;
import java.util.List;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.InputMismatchException;

/**
 * The controller class for the main page of the ChaosGame application.
 * This class is responsible for initializing the game and the view,
 * and handling events from the view.
 */
public class MainPageController {
  private final ChaosGame game;
  private final MainPageView view;

  private static final String TRANSFORMATIONS_PATH = "src/main/resources/transformations/";

  /**
   * The constructor for the MainPageController class.
   * The constructor initializes the game and the view,
   * and renders the view.
   */
  public MainPageController() {
    this.game = new ChaosGame(ChaosGameDescriptionFactory
            .get(ChaosGameDescriptionFactory.descriptionTypeEnum.SIERPINSKI_TRIANGLE),
            600, 600);
    this.view = new MainPageView(this);
    this.game.registerObserver(view);
    this.view.render();
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
  }

  /**
   * Validates the file that is uploaded., and calls storeFile if the
   * file exists and is formatted correctly.
   *
   * @param file The file to upload.
   * @throws InputMismatchException If the file is not found or the input is invalid.
   */
  public void uploadFile(File file) {
    try {
      validateFile(file);
      if (!Files.exists(Path.of(TRANSFORMATIONS_PATH + file.getName()))
              || view.askConfirmation("File already exists. Do you want to overwrite it?")) {
        storeFile(file);
      }
    } catch (Exception e) {
      view.showAlert(e.getMessage());
    }
  }

  private void validateFile(File file)
          throws InputMismatchException, FileNotFoundException {
    try {
      new ChaosGameFileHandler().readFromFile(file);
    } catch (InputMismatchException e) {
      throw new InputMismatchException(e.getMessage());
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException(e.getMessage());
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
    } catch (IOException e) {
      throw new InputMismatchException("Error copying file: " + e.getMessage());
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
  }


  public void addNewTransformation(Vector2d minCoords, Vector2d maxCoords,
      List<Transform2D> transform, TransformationType transformationType, String TransformationName) {
    ChaosGameFileHandler chaosGameFileHandler = new ChaosGameFileHandler();
    ChaosGameDescription newChaosGameDescription = new ChaosGameDescription(minCoords, maxCoords, transform);

    //switch (transformationType) {
      //case JULIA -> chaosGameFileHandler.writeToFile(newChaosGameDescription, "src/main/resources/" + TransformationName);
      //case AFFINE -> chaosGameFileHandler.writeToFile(newChaosGameDescription, "src/main/resources/" + TransformationName);
    //method must define affine or julia
  }

}




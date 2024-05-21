package edu.ntnu.idatt2003.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a chaos game.
 * Contains a canvas, current point and a description of the game conditions.
 * Includes the method runSteps to repeatedly apply random transformations to the current point.
 * Goal: act as a model for a chaos game.
 */

public class ChaosGame implements Serializable {

  private ChaosCanvas canvas;
  private ChaosGameDescription description;
  private String descriptionName;
  private Vector2d currentPoint;
  private final int width;
  private final int height;
  private final List<ChaosGameObserver> observers;
  private int totalSteps;
  Random randomGenerator;

  /**
   * Constructs a new ChaosGame with specified description and dimensions of the canvas.
   *
   * @param description The description of the chaos game.
   * @param width       The width of the canvas.
   * @param height      The height of the canvas.
   */

  public ChaosGame(ChaosGameDescription description, int width, int height) {
    this.observers = new ArrayList<>();
    this.width = width;
    this.height = height;
    this.descriptionName = "";
    setDescription(description);
    randomGenerator = new Random();
    this.totalSteps = 0;
  }

  /**
   * Returns the canvas of the chaos game.
   *
   * @return The canvas of the chaos game.
   */
  public ChaosCanvas getCanvas() {
    return canvas;
  }

  /**
   * Returns the minimum coordinates of the chaos game.
   *
   * @return The minimum coordinates of the chaos game.
   */
  public double[] getMinCoordsList() {
    return description.getMinCoordsList();
  }

  /**
   * Returns the maximum coordinates of the chaos game.
   *
   * @return The maximum coordinates of the chaos game.
   */
  public double[] getMaxCoordsList() {
    return description.getMaxCoordsList();
  }

  /**
   * Returns the list of transformations of the chaos game.
   *
   * @return The list of transformations of the chaos game.
   */
  public List<Transform2D> getTransformList() {
    return description.getTransform();
  }

  /**
   * Returns the total number of steps run in the chaos game.
   *
   * @return The total number of steps run in the chaos game.
   */
  public int getTotalSteps() {
    return totalSteps;
  }

  public String getDescriptionName() {
    return descriptionName;
  }

  public void setDescriptionName(String descriptionName) {
    this.descriptionName = descriptionName;
  }

  /**
   * Runs the chaos game simulation for the specified number of steps, by
   * calling the runSteps-method and passing true as the second parameter
   * to also update totalSteps.
   *
   * @param steps The number of steps to run the simulation.
   */
  public void runStepsAndUpdateTotal(int steps) {
    runSteps(steps, true);
  }

  /**
   * Runs the chaos game simulation for the specified number of steps, by
   * calling the runSteps-method and passing false as the second parameter
   * to not update totalSteps.
   *
   * @param steps The number of steps to run the simulation.
   */
  public void runStepsWithoutUpdatingTotal(int steps) {
    runSteps(steps, false);
  }

  /**
   * Runs the chaos game simulation for the specified number of steps or cleared
   * if steps is negative. Generates points on the canvas based on random chosen
   * transformation. If addSteps is true, the totalSteps is updated, if false,
   * the totalSteps is not updated. In the end, the observers are notified.
   *
   * @param steps    The number of steps to run the simulation.
   * @param addSteps Whether to update the totalSteps or not.
   */
  private void runSteps(int steps, boolean addSteps) {
    if (steps < 0) {
      this.canvas.clear();
      totalSteps = 0;
    } else {
      for (int i = 0; i < steps; i++) {
        applyRandomTransformation();
      }
      if (addSteps) {
        totalSteps += steps;
      }
    }
    notifyObservers();
  }

  /**
   * Generates a point on the canvas based on a random transformation, and puts
   * the point on the canvas. It also updates the current point to the new point.
   */
  private void applyRandomTransformation() {
    int randomIndex = randomGenerator.nextInt(description.getTransform().size());
    currentPoint = description.getTransform().get(randomIndex).transform(currentPoint);
    canvas.putPixel(currentPoint);
  }

  /**
   * Changes the transformation of the chaos game. Calls the setDescription-method
   * and notifies the observers that it has changed.
   *
   * @param chaosGameDescription The type of fractal description to retrieve.
   */
  public void changeTransformation(ChaosGameDescription chaosGameDescription,
                                   String descriptionName) {
    setDescription(chaosGameDescription);
    setDescriptionName(descriptionName);
    totalSteps = 0;
    notifyObservers();
  }

  /**
   * Sets the description of the chaos game, and creates a new canvas
   * based on the new description.
   *
   * @param description The description of the chaos game.
   */
  public void setDescription(ChaosGameDescription description) {
    this.description = description;
    this.canvas = new ChaosCanvas(width, height,
            description.getMinCoords(), description.getMaxCoords());
    this.currentPoint = new Vector2d(0, 0);
    notifyObservers();
  }

  public ChaosGameDescription getDescription() {
    return description;
  }

  /**
   * Registers an observer to the list of observers.
   *
   * @param observer The observer to be registered.
   */
  public void registerObserver(ChaosGameObserver observer) {
    observers.add(observer);
  }

  /**
   * Removes an observer from the list of observers.
   *
   * @param observer The observer to be removed.
   */
  public void removeObserver(ChaosGameObserver observer) {
    observers.remove(observer);
  }

  /**
   * Notifies all observers, and calls their update-method.
   */
  public void notifyObservers() {
    for (ChaosGameObserver observer : observers) {
      observer.update();
    }
  }
}

package edu.ntnu.idatt2003.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a chaos game.
 * Contains a canvas, current point and a description of the game conditions.
 * Includes the method runSteps to repeatedly apply random transformations to the current point.
 * Goal: act as a model for a chaos game.
 */

public class ChaosGame {

  private ChaosCanvas canvas;
  private ChaosGameDescription description;
  private Vector2d currentPoint;
  private final int width;
  private final int height;
  private final List<ChaosGameObserver> observers;
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
    setDescription(description);
    randomGenerator = new Random();
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
   * Runs the chaos game simulation for the specified number of steps.
   * Generates points on the canvas based on random chosen transformation.
   * The current point is replaced with the new, which gets transformed by a random transformation.
   *
   * @param steps The number of steps to run the simulation.
   */
  public void runSteps(int steps) {
    if (steps < 0) {
      this.canvas.clear();
    } else {
      for (int i = 0; i < steps; i++) {
        applyRandomTransformation();
      }
    }
    notifyObservers();
  }

  private void applyRandomTransformation() {
    int randomIndex = randomGenerator.nextInt(description.getTransform().size());
    currentPoint = description.getTransform().get(randomIndex).transform(currentPoint);
    canvas.putPixel(currentPoint);
  }

  /**
   * Changes the transformation of the chaos game. Calls the setDescription-method
   * and notifies the observers that it has changed.
   *
   * @param descriptionType The type of fractal description to retrieve.
   */
  public void changeTransformation(ChaosGameDescriptionFactory
                                           .descriptionTypeEnum descriptionType) {
    setDescription(ChaosGameDescriptionFactory.get(descriptionType));
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

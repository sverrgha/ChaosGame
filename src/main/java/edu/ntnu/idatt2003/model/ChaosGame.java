package edu.ntnu.idatt2003.model;
import java.util.Random;

/**
 * Represents a chaosgame.
 * Contains a canvas, current point and a description of the game conditions.
 * Includes the method runSteps to repeatedly apply random transformations to the current point.
 * Goal: act as a model for a chaos game.
 *
 */

public class ChaosGame {

  private final ChaosCanvas canvas;
  private final ChaosGameDescription description;
  private Vector2d currentPoint;
  Random random;

  /**
   * Constructs a new ChaosGame with specified description and dimensions of the canvas.
   *
   * @param description The description of the chaos game.
   * @param width The width of the canvas.
   * @param height The height of the canvas.
   */

  public ChaosGame (ChaosGameDescription description, int width, int height) {
    this.description = description;
    this.canvas = new ChaosCanvas(width, height, description.getMinCoords(), description.getMaxCoords());
    this.currentPoint = new Vector2d(0, 0);
    random = new Random();
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
    for (int i = 0; i < steps; i++) {
      int randomIndex = random.nextInt(description.getTransform().size());
      currentPoint = description.getTransform().get(randomIndex).transform(currentPoint);

      canvas.putPixel(currentPoint);

    }
  }


}

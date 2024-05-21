package edu.ntnu.idatt2003.model;

import java.io.Serializable;
import java.util.List;

/**
 * This class represents a chaos game description.
 * It contains the minimum (bottom left) and maximum (top Right) coordinates of
 * the game, and the transformations to be used.
 * Goal: act as a model for a chaos game description.
 */
public class ChaosGameDescription implements Serializable {
  /**
   * The minimum (bottom left) coordinates of the game.
   */
  private final Vector2d minCoords;
  /**
   * The maximum (top right) coordinates of the game.
   */
  private final Vector2d maxCoords;
  /**
   * A list of the transformations to be used.
   */
  private final List<Transform2D> transform;

  /**
   * Constructs a ChaosGameDescription object.
   *
   * @param minCoords The minimum (bottom left) coordinates of the game.
   * @param maxCoords The maximum (top right) coordinates of the game.
   * @param transform A list of the transformations to be used.
   */
  public ChaosGameDescription(Vector2d minCoords, Vector2d maxCoords, List<Transform2D> transform) {
    this.minCoords = minCoords;
    this.maxCoords = maxCoords;
    this.transform = transform;
  }

  /**
   * Get the minimum (bottom left) coordinates of the game.
   *
   * @return the minimum (bottom left) coordinates of the game.
   */
  public Vector2d getMinCoords() {
    return minCoords;
  }

  /**
   * Get the maximum (top right) coordinates of the game.
   *
   * @return the maximum (top right) coordinates of the game.
   */
  public Vector2d getMaxCoords() {
    return maxCoords;
  }

  public double[] getMinCoordsList() {
    return minCoords.getCoordsList();
  }
  public double[] getMaxCoordsList() {
    return maxCoords.getCoordsList();
  }
  /**
   * Get the list of transformations to be used.
   *
   * @return the list of transformations to be used.
   */
  public List<Transform2D> getTransform() {
    return transform;
  }

  @Override
  public String toString() {
    StringBuilder output = new StringBuilder();
    if (transform.get(0) instanceof AffineTransform2D) {
      output.append("Affine2D\n");
    } else {
      output.append("Julia\n");
    }
    output.append(minCoords.toString()).append("\n");
    output.append(maxCoords.toString()).append("\n");
    for (Transform2D t : transform) {
      output.append(t.toString()).append("\n");
    }
    return output.toString();
  }

}

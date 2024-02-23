package edu.ntnu.idatt2003.model;

/**
 * Represents a 2-dimensional vector.
 * Contains a constructor and getters for the x0 and x1 attributes.
 * Includes methods for adding and subtracting vectors.
 * Goal: act as a model for a 2-dimensional vector.
 *
 * @author sverrgha
 */
public class Vector2d {

  /**
   * The first coordinate of the vector.
   */
  private final double x0;

  /**
   * The second coordinate of the vector.
   */
  private final double x1;

  /**
   * Constructs a Vector2d object.
   *
   * @param x0 The first coordinate of the vector.
   * @param x1 The second coordinate of the vector.
   */
  public Vector2d(double x0, double x1) {
    this.x0 = x0;
    this.x1 = x1;
  }

  /**
   * Returns the first coordinate (x0) of the vector.
   *
   * @return The first coordinate (x0) of the vector.
   */
  public double getX0() {
    return x0;
  }

  /**
   * Returns the second coordinate (x1) of the vector.
   *
   * @return The second coordinate (x1) of the vector.
   */
  public double getX1() {
    return x1;
  }

  /**
   * Adds another vector to this vector.
   *
   * @param other The vector to add to this vector.
   * @return A new vector that is the sum of this vector and the other vector.
   */
  public Vector2d add(Vector2d other) {
    return new Vector2d(x0 + other.x0, x1 + other.x1);
  }

  /**
   * Subtracts another vector from this vector.
   *
   * @param other The vector to subtract from this vector.
   * @return A new vector that is the difference between this vector and the other vector.
   */
  public Vector2d subtract(Vector2d other) {
    return new Vector2d(x0 - other.x0, x1 - other.x1);
  }
}

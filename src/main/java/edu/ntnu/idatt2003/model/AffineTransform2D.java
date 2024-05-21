package edu.ntnu.idatt2003.model;

import java.io.Serializable;

/**
 * Represents a 2-dimensional affine transformation.
 * Contains a constructor and a method for transforming a 2-dimensional vector.
 * Goal: act as a model for a 2-dimensional affine transformation.
 * autor: nicklapt
 */

public class AffineTransform2D implements Transform2D, Serializable {

  /**
   * The 2x2 matrix of the affine transformation.
   */

  private final Matrix2x2 matrix;

  /**
   * The 2-dimensional vector of the affine transformation.
   */
  private final Vector2d vector;

  /**
   * Constructs a new AffineTransform2D with specified values for its matrix and vector.
   *
   * @param matrix The 2x2 matrix of the affine transformation.
   * @param vector The 2-dimensional vector of the affine transformation.
   */

  public AffineTransform2D(Matrix2x2 matrix, Vector2d vector) {
    this.matrix = matrix;
    this.vector = vector;
  }

  /**
   * Transforms a 2-dimensional vector using the affine transformation.
   *
   * @param point The 2-dimensional vector to be transformed.
   * @return A new Vector2D representing the result of the transformation.
   */

  @Override
  public Vector2d transform(Vector2d point) {
    return matrix.multiply(point).add(vector);
  }

  public double[] getMatrixCoordsList() {
    return matrix.getCoordsList();
  }
  public double[] getVectorCoordsList() {
    return vector.getCoordsList();
  }
  /**
   * Returns a string representation of the AffineTransform2D, by combining the strings
   * of the matrix and vector separated by a ','.
   *
   * @return A string representation of the AffineTransform2D.
   */
  @Override
  public String toString() {
    return matrix.toString() + ", " + vector.toString();
  }


}

package edu.ntnu.idatt2003.model;

import java.io.Serializable;
import java.util.Locale;

/**
 * A 2x2 matrix representation with basic operations.
 *
 * @author nicklapt
 */
public class Matrix2x2 implements Serializable {

  private final double a00;
  private final double a01;
  private final double a10;
  private final double a11;

  /**
   * Constructs a new Matrix2x2 with specified values for its elements.
   *
   * @param a00 Element at the first row and first column.
   * @param a01 Element at the first row and second column.
   * @param a10 Element at the second row and first column.
   * @param a11 Element at the second row and second column.
   */
  public Matrix2x2(double a00, double a01, double a10, double a11) {
    this.a00 = a00;
    this.a01 = a01;
    this.a10 = a10;
    this.a11 = a11;
  }

  /**
   * Multiplies the Matrix2x2 by a Vector2D.
   *
   * @param vector The Vector2D to be multiplied with the matrix.
   * @return A new Vector2D representing the result of the matrix-vector multiplication.
   */

  public Vector2d multiply(Vector2d vector) {
    double x0 = a00 * vector.getX0() + a01 * vector.getX1();
    double x1 = a10 * vector.getX0() + a11 * vector.getX1();
    return new Vector2d(x0, x1);
  }

  /**
   * Returns a string representation of the Matrix2x2, that separates the
   * coordinates by ', '.
   *
   * @return A string representation of the Matrix2x2.
   */
  @Override
  public String toString() {
    return String.format(Locale.ENGLISH, "%f, %f, %f, %f", a00, a01, a10, a11);
  }
}

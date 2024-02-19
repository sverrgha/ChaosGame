package edu.ntnu.idatt2003;

/**
 * A 2x2 matrix representation with basic operations.
 *
 * @author nicklapt
 *
 */
public class Matrix2x2 {

  private double a00;
  private double a01;
  private double a10;
  private double a11;

  /**
   * Constructs a new Matrix2x2 with specified values for its elements.
   *
   * @param a00 Element at the first row and first column.
   * @param a01 Element at the first row and second column.
   * @param a10 Element at the second row and first column.
   * @param a11 Element at the second row and second column.
   */
  public matrix2x2(double a00, double a01, double a10, double a11){
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

  public multiply(Vector2D vector){
    double x = a00 * vector.getX0() + a01 * vector.getX1();
    double y = a10 * vector.getX0() + a11 * vector.getX1();

    return new Vector2D(x, y);
  }

}

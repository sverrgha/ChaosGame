package edu.ntnu.idatt2003.model;

/**
 * Represents a Julia transformation applied to 2D vectors.
 * Contains a constructor and a method for transforming a 2D vector.
 * Goal: act as a model for a Julia transformation.
 *
 * @author sverrgha
 */
public class JuliaTransform implements Transform2D {

  /**
   * The point to transform the 2D vector by.
   */
  private final Complex point;

  /**
   * The sign of the transformation (+/- 1).
   */
  private final int sign;

  /**
   * Constructs a JuliaTransform object.
   *
   * @param point The point to transform the 2D vector by.
   * @param sign  The sign of the transformation (+/- 1).
   */
  public JuliaTransform(Complex point, int sign) {
    this.point = point;
    this.sign = sign;
  }

  /**
   * Transforms a 2D vector by taking adding/subtracting based on the sign
   * and taking the square root.
   *
   * @param point The 2D vector to transform.
   * @return The transformed 2D vector.
   */
  public Vector2d transform(Vector2d point) {
    Vector2d subtracted = point.subtract(this.point);
    Complex z = new Complex(subtracted.getX0(), subtracted.getX1());
    Complex complex = z.sqrt();
    return new Complex(complex.getX0() * this.sign,
            complex.getX1() * this.sign);
  }

  /**
   * Returns a string representation of the JuliaTransform object, that is equal
   * to the string representation for the transformation's point.
   *
   * @return A string representation of the JuliaTransform object.
   */
  @Override
  public String toString() {
    return point.toString();
  }
}

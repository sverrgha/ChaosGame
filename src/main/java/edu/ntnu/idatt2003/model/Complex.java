package edu.ntnu.idatt2003.model;

/**
 * Represents a subclass that extends the {@link Vector2d}.
 * It adds the additional functionality of finding the square root of a complex number.
 * Goal: act as a model for a complex number.
 *
 * @author svhaa
 */
public class Complex extends Vector2d {
  /**
   * Constructs a Complex object.
   *
   * @param realPart The real part of the complex number.
   * @param imaginaryPart The imaginary part of the complex number.
   */
  public Complex(double realPart, double imaginaryPart) {
    super(realPart, imaginaryPart);
  }

  /**
   * New method introduced in subclass.
   * Computes the square root of a complex number.
   *
   * @return the square root of the complex number.
   */
  public Complex sqrt() {
    double length = Math.sqrt(this.x0 * this.x0 + this.x1 * this.x1);
    double realPart = Math.sqrt((this.x0 + length) / 2);
    double imaginaryPart = Math.signum(this.x1) * Math.sqrt((length - this.x0) / 2);
    return new Complex(realPart, imaginaryPart);
  }
}

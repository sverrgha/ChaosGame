package edu.ntnu.idatt2003.model;

import java.io.Serializable;

/**
 * Represents a subclass that extends the {@link Vector2d}.
 * It adds the additional functionality of finding the square root of a complex number.
 * Goal: act as a model for a complex number.
 *
 * @author svhaa
 */
public class Complex extends Vector2d implements Serializable {
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
    double length = Math.sqrt(getX0() * getX0() + getX1() * getX1());
    double realPart = Math.sqrt((getX0() + length) / 2);
    double imaginaryPart = Math.signum(getX1()) * Math.sqrt((length - getX0()) / 2);
    return new Complex(realPart, imaginaryPart);
  }
}

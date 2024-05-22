package edu.ntnu.idatt2003.utils;

import edu.ntnu.idatt2003.model.AffineTransform2D;
import edu.ntnu.idatt2003.model.ChaosGame;
import edu.ntnu.idatt2003.model.Complex;
import edu.ntnu.idatt2003.model.JuliaTransform;
import edu.ntnu.idatt2003.model.Matrix2x2;
import edu.ntnu.idatt2003.model.Transform2D;
import edu.ntnu.idatt2003.model.Vector2d;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;

/**
 * Utility class for handling transformations in the ChaosGame.
 */
public class TransformationUtil {

  /**
   * Returns the list of coordinate arrays of the game.
   *
   * @param game the ChaosGame instance
   * @return the list of coordinate arrays of the game
   */
  public static List<double[]> getTransformList(ChaosGame game) {
    if (fractalIsJulia(game)) {
      return getTransformListJulia(game);
    } else {
      return getTransformListAffine(game);
    }
  }

  /**
   * Returns the list of coordinate arrays for a Julia fractal.
   *
   * @param game the ChaosGame instance
   * @return the list of coordinate arrays for a Julia fractal
   */
  private static List<double[]> getTransformListJulia(ChaosGame game) {
    List<double[]> transformList = new ArrayList<>();
    transformList.add(((JuliaTransform) game.getTransformList().getFirst()).getPointAsList());
    return transformList;
  }

  /**
   * Returns the list of coordinate arrays for an affine fractal.
   *
   * @param game the ChaosGame instance
   * @return the list of coordinate arrays for an affine fractal
   */
  private static List<double[]> getTransformListAffine(ChaosGame game) {
    List<double[]> transformList = new ArrayList<>();
    for (Transform2D transform : game.getTransformList()) {
      transformList.add(DoubleStream.concat(DoubleStream.of(
              ((AffineTransform2D) transform).getMatrixCoordsList()),
          DoubleStream.of(((AffineTransform2D) transform).getVectorCoordsList())).toArray());
    }
    return transformList;
  }

  /**
   * Checks if the current fractal is a Julia fractal.
   *
   * @param game the ChaosGame instance
   * @return true if the fractal is a Julia fractal, false otherwise
   */
  public static boolean fractalIsJulia(ChaosGame game) {
    try {
      return game.getTransformList().getFirst() instanceof JuliaTransform;
    } catch (IndexOutOfBoundsException e) {
      return false;
    }
  }

  /**
   * Creates a Vector2d object from a string array containing the x and y coordinates.
   *
   * @param vector the string array containing the x and y coordinates
   * @return the Vector2d object created from the string array
   * @throws IllegalArgumentException if the coordinates are invalid
   */
  public static Vector2d getVector2dFromStringList(String[] vector) {
    try {
      return new Vector2d(Double.parseDouble(vector[0]), Double.parseDouble(vector[1]));
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid coordinates. Please enter valid numbers.");
    }
  }

  /**
   * Creates a list of Transform2D objects from a list of string arrays.
   *
   * @param transform the list of string arrays containing the transformation parameters
   * @return the list of Transform2D objects created from the string arrays
   * @throws IllegalArgumentException if the coordinates are invalid
   */
  public static List<Transform2D> getTransformListFromStringList(List<String[]> transform) {
    try {
      List<Transform2D> transformList = new ArrayList<>();
      for (String[] transformation : transform) {
        if (transformation.length == 2) {
          transformList.addAll(parseJuliaTransform(transformation));
        } else if (transformation.length == 6) {
          transformList.add(parseAffineTransform(transformation));
        }
      }
      return transformList;
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid coordinates. Please enter valid numbers.");
    }
  }

  /**
   * Parses the Julia transformations and returns a list of JuliaTransform objects.
   *
   * @param transformation the string array containing the transformation parameters
   * @return the list of JuliaTransform objects
   */
  private static List<Transform2D> parseJuliaTransform(String[] transformation) {
    return List.of(
        new JuliaTransform(
            new Complex(
                Double.parseDouble(transformation[0]),
                Double.parseDouble(transformation[1])
            ),
            1),
        new JuliaTransform(
            new Complex(
                Double.parseDouble(transformation[0]),
                Double.parseDouble(transformation[1])
            ),
            1)
    );
  }

  /**
   * Parses the affine transformations and returns an AffineTransform2D object.
   *
   * @param transformation the string array containing the transformation parameters
   * @return the AffineTransform2D object
   */
  private static AffineTransform2D parseAffineTransform(String[] transformation) {
    return new AffineTransform2D(
        new Matrix2x2(
            Double.parseDouble(transformation[0]),
            Double.parseDouble(transformation[1]),
            Double.parseDouble(transformation[2]),
            Double.parseDouble(transformation[3])
        ),
        new Vector2d(
            Double.parseDouble(transformation[4]),
            Double.parseDouble(transformation[5])
        ));
  }
}

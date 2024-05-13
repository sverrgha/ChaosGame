package edu.ntnu.idatt2003.model;

/**
 * Class for creating a canvas for the chaos game.
 * Contains methods for converting coordinates to indices on the canvas, and for displaying the canvas.
 * Includes a method for clearing the canvas.
 * Goal: act as a model for a canvas for the chaos game.
 */

public class ChaosCanvas {

  private final int[][] canvas;
  private final int width;
  private final int height;
  private final Vector2d minCoords;
  private final Vector2d maxCoords;
  private final AffineTransform2D transformCoordsToIndices;

  /**
   * Constructs a new ChaosCanvas with specified width, height, minimum coordinates and maximum coordinates.
   * It also gives Matrix A and Vector b to the AffineTransform2D given in task description, used to transform coordinates to indices.
   *
   *
   * @param width The width of the canvas.
   * @param height The height of the canvas.
   * @param minCoords The minimum coordinates of the canvas.
   * @param maxCoords The maximum coordinates of the canvas.
   */

  public ChaosCanvas(int width, int height, Vector2d minCoords, Vector2d maxCoords) {
    this.width = width;
    this.height = height;
    this.minCoords = minCoords;
    this.maxCoords = maxCoords;
    canvas = new int[width][height];
    transformCoordsToIndices = setTransformCoordsToIndices();
  }



  /**
   * Translates a vector with coordinates in the range of minCoords and maxCoords to the corresponding indices in the canvas.
   * returns the pixel in defined vector.
   *
   * @param point The placement in the picture defined as vector.
   *
   * @return the pixel as 0 or 1.
   */
  public int getPixel(Vector2d point) {
    Vector2d transformedPoint = transformCoordsToIndices.transform(point);
    return canvas[(int) transformedPoint.getX0()][(int) transformedPoint.getX1()];
  }
  /**
   * Translates a vector with coordinates in the range of minCoords and maxCoords to the corresponding indices in the canvas.
   * set pixel to 1 in the placement of a defined vector.
   *
   * @param point The placement of pixel defined as a vector
   */
  public void putPixel(Vector2d point) {
    Vector2d transformedPoint = transformCoordsToIndices.transform(point);
    canvas[(int) transformedPoint.getX0()][(int) transformedPoint.getX1()] = 1;
  }

  /**
   * Returns the canvas array.
   *
   * @return the canvas array.
   */

  public int[][] getCanvasArray() {
    return canvas;
  }

  /**
   * Clears the canvas by setting all pixels to 0.
   */

  public void clear() {
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        canvas[i][j] = 0;
      }
    }
  }

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }

  /**
   * Sets the transformation matrix and vector to convert coordinates to indices on the canvas.
   * Calculates the scaling factors and translation values based on canvas dimensions and coordinate bounds.
   *
   * @return AffineTransform2D object representing the transformation matrix and vector.
   */

  private AffineTransform2D setTransformCoordsToIndices() {
    double a01 = (height - 1) / (minCoords.getX1() - maxCoords.getX1());
    double a10 = (width - 1) / (maxCoords.getX0() - minCoords.getX0());

    Matrix2x2 transformMatrix = new Matrix2x2(0, a01, a10, 0);

    double x0 = ((height - 1) * maxCoords.getX1()) / (maxCoords.getX1() - minCoords.getX1());
    double x1 = ((width - 1) * minCoords.getX0()) / (minCoords.getX0() - maxCoords.getX0());

    Vector2d transformVector = new Vector2d(x0, x1);

    return new AffineTransform2D(transformMatrix, transformVector);
  }

  /**
   * Displays the canvas in the console.
   */

  public void showCanvas() {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (canvas[i][j] == 1) {
          System.out.print("X");
        } else {
          System.out.print(" ");
        }
      }
      System.out.println();
    }
  }






}

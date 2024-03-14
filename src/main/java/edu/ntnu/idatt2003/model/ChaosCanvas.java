package edu.ntnu.idatt2003.model;

/**
 * Class for creating a canvas for the chaos game.
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

    Matrix2x2 A = new Matrix2x2(0, (height-1)/(minCoords.getX1() - this.maxCoords.getX1()), ((this.width-1)/(this.maxCoords.getX0() - this.minCoords.getX0())),0);
    Vector2d b = new Vector2d(((this.height-1)*this.maxCoords.getX1())/this.maxCoords.getX1() - this.minCoords.getX1(), ((this.width-1)*this.minCoords.getX0())/(this.minCoords.getX0() - this.maxCoords.getX0()));
    transformCoordsToIndices = new AffineTransform2D(A, b);
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


}

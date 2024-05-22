package edu.ntnu.idatt2003.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Test class for AffineTransform2D, covering positive test cases.
 */
public class AffineTransform2dTest {

  private static Matrix2x2 a1;
  private static Vector2d v1;
  private static Vector2d b1;

  /**
   * Sets up the test environment before all tests.
   * Initializes the Matrix2x2 and Vector2d objects.
   */
  @BeforeAll
  static void setUp() {
    a1 = new Matrix2x2(2, 2, 4, 5);
    v1 = new Vector2d(1, 2);
    b1 = new Vector2d(3, 4);
  }

  /**
   * Positive test cases for AffineTransform2D.
   */
  @Nested
  @DisplayName("Positive tests")
  class PositiveTests {

    /**
     * Tests the transform method of AffineTransform2D.
     * Verifies that the transformation is correctly applied.
     */
    @Test
    @DisplayName("transformTestAffine")
    void testTransform() {
      AffineTransform2D at1 = new AffineTransform2D(a1, b1);
      Vector2d v3 = at1.transform(v1);

      assertEquals(9, v3.getX0());
      assertEquals(18, v3.getX1());
    }

    /**
     * Tests the getMatrixCoordsList method of AffineTransform2D.
     * Verifies that the matrix coordinates are correctly retrieved.
     */
    @Test
    @DisplayName("getMatrixCoordsListTestAffine")
    void testGetMatrixCoordsList() {
      AffineTransform2D transform = new AffineTransform2D(a1, b1);
      double[] matrixCoords = transform.getMatrixCoordsList();

      assertEquals(2, matrixCoords[0]);
      assertEquals(2, matrixCoords[1]);
      assertEquals(4, matrixCoords[2]);
      assertEquals(5, matrixCoords[3]);
    }

    /**
     * Tests the getVectorCoordsList method of AffineTransform2D.
     * Verifies that the vector coordinates are correctly retrieved.
     */
    @Test
    @DisplayName("getVectorCoordsListTestAffine")
    void testGetVectorCoordsList() {
      AffineTransform2D transform = new AffineTransform2D(a1, b1);
      double[] vectorCoords = transform.getVectorCoordsList();

      assertEquals(3, vectorCoords[0]);
      assertEquals(4, vectorCoords[1]);
    }
  }
}

package edu.ntnu.idatt2003.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ChaosGameDescription.
 */

class ChaosGameDescriptionTest {
  private static List<Transform2D> transforms;
  private static ChaosGameDescription chaosGameDescription;
  private static ChaosGameDescription chaosGameDescriptionJulia;
  private static List<Transform2D> juliaTransforms;

  /**
   * Sets up the test environment before all tests.
   * Initializes the list of transformations and the ChaosGameDescription object.
   */
  @BeforeAll
  static void setUp() {
    transforms = new ArrayList<>();
    transforms.add(new AffineTransform2D(new Matrix2x2(1, 2, 3, 4),
            new Vector2d(3, 4)));

    chaosGameDescription = new ChaosGameDescription(new Vector2d(1, 2),
            new Vector2d(3, 4),
            transforms);

    juliaTransforms = new ArrayList<>();
    juliaTransforms.add(new JuliaTransform(new Complex(1, 2), 1));

    chaosGameDescriptionJulia = new ChaosGameDescription(new Vector2d(1, 2), new Vector2d(3, 4), juliaTransforms);
  }
  /**
   * Tests the getMinCoords() method of ChaosGameDescription.
   * Verifies that the minimum coordinates are correctly retrieved.
   */
  @Test
  @DisplayName("Test getMinCoords()")
  void testGetMinCoords() {
    assertEquals(1, chaosGameDescription.getMinCoords().getX0());
    assertEquals(2, chaosGameDescription.getMinCoords().getX1());
  }

  /**
   * Tests the getMaxCoords() method of ChaosGameDescription.
   * Verifies that the maximum coordinates are correctly retrieved.
   */
  @Test
  @DisplayName("Test getMaxCoords()")
  void testGetMaxCoords() {
    assertEquals(3, chaosGameDescription.getMaxCoords().getX0());
    assertEquals(4, chaosGameDescription.getMaxCoords().getX1());
  }

  /**
   * Tests the getTransform() method of ChaosGameDescription.
   * Verifies that the list of transformations is correctly retrieved.
   */
  @Test
  @DisplayName("Test getTransform()")
  void testGetTransform() {
    assertEquals(transforms, chaosGameDescription.getTransform());
  }

  /**
   * Tests the toString() method of ChaosGameDescription.
   * Verifies the string representation of the ChaosGameDescription for both AffineTransform2D and JuliaTransform cases.
   */
  @Test
  @DisplayName("Test toString() with AffineTransform2D and JuliaTransform")
  void testToString() {
    String expectedOutputAffine = "Affine2D\n" +
        "1.0, 2.0\n" +
        "3.0, 4.0\n" +
        "1.00, 2.00, 3.00, 4.00, 3.0, 4.0\n";

    String expectedOutputJulia = "Julia\n" +
        "1.0, 2.0\n" +
        "3.0, 4.0\n" +
        "1.0, 2.0\n";

    assertEquals(expectedOutputAffine, chaosGameDescription.toString());
    assertEquals(expectedOutputJulia, chaosGameDescriptionJulia.toString());
  }
}
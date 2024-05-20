package edu.ntnu.idatt2003.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Test class for JuliaTransform, covering positive test cases.
 */
class JuliaTransformTest {
  /**
   * Positive test cases for JuliaTransform.
   */
  @Nested
  @DisplayName("Positive tests")
  class PositiveTests {

    /**
     * Tests the transform method of JuliaTransform with a positive sign.
     * Verifies that the transformation is correctly applied.
     */
    @Test
    @DisplayName("Test transform with positive sign")
    void testTransform() {
      JuliaTransform jt = new JuliaTransform(new Complex(1, 2), 1);
      Complex v2 = new Complex(3, 4);
      Vector2d v3 = jt.transform(v2);
      assertEquals(1.554, v3.getX0(), 0.001);
      assertEquals(0.644, v3.getX1(), 0.001);
    }

    /**
     * Tests the transform method of JuliaTransform with a negative sign.
     * Verifies that the transformation is correctly applied.
     */
    @Test
    @DisplayName("Test transform with negative sign")
    void testTransform2() {
      JuliaTransform jt = new JuliaTransform(new Complex(1, 2), -1);
      Complex v2 = new Complex(3, 4);
      Vector2d v3 = jt.transform(v2);
      assertEquals(-1.554, v3.getX0(), 0.001);
      assertEquals(-0.644, v3.getX1(), 0.001);
    }
  }
}
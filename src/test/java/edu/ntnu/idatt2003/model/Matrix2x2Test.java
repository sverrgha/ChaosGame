package edu.ntnu.idatt2003.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Test class for Matrix2x2, covering both positive test cases.
 */
public class Matrix2x2Test {
    private static Matrix2x2 m1;

  /**
   * Sets up the test environment before all tests.
   * Initializes the Matrix2x2 object.
   */
    @BeforeAll
    static void setUp() {
      m1 = new Matrix2x2(2,2,4,5);
    }
  /**
   * Positive test cases for Matrix2x2.
   */
    @Nested
    @DisplayName("Positive tests")
    class positiveTests {
    /**
     * Tests the multiply method of Matrix2x2.
     * Verifies that the matrix multiplication is correctly applied to a vector.
     */
      @Test
      @DisplayName("Test multiply")
      void testMultiply() {
        Vector2d v1 = new Vector2d(1, 2);
        Vector2d v2 = m1.multiply(v1);
        assertEquals(6, v2.getX0());
        assertEquals(14, v2.getX1());
      }

    }


}

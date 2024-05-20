package edu.ntnu.idatt2003.model;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for Complex, covering both positive test cases.
 */
class ComplexTest {
  private static Complex v1;

  /**
   * Sets up the test environment before all tests.
   * Initializes the Complex object.
   */
  @BeforeAll
  static void setUp() {
    v1 = new Complex(1, 2);
  }

  /**
   * Positive test cases for Complex.
   */
  @Nested
  @DisplayName("Positive tests")
  class PositiveTests {

    /**
     * Tests the getX0() method of Complex.
     * Verifies that the real part (X0) is correctly retrieved.
     */
    @Test
    @DisplayName("Test getX0")
    void testGetX0() {
      assertEquals(1, v1.getX0());
    }

    /**
     * Tests the getX1() method of Complex.
     * Verifies that the imaginary part (X1) is correctly retrieved.
     */
    @Test
    @DisplayName("Test getX1")
    void testGetX1() {
      assertEquals(2, v1.getX1());
    }

    /**
     * Tests the add() method of Complex.
     * Verifies that two Complex numbers are correctly added.
     */
    @Test
    @DisplayName("Test add")
    void testAdd() {
      Complex v2 = new Complex(3, 4);
      Vector2d v3 = v1.add(v2);
      assertEquals(4, v3.getX0());
      assertEquals(6, v3.getX1());
    }

    /**
     * Tests the subtract() method of Complex.
     * Verifies that two Complex numbers are correctly subtracted.
     */
    @Test
    @DisplayName("Test subtract")
    void testSubtract() {
      Complex v2 = new Complex(3, 4);
      Vector2d v3 = v1.subtract(v2);
      assertEquals(-2, v3.getX0());
      assertEquals(-2, v3.getX1());
    }

    /**
     * Tests the sqrt() method of Complex.
     * Verifies that the square root of a Complex number is correctly calculated.
     */
    @Test
    @DisplayName("Test sqrt")
    void testSqrt() {
      Complex v2 = v1.sqrt();
      assertEquals(1.272, v2.getX0(), 0.001);
      assertEquals(0.786, v2.getX1(), 0.001);
    }
  }
}
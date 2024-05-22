package edu.ntnu.idatt2003.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for Vector2d, covering both positive test cases.
 */
public class Vector2dTest {
    private static Vector2d v1;
  /**
   * Sets up the test environment before all tests.
   * Initializes the Vector2d object.
   */
  @BeforeAll
  static void setUp() {
    v1 = new Vector2d(1, 2);
  }
  /**
   * Positive test cases for Vector2d.
   */
  @Nested
  @DisplayName("Positive tests")
  class positiveTests {
    @Test
    @DisplayName("Test getX0")
    void testGetX0() {
      assertEquals(1, v1.getX0());
    }
    /**
     * Tests the getX0() method of Vector2d.
     * Verifies that the X0 component is correctly retrieved.
     */
    @Test
    @DisplayName("Test getX1")
    void testGetX1() {
      assertEquals(2, v1.getX1());
    }
    /**
     * Tests the getX1() method of Vector2d.
     * Verifies that the X1 component is correctly retrieved.
     */
    @Test
    @DisplayName("Test add")
    void testAdd() {
      Vector2d v2 = new Vector2d(3, 4);
      Vector2d v3 = v1.add(v2);
      assertEquals(4, v3.getX0());
      assertEquals(6, v3.getX1());
    }
    /**
     * Tests the add() method of Vector2d.
     * Verifies that two Vector2d objects are correctly added.
     */
    @Test
    @DisplayName("Test subtract")
    void testSubtract() {
      Vector2d v1 = new Vector2d(1, 2);
      Vector2d v2 = new Vector2d(3, 4);
      Vector2d v3 = v1.subtract(v2);
      assertEquals(-2, v3.getX0());
      assertEquals(-2, v3.getX1());
    }
  }
}

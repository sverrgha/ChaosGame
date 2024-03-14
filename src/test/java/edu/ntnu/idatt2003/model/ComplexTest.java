package edu.ntnu.idatt2003.model;


import edu.ntnu.idatt2003.model.Complex;
import edu.ntnu.idatt2003.model.Vector2d;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class gComplexTest {
  private static Complex v1;

  @BeforeAll
  static void setUp() {
    v1 = new Complex(1, 2);
  }

  @Nested
  @DisplayName("Positive tests")
  class PositiveTests {

    @Test
    @DisplayName("Test getX0")
    void testGetX0() {
      assertEquals(1, v1.getX0());
    }

    @Test
    @DisplayName("Test getX1")
    void testGetX1() {
      assertEquals(2, v1.getX1());
    }

    @Test
    @DisplayName("Test add")
    void testAdd() {
      Complex v2 = new Complex(3, 4);
      Vector2d v3 = v1.add(v2);
      assertEquals(4, v3.getX0());
      assertEquals(6, v3.getX1());
    }

    @Test
    @DisplayName("Test subtract")
    void testSubtract() {
      Complex v2 = new Complex(3, 4);
      Vector2d v3 = v1.subtract(v2);
      assertEquals(-2, v3.getX0());
      assertEquals(-2, v3.getX1());
    }

    @Test
    @DisplayName("Test sqrt")
    void testSqrt() {
      Complex v2 = v1.sqrt();
      assertEquals(1.272, v2.getX0(), 0.001);
      assertEquals(0.786, v2.getX1(), 0.001);
    }
  }
}
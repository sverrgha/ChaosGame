package edu.ntnu.idatt2003.model;

import edu.ntnu.idatt2003.model.Vector2d;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Vector2dTest {
    private static Vector2d v1;

  @BeforeAll
  static void setUp() {
    v1 = new Vector2d(1, 2);
  }

  @Nested
  @DisplayName("Positive tests")
  class positiveTests {
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
      Vector2d v2 = new Vector2d(3, 4);
      Vector2d v3 = v1.add(v2);
      assertEquals(4, v3.getX0());
      assertEquals(6, v3.getX1());
    }

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

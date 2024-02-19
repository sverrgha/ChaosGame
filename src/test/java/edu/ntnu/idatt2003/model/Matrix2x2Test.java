package edu.ntnu.idatt2003.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idatt2003.model.Matrix2x2;
import edu.ntnu.idatt2003.model.Vector2d;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class Matrix2x2Test {
    private static Matrix2x2 m1;

    @BeforeAll
    static void setUp() {
      m1 = new Matrix2x2(2,2,4,5);
    }

    @Nested
    @DisplayName("Positive tests")
    class positiveTests {
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

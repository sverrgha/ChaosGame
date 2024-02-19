package edu.ntnu.idatt2003;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idatt2003.model.AffineTransform2D;
import edu.ntnu.idatt2003.model.Matrix2x2;
import edu.ntnu.idatt2003.model.Vector2d;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class AffineTransform2DTest {

  private static Matrix2x2 a1;
  private static Vector2d v1;
  private static Vector2d b1;
  private

  @BeforeAll
  static void setUp() {
    a1 = new Matrix2x2(2,2,4,5);
    v1 = new Vector2d(1,2);
    b1 = new Vector2d(3,4);
  }

  @Nested
  @DisplayName("Positive tests")
  public class positiveTests {
    @Test
    @DisplayName("transformTestAffine")
    void testTransform() {
      AffineTransform2D aT1 = new AffineTransform2D(a1, b1);
      Vector2d v3 = aT1.transform(v1);

      assertEquals(9, v3.getX0());
      assertEquals(18, v3.getX1());

    }
  }





}

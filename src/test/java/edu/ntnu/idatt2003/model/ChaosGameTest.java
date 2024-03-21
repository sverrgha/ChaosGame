package edu.ntnu.idatt2003.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class ChaosGameTest {

  private static ChaosGame cg;

  @BeforeAll
  public static void setup() {

    Vector2d minCoords = new Vector2d(0, 0);
    Vector2d maxCoords = new Vector2d(100, 100);
    Transform2D transform = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5),
        new Vector2d(0, 0));
    ChaosGameDescription description = new ChaosGameDescription(minCoords, maxCoords,
        List.of(transform));

    cg = new ChaosGame(description, 100, 100);
    cg.runSteps(100);
  }

  @Nested
  @DisplayName("Positive tests")
  public class PositiveTests {

    @Test
    public void testChaosGameInstanceNotNull() {
      assertNotNull(cg, "ChaosGame instance should not be null");
    }
  }
}

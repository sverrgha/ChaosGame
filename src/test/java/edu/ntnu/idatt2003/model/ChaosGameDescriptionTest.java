package edu.ntnu.idatt2003.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChaosGameDescriptionTest {
  private static ChaosGameDescription chaosGameDescription;
  private static List<Transform2D> transforms;

  @BeforeAll
  static void setUp() {
    transforms = new ArrayList<>();
    transforms.add(new JuliaTransform(new Complex(1, 2), 1));
    transforms.add(new AffineTransform2D(new Matrix2x2(1, 2, 3, 4),
            new Vector2d(3, 4)));

    chaosGameDescription = new ChaosGameDescription(new Vector2d(1, 2),
            new Vector2d(3, 4),
            transforms);
  }

  @Test
  @DisplayName("Test getMinCoords()")
  void testGetMinCoords() {
    assertEquals(1, chaosGameDescription.getMinCoords().getX0());
    assertEquals(2, chaosGameDescription.getMinCoords().getX1());
  }

  @Test
  @DisplayName("Test getMaxCoords()")
  void testGetMaxCoords() {
    assertEquals(3, chaosGameDescription.getMaxCoords().getX0());
    assertEquals(4, chaosGameDescription.getMaxCoords().getX1());
  }

  @Test
  @DisplayName("Test getTransform()")
  void testGetTransform() {
    assertEquals(transforms, chaosGameDescription.getTransform());
  }
}
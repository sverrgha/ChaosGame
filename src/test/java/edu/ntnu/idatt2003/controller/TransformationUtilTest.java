package edu.ntnu.idatt2003.controller;

import edu.ntnu.idatt2003.model.AffineTransform2D;
import edu.ntnu.idatt2003.model.ChaosGame;
import edu.ntnu.idatt2003.model.ChaosGameDescription;
import edu.ntnu.idatt2003.model.Complex;
import edu.ntnu.idatt2003.model.JuliaTransform;
import edu.ntnu.idatt2003.model.Matrix2x2;
import edu.ntnu.idatt2003.model.Transform2D;
import edu.ntnu.idatt2003.model.Vector2d;

import edu.ntnu.idatt2003.utils.TransformationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class TransformationUtilTest {

  private ChaosGame juliaGame;
  private ChaosGame affineGame;
  private List<String[]> juliaTransformStrings;
  private List<String[]> affineTransformStrings;

  @BeforeEach
  public void setUp() {
    List<Transform2D> juliaTransforms = new ArrayList<>();
    juliaTransforms.add(new JuliaTransform(new Complex(0.355, 0.355), 1));
    juliaGame = new ChaosGame(new ChaosGameDescription(
        new Vector2d(0,0), new Vector2d(1,1),juliaTransforms), 800, 800);

    List<Transform2D> affineTransforms = new ArrayList<>();
    affineTransforms.add(new AffineTransform2D(
        new Matrix2x2(0.5, 0, 0, 0.5), new Vector2d(1, 1)));
    affineGame = new ChaosGame(new ChaosGameDescription(
        new Vector2d(0,0), new Vector2d(1,1),affineTransforms), 800, 800);

    juliaTransformStrings = new ArrayList<>();
    juliaTransformStrings.add(new String[]{"0.355", "0.355"});

    affineTransformStrings = new ArrayList<>();
    affineTransformStrings.add(new String[]{"0.5", "0", "0", "0.5", "1", "1"});
  }

  @Nested
  @DisplayName("Positive Tests")
  class PositiveTests {

    /**
     * Tests that the transformation list for a Julia fractal is returned correctly.
     */
    @Test
    @DisplayName("Get transformation list for Julia fractal")
    public void testGetTransformListJulia() {
      List<double[]> transformList = TransformationUtil.getTransformList(juliaGame);
      assertEquals(1, transformList.size());
      assertArrayEquals(new double[]{0.355, 0.355}, transformList.get(0));
    }

    /**
     * Tests that the transformation list for an affine fractal is returned correctly.
     */
    @Test
    @DisplayName("Get transformation list for affine fractal")
    public void testGetTransformListAffine() {
      List<double[]> transformList = TransformationUtil.getTransformList(affineGame);
      assertEquals(1, transformList.size());
      assertArrayEquals(new double[]{0.5, 0, 0, 0.5, 1, 1}, transformList.get(0));
    }

    /**
     * Tests that the method correctly identifies a Julia fractal.
     */
    @Test
    @DisplayName("Check if fractal is Julia")
    public void testFractalIsJulia() {
      assertTrue(TransformationUtil.fractalIsJulia(juliaGame));
    }

    /**
     * Tests that the method correctly identifies when the fractal is not a Julia fractal.
     */
    @Test
    @DisplayName("Check if fractal is not Julia")
    public void testFractalIsNotJulia() {
      assertFalse(TransformationUtil.fractalIsJulia(affineGame));
    }

    /**
     * Tests the conversion from a string array to a Vector2d object.
     */
    @Test
    @DisplayName("Convert string array to Vector2d")
    public void testGetVector2dFromStringList() {
      String[] vector = {"1.0", "2.0"};
      Vector2d vec = TransformationUtil.getVector2dFromStringList(vector);
      assertEquals(1.0, vec.getX0());
      assertEquals(2.0, vec.getX1());
    }

    /**
     * Tests the creation of JuliaTransform objects from string arrays.
     */
    @Test
    @DisplayName("Create JuliaTransform list from string arrays")
    public void testGetTransformListFromStringListJulia() {
      List<Transform2D> transforms = TransformationUtil.getTransformListFromStringList(juliaTransformStrings);
      assertEquals(2, transforms.size());
      assertTrue(transforms.get(0) instanceof JuliaTransform);
      assertTrue(transforms.get(1) instanceof JuliaTransform);
    }

    /**
     * Tests the creation of AffineTransform2D objects from string arrays.
     */
    @Test
    @DisplayName("Create AffineTransform2D list from string arrays")
    public void testGetTransformListFromStringListAffine() {
      List<Transform2D> transforms = TransformationUtil.getTransformListFromStringList(affineTransformStrings);
      assertEquals(1, transforms.size());
      assertTrue(transforms.get(0) instanceof AffineTransform2D);
    }
  }

  @Nested
  @DisplayName("Negative Tests")
  class NegativeTests {

    /**
     * Tests that an IllegalArgumentException is thrown for invalid input when converting string array to Vector2d.
     */
    @Test
    @DisplayName("Invalid input for converting string array to Vector2d")
    public void testGetVector2dFromStringListInvalid() {
      String[] vector = {"abc", "2.0"};
      assertThrows(IllegalArgumentException.class, () -> {
        TransformationUtil.getVector2dFromStringList(vector);
      });
    }

    /**
     * Tests that an IllegalArgumentException is thrown for invalid input when creating Transform2D list from string arrays.
     */
    @Test
    @DisplayName("Invalid input for creating Transform2D list from string arrays")
    public void testGetTransformListFromStringListInvalid() {
      List<String[]> transformStrings = new ArrayList<>();
      transformStrings.add(new String[]{"abc", "0", "0", "0.5", "1", "1"});
      assertThrows(IllegalArgumentException.class, () -> {
        TransformationUtil.getTransformListFromStringList(transformStrings);
      });
    }
  }
}

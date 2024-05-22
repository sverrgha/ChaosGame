package edu.ntnu.idatt2003.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ChaosGameDescriptionFactory, covering both positive and negative test cases.
 */
public class ChaosGameDescriptionFactoryTest {

  /**
   * Positive test cases for ChaosGameDescriptionFactory.
   */
  @Nested
  @DisplayName("Positive Tests")
  class PositiveTests {

    /**
     * Tests getting the Sierpinski Triangle description.
     * This test checks if the Sierpinski Triangle description is correctly created with the expected minimum and maximum coordinates
     * and the correct number and types of transformations.
     */
    @Test
    @DisplayName("Get Sierpinski Triangle Test")
    void testGetSierpinskiTriangle() {
      ChaosGameDescription description = ChaosGameDescriptionFactory.get(ChaosGameDescriptionFactory.DescriptionTypeEnum.SIERPINSKI_TRIANGLE);

      assertNotNull(description);
      assertEquals(new Vector2d(0, 0).toString(), description.getMinCoords().toString());
      assertEquals(new Vector2d(1, 1).toString(), description.getMaxCoords().toString());

      List<Transform2D> transforms = description.getTransform();
      assertEquals(3, transforms.size());

      assertInstanceOf(AffineTransform2D.class, transforms.get(0));
      assertInstanceOf(AffineTransform2D.class, transforms.get(1));
      assertInstanceOf(AffineTransform2D.class, transforms.get(2));
    }

    /**
     * Tests getting the Barnsley Fern description.
     * This test checks if the Barnsley Fern description is correctly created with the expected minimum and maximum coordinates
     * and the correct number and types of transformations.
     */
    @Test
    @DisplayName("Get Barnsley Fern Test")
    void testGetBarnsleyFern() {
      ChaosGameDescription description = ChaosGameDescriptionFactory.get(ChaosGameDescriptionFactory.DescriptionTypeEnum.BARNSLEY_FERN);

      assertNotNull(description);
      assertEquals(new Vector2d(-2.5, 0).toString(), description.getMinCoords().toString());
      assertEquals(new Vector2d(2.5, 10).toString(), description.getMaxCoords().toString());

      List<Transform2D> transforms = description.getTransform();
      assertEquals(4, transforms.size());

      assertInstanceOf(AffineTransform2D.class, transforms.get(0));
      assertInstanceOf(AffineTransform2D.class, transforms.get(1));
      assertInstanceOf(AffineTransform2D.class, transforms.get(2));
      assertInstanceOf(AffineTransform2D.class, transforms.get(3));
    }

    /**
     * Tests getting the Julia transformation description.
     * This test checks if the Julia transformation description is correctly created with the expected minimum and maximum coordinates
     * and the correct number and types of transformations.
     */
    @Test
    @DisplayName("Get Julia Transformation Test")
    void testGetJuliaTransformation() {
      ChaosGameDescription description = ChaosGameDescriptionFactory.get(ChaosGameDescriptionFactory.DescriptionTypeEnum.JULIA);

      assertNotNull(description);
      assertEquals(new Vector2d(-1.6, -1).toString(), description.getMinCoords().toString());
      assertEquals(new Vector2d(1.6, 1).toString(), description.getMaxCoords().toString());

      List<Transform2D> transforms = description.getTransform();
      assertEquals(2, transforms.size());

      assertInstanceOf(JuliaTransform.class, transforms.get(0));
      assertInstanceOf(JuliaTransform.class, transforms.get(1));
    }

    /**
     * Tests getting the Levy C Curve description.
     * This test checks if the Levy C Curve description is correctly created with the expected minimum and maximum coordinates
     * and the correct number and types of transformations.
     */
    @Test
    @DisplayName("Get Levy C Curve Test")
    void testGetLevyCCurve() {
      ChaosGameDescription description = ChaosGameDescriptionFactory.get(ChaosGameDescriptionFactory.DescriptionTypeEnum.LEVY_C_CURVE);

      assertNotNull(description);
      assertEquals(new Vector2d(-1, -0.5).toString(), description.getMinCoords().toString());
      assertEquals(new Vector2d(2, 1.5).toString(), description.getMaxCoords().toString());

      List<Transform2D> transforms = description.getTransform();
      assertEquals(2, transforms.size());

      assertInstanceOf(AffineTransform2D.class, transforms.get(0));
      assertInstanceOf(AffineTransform2D.class, transforms.get(1));
    }

    /**
     * Tests getting the Dragon Curve description.
     * This test checks if the Dragon Curve description is correctly created with the expected minimum and maximum coordinates
     * and the correct number and types of transformations.
     */
    @Test
    @DisplayName("Get Dragon Curve Test")
    void testGetDragonCurve() {
      ChaosGameDescription description = ChaosGameDescriptionFactory.get(ChaosGameDescriptionFactory.DescriptionTypeEnum.DRAGON_CURVE);

      assertNotNull(description);
      assertEquals(new Vector2d(-7, 0).toString(), description.getMinCoords().toString());
      assertEquals(new Vector2d(6, 11).toString(), description.getMaxCoords().toString());

      List<Transform2D> transforms = description.getTransform();
      assertEquals(2, transforms.size());

      assertInstanceOf(AffineTransform2D.class, transforms.get(0));
      assertInstanceOf(AffineTransform2D.class, transforms.get(1));
    }
  }

  /**
   * Negative test cases for ChaosGameDescriptionFactory.
   */
  @Nested
  @DisplayName("Negative Tests")
  class NegativeTests {

    /**
     * Tests getting a custom transformation file that does not exist.
     * This test checks if a FileNotFoundException is thrown when trying to get a custom transformation
     * that does not exist.
     */
    @Test
    @DisplayName("Get Custom Transformation File Not Found Test")
    void testGetCustomTransformationFileNotFound() {
      Exception exception = assertThrows(FileNotFoundException.class, () ->
          ChaosGameDescriptionFactory.getCustom("non_existent_transformation")
      );

      String expectedMessage = "File src/main/resources/fractals/non_existent_transformation.txt not found.";
      String actualMessage = exception.getMessage();

      assertTrue(actualMessage.contains(expectedMessage));
    }
  }
}





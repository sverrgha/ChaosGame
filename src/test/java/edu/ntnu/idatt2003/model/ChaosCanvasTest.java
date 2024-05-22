package edu.ntnu.idatt2003.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ChaosCanvas, covering both positive and negative test cases.
 */
public class ChaosCanvasTest {

  private ChaosCanvas chaosCanvas;

  @BeforeEach
  public void setUp() {
    Vector2d minCoords = new Vector2d(0, 0);
    Vector2d maxCoords = new Vector2d(10, 10);
    chaosCanvas = new ChaosCanvas(10, 10, minCoords, maxCoords);
  }

  /**
   * Positive test cases for ChaosCanvas.
   */
  @Nested
  @DisplayName("Positive Tests")
  class PositiveTests {

    /**
     * Tests the constructor of ChaosCanvas.
     */
    @Test
    @DisplayName("Constructor Test")
    public void testConstructor() {
      assertNotNull(chaosCanvas);
      assertEquals(10, chaosCanvas.getWidth());
      assertEquals(10, chaosCanvas.getHeight());
      assertNotNull(chaosCanvas.getCanvasArray());
    }

    /**
     * Tests getting a pixel within bounds.
     */
    @Test
    @DisplayName("Get Pixel Test")
    public void testGetPixel() {
      Vector2d point = new Vector2d(5, 5);
      assertEquals(0, chaosCanvas.getPixel(point));

      chaosCanvas.putPixel(point);
      assertEquals(1, chaosCanvas.getPixel(point));
    }

    /**
     * Tests putting a pixel within bounds.
     */
    @Test
    @DisplayName("Put Pixel Test")
    public void testPutPixel() {
      Vector2d point = new Vector2d(5, 5);
      chaosCanvas.putPixel(point);
      assertEquals(1, chaosCanvas.getPixel(point));
    }

    /**
     * Tests getting the canvas array.
     */
    @Test
    @DisplayName("Get Canvas Array Test")
    public void testGetCanvasArray() {
      int[][] canvasArray = chaosCanvas.getCanvasArray();
      assertNotNull(canvasArray);
      assertEquals(10, canvasArray.length);
      assertEquals(10, canvasArray[0].length);
    }

    /**
     * Tests clearing the canvas.
     */
    @Test
    @DisplayName("Clear Canvas Test")
    public void testClear() {
      Vector2d point = new Vector2d(5, 5);
      chaosCanvas.putPixel(point);
      assertEquals(1, chaosCanvas.getPixel(point));

      chaosCanvas.clear();
      assertEquals(0, chaosCanvas.getPixel(point));
    }

    /**
     * Tests getting the height of the canvas.
     */
    @Test
    @DisplayName("Get Height Test")
    public void testGetHeight() {
      assertEquals(10, chaosCanvas.getHeight());
    }

    /**
     * Tests getting the width of the canvas.
     */
    @Test
    @DisplayName("Get Width Test")
    public void testGetWidth() {
      assertEquals(10, chaosCanvas.getWidth());
    }

    /**
     * Tests transforming coordinates to indices.
     */
    @Test
    @DisplayName("Transform Coords to Indices Test")
    public void testTransformCoordsToIndicesIntegration() {
      Vector2d point = new Vector2d(5, 5);
      chaosCanvas.putPixel(point);
      assertEquals(1, chaosCanvas.getPixel(point));

      Vector2d minPoint = new Vector2d(0, 0);
      chaosCanvas.putPixel(minPoint);
      assertEquals(1, chaosCanvas.getPixel(minPoint));

      Vector2d maxPoint = new Vector2d(10, 10);
      chaosCanvas.putPixel(maxPoint);
      assertEquals(1, chaosCanvas.getPixel(maxPoint));
    }

    /**
     * Tests displaying the canvas.
     */
    @Test
    @DisplayName("Show Canvas Test")
    public void testShowCanvas() {
      chaosCanvas.showCanvas();
    }
  }

  /**
   * Negative test cases for ChaosCanvas.
   */
  @Nested
  @DisplayName("Negative Tests")
  class NegativeTests {

    /**
     * Tests getting a pixel out of bounds.
     */
    @Test
    @DisplayName("Get Pixel Out of Bounds Test")
    public void testGetPixelOutOfBounds() {
      Vector2d outOfBoundsPoint = new Vector2d(20, 20);
      assertEquals(0, chaosCanvas.getPixel(outOfBoundsPoint));
    }

    /**
     * Tests putting a pixel out of bounds.
     */
    @Test
    @DisplayName("Put Pixel Out of Bounds Test")
    public void testPutPixelOutOfBounds() {
      Vector2d outOfBoundsPoint = new Vector2d(20, 20);
      chaosCanvas.putPixel(outOfBoundsPoint);
      assertEquals(0, chaosCanvas.getPixel(outOfBoundsPoint));
    }
  }
}
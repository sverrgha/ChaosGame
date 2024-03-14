package edu.ntnu.idatt2003.model;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;

public class ChaosCanvasTest {

  private static ChaosCanvas canvas;
  private static final int WIDTH = 10;
  private static final int HEIGHT = 10;

  @BeforeEach
  public void setUp() {
    Vector2d minCoords = new Vector2d(0, 0);
    Vector2d maxCoords = new Vector2d(100, 100); // Example coordinates
    canvas = new ChaosCanvas(WIDTH, HEIGHT, minCoords, maxCoords);
  }

  @Test
  public void testGetPixel() {
    assertEquals(0, canvas.getPixel(new Vector2d(0, 0)));
    assertEquals(0, canvas.getPixel(new Vector2d(5, 5)));
    assertEquals(0, canvas.getPixel(new Vector2d(90, 50)));
  }

  @Test
  public void testPutPixel() {
    Vector2d point1 = new Vector2d(50, 50);
    Vector2d point2 = new Vector2d(5, 5);
    Vector2d point3 = new Vector2d(75, 30);
    canvas.putPixel(point1);
    canvas.putPixel(point2);
    canvas.putPixel(point3);
    assertEquals(1, canvas.getPixel(point1));
    assertEquals(1, canvas.getPixel(point2));
    assertEquals(1, canvas.getPixel(point3));
  }

  @Test
  public void testGetCanvasArray() {
    int[][] canvasArray = canvas.getCanvasArray();
    assertEquals(WIDTH, canvasArray.length);
    assertEquals(HEIGHT, canvasArray[0].length);
  }

  @Test
  public void testClear() {
    // Put some pixels first
    Vector2d point1 = new Vector2d(10, 10);
    Vector2d point2 = new Vector2d(20, 20);
    canvas.putPixel(point1);
    canvas.putPixel(point2);

    // Clear the canvas
    canvas.clear();

    // Check if all pixels are cleared (set to 0)
    for (int i = 0; i < WIDTH; i++) {
      for (int j = 0; j < HEIGHT; j++) {
        assertEquals(0, canvas.getPixel(new Vector2d(i, j)));
      }
    }
  }
}
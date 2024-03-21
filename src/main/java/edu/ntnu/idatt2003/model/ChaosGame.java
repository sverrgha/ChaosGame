package edu.ntnu.idatt2003.model;
import java.util.Random;

public class ChaosGame {

  private final ChaosCanvas canvas;
  private final ChaosGameDescription description;

  private Vector2d currentPoint;

  Random random;

  public ChaosGame (ChaosGameDescription description, int width, int height) {
    this.description = description;
    this.canvas = new ChaosCanvas(width, height, description.getMinCoords(), description.getMaxCoords());
    this.currentPoint = new Vector2d(0, 0);
    random = new Random();
  }

  public ChaosCanvas getCanvas() {
    return canvas;
  }

  public void runSteps(int steps) {
    canvas.putPixel(currentPoint);
    for (int i = 0; i < steps; i++) {
      int randomIndex = random.nextInt(description.getTransform().size());
      currentPoint = description.getTransform().get(randomIndex).transform(currentPoint);

      canvas.putPixel(currentPoint);
    }
  }


}

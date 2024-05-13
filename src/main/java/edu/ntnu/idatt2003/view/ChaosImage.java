package edu.ntnu.idatt2003.view;

import edu.ntnu.idatt2003.model.ChaosCanvas;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ChaosImage {

  private WritableImage createImageFromCanvas(ChaosCanvas chaosCanvas){
    int width = chaosCanvas.getWidth();
    int height = chaosCanvas.getHeight();
    WritableImage image = new WritableImage(width, height);
    int[][] canvasData = chaosCanvas.getCanvasArray();

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        if (canvasData[x][y] == 1) {
          image.getPixelWriter().setColor(x, y, Color.BLACK);
        } else {
          image.getPixelWriter().setColor(x, y, Color.WHITE);
        }
      }
    }
    return image;
  }

}

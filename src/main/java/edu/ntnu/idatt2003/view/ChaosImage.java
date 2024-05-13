package edu.ntnu.idatt2003.view;

import edu.ntnu.idatt2003.model.ChaosCanvas;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ChaosImage {

  public WritableImage createImageFromCanvas(ChaosCanvas chaosCanvas){
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

  public WritableImage scaleImage(WritableImage image, double scaleFactor) {
    int newWidth = (int) (image.getWidth() * scaleFactor);
    int newHeight = (int) (image.getHeight() * scaleFactor);
    WritableImage scaledImage = new WritableImage(newWidth, newHeight);
    for (int y = 0; y < newHeight; y++) {
      for (int x = 0; x < newWidth; x++) {
        Color originalColor = image.getPixelReader().getColor((int) (x / scaleFactor), (int) (y / scaleFactor));
        scaledImage.getPixelWriter().setColor(x, y, originalColor);
      }
    }
    return scaledImage;
  }

}

package edu.ntnu.idatt2003.view;

import edu.ntnu.idatt2003.model.ChaosCanvas;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * The ChaosImage class provides methods for creating and manipulating images based on a
 * ChaosCanvas object. It includes functionality to create an image from a canvas and to scale
 * an image by a specified factor.
 */

public class ChaosImage {

  /**
   * Creates a WritableImage from a given ChaosCanvas object.
   * Each element in the canvas array is mapped to a pixel in the image,
   * where a value of 1 is black and 0 is white.
   *
   * @param chaosCanvas The ChaosCanvas object containing the data for the image.
   * @return A WritableImage representing the canvas data.
   */

  public static WritableImage createImageFromCanvas(ChaosCanvas chaosCanvas) {
    int width = chaosCanvas.getWidth();
    int height = chaosCanvas.getHeight();
    WritableImage image = new WritableImage(width, height);
    int[][] canvasData = chaosCanvas.getCanvasArray();

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (canvasData[x][y] == 1) {
          image.getPixelWriter().setColor(y, x, Color.BLACK);
        } else {
          image.getPixelWriter().setColor(y, x, Color.WHITE);
        }
      }
    }
    return image;
  }

  /**
   * Scales a given WritableImage by a specified factor.
   * The method creates a new image with dimensions scaled by the scaleFactor and copies the color
   * data from the original image.
   *
   * @param image The original WritableImage to be scaled.
   * @param scaleFactor The factor by which to scale the image. A scaleFactor > 1 enlarges the
   *                    image,while a scaleFactor < 1 reduces its size.
   * @return A new WritableImage that is scaled by the specified factor.
   */

  public static WritableImage scaleImage(WritableImage image, double scaleFactor) {
    int newWidth = (int) (image.getWidth() * scaleFactor);
    int newHeight = (int) (image.getHeight() * scaleFactor);
    WritableImage scaledImage = new WritableImage(newWidth, newHeight);
    for (int y = 0; y < newHeight; y++) {
      for (int x = 0; x < newWidth; x++) {
        Color originalColor = image.getPixelReader()
            .getColor((int) (x / scaleFactor), (int) (y / scaleFactor));
        scaledImage.getPixelWriter().setColor(x, y, originalColor);
      }
    }
    return scaledImage;
  }

}

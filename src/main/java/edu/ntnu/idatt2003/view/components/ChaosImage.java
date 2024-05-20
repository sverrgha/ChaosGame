package edu.ntnu.idatt2003.view.components;

import edu.ntnu.idatt2003.model.ChaosCanvas;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;

/**
 * The ChaosImage class provides methods for creating and manipulating images based on a
 * ChaosCanvas object. It includes functionality to create an image from a canvas and to scale
 * an image by a specified factor.
 */

public class ChaosImage {
  private static final int MAX_SCALE = 5;
  private static final int MIN_SCALE = 1;
  private static final double ZOOM_FACTOR = 1.05;

  private ChaosImage() {
  }
  /**
   * Creates a WritableImage from a given ChaosCanvas object.
   * Each element in the canvas array is mapped to a pixel in the image,
   * where a value of 1 is black and 0 is white.
   *
   * @param chaosCanvas The ChaosCanvas object containing the data for the image.
   * @return A Pane containing the image generated representing the canvas data.
   */

  public static Pane createImageFromCanvas(ChaosCanvas chaosCanvas) {
    int width = chaosCanvas.getWidth();
    int height = chaosCanvas.getHeight();
    WritableImage image = new WritableImage(width, height);
    int[][] canvasData = chaosCanvas.getCanvasArray();

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (canvasData[y][x] == 1) {
          image.getPixelWriter().setColor(x, y, Color.BLACK);
        } else {
          image.getPixelWriter().setColor(x, y, Color.WHITE);
        }
      }
    }
    ImageView imageView = new ImageView(image);
    Pane pane = new Pane(imageView);
    pane.setMaxHeight(height);
    pane.setMaxWidth(width);
    StackPane.setAlignment(imageView, javafx.geometry.Pos.CENTER);
    pane.setClip(new Rectangle(width, height));
    enableZoom(imageView);
    enablePan(imageView);
    return pane;
  }

  /**
   * Enables zooming on an ImageView object, using a Scale transform.
   *
   * @param imageView The ImageView object to enable zooming on.
   */
  private static void enableZoom(ImageView imageView) {
    Scale scaleTransform = new Scale();
    imageView.getTransforms().add(scaleTransform);
    imageView.setOnScroll(e -> zoom(imageView, e));
  }

  /**
   * Enables panning on an ImageView object, by translating the image based on mouse drag events
   * and the last mouse coordinates. The panning is limited to the edges of the image.
   *
   * @param imageView The ImageView object to enable panning on.
   */
  private static void enablePan(ImageView imageView) {
    final ObjectProperty<Point2D> lastMouseCoordinates = new SimpleObjectProperty<>();
    imageView.setOnMousePressed(e -> lastMouseCoordinates.set(new Point2D(e.getX(), e.getY())));
    imageView.setOnMouseDragged(e -> {
      double deltaX = e.getX() - lastMouseCoordinates.get().getX();
      double deltaY = e.getY() - lastMouseCoordinates.get().getY();

      int newX = (int) (imageView.getTranslateX() + deltaX);
      int newY = (int) (imageView.getTranslateY() + deltaY);

      checkEdgesPan(imageView, newX, newY);
    });
  }

  /**
   * Zooms in or out on an ImageView object based on the scroll event, by scaling the image.
   * The zooming is centered around the mouse position.
   *
   * @param imageView The ImageView object to enable panning on.
   */
  private static void zoom(ImageView imageView, ScrollEvent e) {
    double zoomFactor = calculateZoomFactor(e.getDeltaY());

    final double scaleBefore = imageView.getScaleX();
    double scale = imageView.getScaleX() * zoomFactor;

    scale = clamp(scale, MIN_SCALE, MAX_SCALE);

    imageView.setScaleX(scale);
    imageView.setScaleY(scale);

    double mouseX = e.getX() - imageView.getFitWidth() / 2;
    double mouseY = e.getY() - imageView.getFitHeight() / 2;

    double newTranslateX = calculateNewTranslate(mouseX, scale,
            scaleBefore, imageView.getTranslateX());
    double newTranslateY = calculateNewTranslate(mouseY, scale,
            scaleBefore, imageView.getTranslateY());

    checkEdgesZoom(imageView, newTranslateX, newTranslateY);
  }

  /**
   * Checks if the image is within the bounds of the ImageView after zooming.
   * If the image is outside the bounds, the image is translated back into the bounds.
   *
   * @param imageView The ImageView object to check the edges of.
   * @param newX      The new x-coordinate of the image.
   * @param newY      The new y-coordinate of the image.
   */
  private static void checkEdgesZoom(ImageView imageView, double newX, double newY) {
    double imageWidthHalf = imageView.getFitWidth() / 2;
    double imageHeightHalf = imageView.getFitHeight() / 2;
    if ((newX + imageWidthHalf) / imageView.getScaleX() <= imageWidthHalf
            && (newX - imageWidthHalf) / imageView.getScaleX() >= -imageWidthHalf) {
      imageView.setTranslateX(newX);
    } else if (imageView.getScaleX() <= 1) {
      imageView.setTranslateX(0);
    }
    if ((newY + imageHeightHalf) / imageView.getScaleY() <= imageHeightHalf
            && (newY - imageHeightHalf) / imageView.getScaleY() >= -imageHeightHalf) {
      imageView.setTranslateY(newY);
    } else if (imageView.getScaleY() <= 1) {
      imageView.setTranslateY(0);
    }
  }

  /**
   * Checks if the image is within the bounds of the ImageView after panning.
   * If the image is outside the bounds, the image is not moved.
   *
   * @param imageView The ImageView object to check the edges of.
   * @param newX      The new x-coordinate of the image.
   * @param newY      The new y-coordinate of the image.
   */

  private static void checkEdgesPan(ImageView imageView, double newX, double newY) {
    double imageWidthHalf = imageView.getFitWidth() / 2;
    double imageHeightHalf = imageView.getFitHeight() / 2;
    if ((newX + imageWidthHalf) / imageView.getScaleX() <= imageWidthHalf
            && (newX - imageWidthHalf) / imageView.getScaleX() >= -imageWidthHalf) {
      imageView.setTranslateX(newX);
    }
    if ((newY + imageHeightHalf) / imageView.getScaleY() <= imageHeightHalf
            && (newY - imageHeightHalf) / imageView.getScaleY() >= -imageHeightHalf) {
      imageView.setTranslateY(newY);
    }
  }

  /**
   * Calculates the zoom factor based on the way the mouse wheel is scrolled.
   * if deltaY is negative, the image is zoomed out, otherwise it is zoomed in.
   * The zoom factor is calculated as 1.05, for zooming in, and 1/1.05 for zooming out.
   *
   * @param deltaY The amount the mouse wheel is scrolled.
   * @return The calculated zoom factor.
   */
  private static double calculateZoomFactor(double deltaY) {
    return deltaY < 0 ? 1 / ZOOM_FACTOR : ZOOM_FACTOR;
  }

  /**
   * Calculates the new translation of the image after zooming, based on the mouse position,
   * the scale factor after zooming, the scale factor before zooming, and the translation before
   * zooming.
   *
   * @param mousePosition   The position of the mouse.
   * @param scale           The scale factor after zooming.
   * @param scaleBefore     The scale factor before zooming.
   * @param translateBefore The translation before zooming.
   * @return The new translation of the image.
   */
  private static double calculateNewTranslate(double mousePosition, double scale,
                                              double scaleBefore, double translateBefore) {
    return -mousePosition * scale + mousePosition * scaleBefore + translateBefore;
  }

  /**
   * Clamp a value between a minimum and maximum value. Makes sure the value is within the bounds.
   *
   * @param value The value to clamp.
   * @param min   The minimum value.
   * @param max   The maximum value.
   * @return The clamped value.
   */
  private static double clamp(double value, double min, double max) {
    return Math.max(min, Math.min(max, value));
  }
}
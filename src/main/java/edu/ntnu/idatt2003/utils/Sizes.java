package edu.ntnu.idatt2003.utils;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

/**
 * The Sizes class is a utility class that provides constants
 * for sizes used throughout the application.
 */
public class Sizes {
  private static final Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
  public static final double SCREEN_WIDTH = primaryScreenBounds.getWidth();
  public static final double SCREEN_HEIGHT = primaryScreenBounds.getHeight();

  /**
   * Private constructor to prevent instantiation of this class.
   */
  private Sizes() {
  }
}

package edu.ntnu.idatt2003.view.components;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

/**
 * A factory for creating text boxes. The text boxes are styled with the "text-box"
 * style class.
 * Goal: Creating text boxes.
 */
public class TextBoxFactory {

  /**
   * Private constructor to prevent instantiation.
   */
  private TextBoxFactory() {
  }
  /**
   * Creates a text box with the specified text. The text box is styled with the
   * "text-box" style class. The text box is a StackPane with a Label as a child.
   * The text box is configured to grow horizontally.
   *
   * @param text The text to display in the text box.
   * @return The text box.
   */
  public static StackPane createTextBox(String text) {
    StackPane textBox = new StackPane();
    textBox.getStyleClass().add("text-box");
    HBox.setHgrow(textBox, Priority.ALWAYS);
    textBox.getChildren().add(new Label(text));
    return textBox;
  }
}

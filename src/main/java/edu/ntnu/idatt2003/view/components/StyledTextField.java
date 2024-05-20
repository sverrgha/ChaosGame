package edu.ntnu.idatt2003.view.components;

import javafx.scene.control.TextField;

/**
 * A styled text field with a specified prompt text, width and height.
 * The text field has a style class "text-field".
 * Goal: Create a styled text field with a specified prompt text, width and height.
 */
public class StyledTextField extends TextField {
  /**
   * Creates a styled text field with a specified prompt text, width and height.
   * The text field has a style class "text-field".
   *
   * @param promptText The text to display in the text field when it is empty.
   * @param width The preferred width of the text field.
   * @param height The preferred height of the text field.
   */
  public StyledTextField(String promptText, int width, int height) {
    super();
    this.setPromptText(promptText);
    this.setPrefSize(width, height);
    this.getStyleClass().add("text-field");
  }

}

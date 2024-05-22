package edu.ntnu.idatt2003.view.components;

import java.util.Locale;
import java.util.function.BiConsumer;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

/**
 * A styled text field with a specified prompt text, width and height.
 * The text field has a style class "text-field".
 * Goal: Create a styled text field with a specified prompt text, width and height.
 */
public class TextFieldFactory {

  /**
   * Private constructor to prevent instantiation.
   */
  private TextFieldFactory() {
  }

  /**
   * Creates a styled text field with a specified prompt text, width and height.
   * The text field has a style class "text-field".
   *
   * @param promptText The text to display in the text field when it is empty.
   * @param width      The preferred width of the text field.
   * @param height     The preferred height of the text field.
   */
  public static TextField createTextField(String promptText, int width, int height) {
    TextField textField = new TextField();
    textField.setPromptText(promptText);
    textField.setPrefSize(width, height);
    textField.getStyleClass().add("text-field");
    return textField;
  }

  /**
   * Creates a styled text field with a specified coordinate, width and height.
   * The coordinate is formatted to two decimal places, and is set as the text
   * of the text field.
   *
   * @param coordinate the coordinate to display in the text field.
   * @param width      the preferred width of the text field.
   * @param height     the preferred height of the text field.
   */
  public static TextField createTextField(double coordinate, int width, int height) {
    TextField textField = new TextField(String.format(Locale.US, "%.2f", coordinate));
    textField.setPrefSize(width, height);
    textField.getStyleClass().add("text-field");
    return textField;
  }

  /**
   * Creates a styled text field with a specified coordinate, width and height, in addition to this
   * it also takes an BiConsumer as a parameter to set the action event.
   *
   * @param promptText   The text to display in the text field when it is empty.
   * @param width        The preferred width of the text field.
   * @param height       The preferred height of the text field.
   * @param eventHandler The event handler to set on the text field.
   * @return The styled text field with action event.
   */
  public static TextField createTextField(String promptText, int width, int height,
                                          BiConsumer<TextField, ActionEvent> eventHandler) {
    TextField textField = new TextField();
    textField.setPromptText(promptText);
    textField.setPrefSize(width, height);
    textField.getStyleClass().add("button");
    textField.setOnAction(e -> eventHandler.accept(textField, e));
    return textField;
  }

}

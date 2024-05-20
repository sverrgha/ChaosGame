package edu.ntnu.idatt2003.view.components;

import java.util.List;
import javafx.scene.control.ComboBox;

/**
 * A styled combo box with a specified prompt text, width, height and values.
 * The combo box has a style class "combo-box" and the values are set to the specified values.
 * Goal: Create a styled combo box with a specified prompt text, width, height and values.
 */
public class StyledComboBox<T> extends ComboBox<T> {
  /**
   * Creates a styled combo box with a specified prompt text, width, height and values.
   * The combo box has a style class "combo-box" and the values are set to the specified values.
   *
   * @param promptText The text to display in the combo box when it is empty.
   * @param width      The preferred width of the combo box.
   * @param height     The preferred height of the combo box.
   * @param values     The values to set in the combo box.
   */
  public StyledComboBox(String promptText, int width, int height, List<T> values) {
    super();
    this.setPromptText(promptText);
    this.setPrefSize(width, height);
    this.getStyleClass().add("combo-box");
    if (values != null) {
      this.getItems().addAll(values);
    }
  }
}

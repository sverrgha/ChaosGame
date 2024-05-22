package edu.ntnu.idatt2003.view.components;

import java.util.List;
import java.util.function.BiConsumer;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;

/**
 * A styled combo box with a specified prompt text, width, height and values.
 * The combo box has a style class "combo-box" and the values are set to the specified values.
 * Goal: Create a styled combo box with a specified prompt text, width, height and values.
 */
public class ComboBoxFactory {

  /**
   * Private constructor to prevent instantiation.
   */
  private ComboBoxFactory() {
  }

  /**
   * Creates a styled combo box with a specified prompt text, width, height and values.
   * The combo box has a style class "combo-box" and the values are set to the specified values.
   *
   * @param promptText The text to display in the combo box when it is empty.
   * @param width      The preferred width of the combo box.
   * @param height     The preferred height of the combo box.
   * @param values     The values to set in the combo box.
   */
  public static <T> ComboBox<T> createComboBox(String promptText, int width, int height, List<T> values,
                                               BiConsumer<ComboBox<T>, ActionEvent> eventHandler) {
    return createComboBoxWithStyle(promptText, width, height, values, eventHandler);
  }

  public static <T> ComboBox<T> createComboBox(String promptText, int width, int height, List<T> values,
                                               BiConsumer<ComboBox<T>, ActionEvent> eventHandler, T defaultValue) {
    ComboBox<T> comboBox = createComboBoxWithStyle(promptText, width, height, values, eventHandler);
    comboBox.setValue(defaultValue);
    return comboBox;
  }

  /**
   * Creates a styled combo box with a specified prompt text, width, height, values and events
   * to be used in other methods in class.
   *
   * @param promptText   The text to display in the combo box when it is empty.
   * @param width        The preferred width of the combo box.
   * @param height       The preferred height of the combo box.
   * @param values       The values to set in the combo box.
   * @param eventHandler The event handler to set on the combo box.
   * @return The styled combo box.
   */
  private static <T> ComboBox<T> createComboBoxWithStyle(String promptText, int width, int height, List<T> values,
                                                         BiConsumer<ComboBox<T>, ActionEvent> eventHandler) {
    ComboBox<T> comboBox = new ComboBox<>();
    comboBox.setPromptText(promptText);
    comboBox.setPromptText(promptText);
    comboBox.setPrefSize(width, height);
    comboBox.getStyleClass().add("combo-box");
    comboBox.setOnAction(e -> eventHandler.accept(comboBox, e));
    if (values != null) {
      comboBox.getItems().addAll(values);
    }
    return comboBox;
  }
}

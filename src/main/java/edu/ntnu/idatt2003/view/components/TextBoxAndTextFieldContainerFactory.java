package edu.ntnu.idatt2003.view.components;

import static edu.ntnu.idatt2003.view.components.TextBoxFactory.createTextBox;

import javafx.scene.layout.HBox;

/**
 * A factory for creating containers containing a text box and text fields.
 * Goal: Create containers containing a text box and text fields.
 */
public class TextBoxAndTextFieldContainerFactory {

  /**
   * Private constructor to prevent instantiation.
   */
  private TextBoxAndTextFieldContainerFactory() {
  }

  /**
   * Creates a HBox containing a text box with the specified text and as many text fields
   * as the length of the doubles array, which gets the height and with given as parameters.
   *
   * @param spacing         Spacing between the elements.
   * @param textBoxText     The text for the text box.
   * @param textFieldWidth  The width of the text fields.
   * @param textFieldHeight The height of the text field.
   * @param textFieldCoords The coordinates for the TextFields.
   * @return The HBox containing the text box and text fields.
   */
  public static HBox createTextBoxWithTextFieldsContainer(int spacing,
                                                          String textBoxText,
                                                          int textFieldWidth,
                                                          int textFieldHeight,
                                                          double[] textFieldCoords) {
    HBox container = new HBox(spacing);
    container.getChildren().add(createTextBox(textBoxText));
    for (double coordinate : textFieldCoords) {
      container.getChildren().add(new StyledTextField(coordinate,
              textFieldWidth, textFieldHeight));
    }
    return container;
  }

  /**
   * Creates a HBox containing a text box with the specified text and as many text fields
   * as specified is given prompt text to, which gets the size given as parameters.
   *
   * @param spacing         Spacing between the elements.
   * @param textBoxText     The text to display in the text box.
   * @param textFieldWidth  The width of the text field.
   * @param textFieldHeight The height of the text field.
   * @param promptTexts     The prompt texts for the text fields.
   * @return The HBox containing the text box and text fields.
   */
  public static HBox createTextBoxWithTextFieldsContainer(int spacing,
                                                          String textBoxText,
                                                          int textFieldWidth,
                                                          int textFieldHeight,
                                                          String... promptTexts) {
    HBox container = new HBox(spacing);
    container.getChildren().add(createTextBox(textBoxText));
    for (String promptText : promptTexts) {
      container.getChildren().add(new StyledTextField(promptText,
              textFieldWidth, textFieldHeight));
    }
    return container;
  }


}

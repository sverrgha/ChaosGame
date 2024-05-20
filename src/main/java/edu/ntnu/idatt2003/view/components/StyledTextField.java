package edu.ntnu.idatt2003.view.components;

import javafx.scene.control.TextField;

public class StyledTextField extends TextField {
  public StyledTextField(String promptText, int width, int height) {
    super();
    this.setPromptText(promptText);
    this.setPrefSize(width, height);
    this.getStyleClass().add("text-field");
  }

}

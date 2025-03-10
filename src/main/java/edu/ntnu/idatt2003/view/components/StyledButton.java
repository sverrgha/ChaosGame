package edu.ntnu.idatt2003.view.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * A styled button with a specified tet width, height and event handler.
 * The button has a style class "button" and the event handler is set to the
 * specified event handler.
 * Goal: Create a styled button with a specified text, width, height and event handler.
 */
public class StyledButton extends Button {

  /**
   * Creates a styled button with a specified text, width, height and event handler.
   * The button has a style class "button" and the event handler is set to the
   * specified event handler.
   *
   * @param text         The text to display on the button.
   * @param width        The width of the button.
   * @param height       The height of the button.
   * @param eventHandler The event handler to set on the button.
   */
  public StyledButton(String text, int width, int height,
                      EventHandler<ActionEvent> eventHandler) {
    super(text);
    setPrefSize(width, height);
    getStyleClass().add("button");
    setOnAction(eventHandler);
  }

  /**
   * Creates a styled button with a specified text, height and event handler.
   * The button has a style class "button" and the event handler is set to the
   * specified event handler.
   * The button's width is set to grow with the HBox.
   *
   * @param text The text to display on the button.
   * @param height The height of the button.
   * @param eventHandler The event handler to set on the button.
   */
  public StyledButton(String text, int height, EventHandler<ActionEvent> eventHandler) {
    super(text);
    HBox.setHgrow(this, Priority.ALWAYS);
    setPrefHeight(height);
    getStyleClass().add("button");
    setOnAction(eventHandler);
  }
}

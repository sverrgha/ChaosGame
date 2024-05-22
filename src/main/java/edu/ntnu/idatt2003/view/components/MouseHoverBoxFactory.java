package edu.ntnu.idatt2003.view.components;

import java.util.function.BiConsumer;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * A factory for creating mouse hover boxes. The mouse hover boxes are styled with the "mouse-box"
 * style class, and has a label in the center.
 * Goal: Create mouse hover boxes.
 */
public class MouseHoverBoxFactory {
  /**
   * Private constructor to prevent instantiation.
   */
  private MouseHoverBoxFactory() {
  }

  /**
   * Creates a mouse hover box with the specified text and width, and event. The mouse hover box
   * is styled with the "mouse-box" style class, and has a label in the center. The box grows
   * vertically to fill available space, and has a preferred width. The mouse hover
   * box is configured to call the specified event handler when the mouse is moved over it.
   *
   * @param text         The text to display in the mouse hover box.
   * @param width        The preferred width of the mouse hover box.
   * @param eventHandler The event handler to call when the mouse is moved over the mouse hover box.
   * @return The mouse hover box.
   */
  public static Pane createMouseHoverBox(String text, double width,
                                         BiConsumer<Pane, MouseEvent> eventHandler) {
    Pane box = new StackPane();
    box.setPrefWidth(width);
    box.getStyleClass().add("mouse-box");
    box.getChildren().add(new Label(text));
    box.setOnMouseMoved(e -> eventHandler.accept(box, e));
    return box;
  }
}

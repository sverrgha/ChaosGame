package edu.ntnu.idatt2003.view;

import edu.ntnu.idatt2003.utils.Sizes;
import java.util.Objects;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * The MainPageView class is the main page of the application.
 * The class extends the Scene class from javaFX and creates a
 * GUI with a BorderPane layout.
 * The main page contains a button container with 7 buttons.
 */
public class MainPageView extends Scene {
  private final BorderPane root;
  private final StackPane pageContainer;

  /**
   * Constructs a MainPageView object with a BorderPane as the root node
   * and a specified width and height.
   * The view is styled with a style sheet.
   */
  public MainPageView() {
    super(new BorderPane(), Sizes.SCREEN_WIDTH, Sizes.SCREEN_HEIGHT);
    this.getStylesheets().add(Objects.requireNonNull(getClass()
            .getResource("/styles/mainPage.css")).toExternalForm());

    root = (BorderPane) this.getRoot();
    pageContainer = new StackPane();

    render();
  }

  /**
   * Renders the main page of the application.
   * The main page contains a button container with 7 buttons.
   */
  private void render() {
    root.getStyleClass().add("main-page");
    createPageContainer();
  }

  /**
   * Creates the page container with a button container.
   * The button container contains 7 buttons.
   */
  private void createPageContainer() {
    HBox buttonContainer = createButtonContainer();
    pageContainer.getChildren().add(buttonContainer);
    pageContainer.getStyleClass().add("page-container");
    pageContainer.setMaxHeight(Sizes.SCREEN_HEIGHT - 50);
    pageContainer.setMaxWidth(Sizes.SCREEN_WIDTH - 50);
    root.setCenter(pageContainer);
  }

  /**
   * Creates a button container with 7 buttons.
   *
   * @return The button container.
   */
  private HBox createButtonContainer() {
    HBox buttonContainer = new HBox();
    buttonContainer.getStyleClass().add("button-container");
    buttonContainer.setMaxHeight(Region.USE_PREF_SIZE);
    buttonContainer.setAlignment(Pos.CENTER);
    StackPane.setAlignment(buttonContainer, Pos.TOP_CENTER);
    for (int i = 0; i < 7; i++) {
      Button button = createButton("Button " + i);
      buttonContainer.getChildren().add(button);
    }
    return buttonContainer;
  }

  /**
   * Creates a button with the specified text.
   *
   * @param text The text of the button.
   * @return The button.
   */
  private Button createButton(String text) {
    Button button = new Button(text);
    button.getStyleClass().add("button");
    button.setPrefSize((Sizes.SCREEN_WIDTH - 50) / 7, 40);
    button.setOnAction(e -> {
      // Handle button click
    });
    return button;
  }
}

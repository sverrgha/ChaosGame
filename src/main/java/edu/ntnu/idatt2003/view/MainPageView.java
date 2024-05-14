package edu.ntnu.idatt2003.view;

import edu.ntnu.idatt2003.model.ChaosGame;
import edu.ntnu.idatt2003.model.ChaosGameDescriptionFactory;
import edu.ntnu.idatt2003.utils.Sizes;

import java.util.Objects;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
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

  private static final int BUTTON_WIDTH = (int) (Sizes.SCREEN_WIDTH - 50) / 6;
  private static final int BUTTON_HEIGHT = 40;


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
    ChaosGame game = new ChaosGame(ChaosGameDescriptionFactory.get(ChaosGameDescriptionFactory.descriptionTypeEnum.SIERPINSKI_TRIANGLE), 600,600);
    game.runSteps(1000);
    pageContainer.getChildren().add(new ImageView(ChaosImage.createImageFromCanvas(game.getCanvas())));
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
    buttonContainer.getChildren().addAll(
            createComboBox(),
            createButton(10),
            createButton(100),
            createButton(1000),
            createInputField(),
            createButton(-1)
    );

    return buttonContainer;
  }

  private ComboBox<String> createComboBox() {
    ComboBox<String> transformMenu = new ComboBox<>();
    transformMenu.getStyleClass().add("combo-box");
    transformMenu.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
    transformMenu.setPromptText("Transformation");
    transformMenu.getItems().addAll(
            "BarnsleyFern",
            "Sierpinski",
            "Julia"
    );
    transformMenu.setOnAction(e -> {
      // Handle combo box selection
    });
    return transformMenu;
  }

  /**
   * Creates a button with a specified number of steps. If -1 it's
   * a reset button
   *
   * @param steps The number of steps for the button.
   * @return The button.
   */
  private Button createButton(int steps) {
    Button button;
    if (steps == -1) {
      button = new Button("Reset");
    } else {
      button = new Button("Steps: " + steps);
    }
    button.getStyleClass().add("button");
    button.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
    button.setOnAction(e -> {
      // Handle button click
    });
    return button;
  }

  /**
   * Creates an input field for custom number of steps.
   *
   * @return The input field.
   */
  private TextField createInputField() {
    TextField inputField = new TextField();
    inputField.setPromptText("Steps");
    inputField.getStyleClass().add("input-field");
    inputField.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
    inputField.setPromptText("Steps");
    return inputField;
  }
}

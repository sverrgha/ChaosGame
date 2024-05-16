package edu.ntnu.idatt2003.view;

import edu.ntnu.idatt2003.controller.MainPageController;
import edu.ntnu.idatt2003.model.ChaosGameDescriptionFactory;
import edu.ntnu.idatt2003.model.ChaosGameObserver;
import edu.ntnu.idatt2003.utils.Sizes;

import java.io.File;
import java.util.Objects;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * The MainPageView class is the main page of the application.
 * The class extends the Scene class from javaFX and creates a
 * GUI with a BorderPane layout.
 * The main page contains a button container with 7 buttons.
 */
public class MainPageView extends Scene implements ChaosGameObserver {
  private final BorderPane root;
  private final StackPane pageContainer;
  private final MainPageController controller;
  private static final int PAGE_CONTAINER_MARGIN = 50;
  private static final int BUTTON_COUNT = 6;
  private static final int BUTTON_HEIGHT = 40;
  private static final int BUTTON_WIDTH = (int) (Sizes.SCREEN_WIDTH - PAGE_CONTAINER_MARGIN)
          / BUTTON_COUNT;


  /**
   * Constructs a MainPageView object with a BorderPane as the root node
   * and a specified width and height.
   * The view is styled with a style sheet.
   */
  public MainPageView(MainPageController mainPageController) {
    super(new BorderPane(), Sizes.SCREEN_WIDTH, Sizes.SCREEN_HEIGHT);
    this.getStylesheets().add(Objects.requireNonNull(getClass()
            .getResource("/styles/mainPage.css")).toExternalForm());

    root = (BorderPane) this.getRoot();
    pageContainer = new StackPane();
    this.controller = mainPageController;
  }

  /**
   * Renders the main page of the application.
   * The main page contains a button container with 7 buttons.
   */
  public void render() {
    root.getStyleClass().add("main-page");
    createPageContainer();
  }

  /**
   * Updates the page container by clearing the children and rendering the page.
   */
  public void update() {
    pageContainer.getChildren().clear();
    render();
  }

  /**
   * Creates the page container with a button container.
   * The button container contains 7 buttons.
   */
  private void createPageContainer() {
    pageContainer.getChildren().add(ChaosImage.createImageFromCanvas(controller
            .getGame().getCanvas()));

    HBox buttonContainer = createButtonContainer();
    pageContainer.getChildren().add(buttonContainer);
    pageContainer.getStyleClass().add("page-container");
    pageContainer.setMaxHeight(Sizes.SCREEN_HEIGHT - PAGE_CONTAINER_MARGIN);
    pageContainer.setMaxWidth(Sizes.SCREEN_WIDTH - PAGE_CONTAINER_MARGIN);
    root.setCenter(pageContainer);
  }

  /**
   * Creates a button container with a ComboBox to change the type
   * of transformation, buttons for running steps/resetting, the
   * transformation and input field to type custom amount of steps.
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

  private ComboBox<ChaosGameDescriptionFactory.descriptionTypeEnum> createComboBox() {
    ComboBox<ChaosGameDescriptionFactory.descriptionTypeEnum> transformMenu = new ComboBox<>();
    transformMenu.getStyleClass().add("combo-box");
    transformMenu.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
    transformMenu.setPromptText("Transformation");

    transformMenu.getItems().addAll(
            ChaosGameDescriptionFactory.descriptionTypeEnum.values()
    );
    transformMenu.setOnAction(e -> controller.changeTransformation(transformMenu.getValue()));

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

    button.setOnAction(e -> controller.runSteps(steps));

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
    inputField.setOnAction(e -> {
      try {
        controller.runSteps(Integer.parseInt(inputField.getText()));
      } catch (Exception ex) {
        showAlert("Invalid input. Please enter an integer.");
        inputField.clear();
      }
    });
    return inputField;
  }

  /**
   * Opens a file chooser dialog that enables the user to upload a custom
   * text file with a chaos game description. The file is then uploaded
   * by the controller. If an exception occurs, an alert is shown.
   */
  private void uploadFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Text Files", "*.txt"));

    Stage fileChooserStage = new Stage();
    File file = fileChooser.showOpenDialog(fileChooserStage);
    if (file != null) {
      controller.uploadFile(file);
    }
  }

  /**
   * Shows an alert with a specified message to give feedback to the user.
   *
   * @param message The message to be shown in the alert.
   */
  public void showAlert(String message) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  /**
   * Asks the user for confirmation with a specified message, to ensure
   * that the user wants to perform the specific action.
   *
   * @param message The message to be shown in the confirmation dialog.
   * @return True if the user confirms, false otherwise.
   */
  public boolean askConfirmation(String message) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
    alert.setHeaderText(null);
    alert.showAndWait();
    return alert.getResult() == ButtonType.YES;
  }
}

package edu.ntnu.idatt2003.view;

import edu.ntnu.idatt2003.controller.MainPageController;
import edu.ntnu.idatt2003.model.ChaosCanvas;
import edu.ntnu.idatt2003.model.ChaosGameDescriptionFactory;
import edu.ntnu.idatt2003.model.Vector2d;
import edu.ntnu.idatt2003.utils.Sizes;
import java.util.Objects;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * The MainPageView class is the main page of the application.
 * The class extends the Scene class from javaFX and creates a
 * GUI with a BorderPane layout.
 * The main page contains a button container with 7 buttons.
 */
public class MainPageView extends Scene {

  private final BorderPane root;
  private final StackPane pageContainer;
  private final MainPageController controller;
  private static final int BUTTON_WIDTH = (int) (Sizes.SCREEN_WIDTH - 50) / 6;
  private static final int BUTTON_HEIGHT = 40;


  /**
   * Constructs a MainPageView object with a BorderPane as the root node and a specified width and
   * height. The view is styled with a style sheet.
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
   * Renders the main page of the application. The main page contains a button container with 7
   * buttons.
   */
  public void render(ChaosCanvas chaosCanvas) {
    root.getStyleClass().add("main-page");
    createPageContainer(chaosCanvas);
  }

  /**
   * Creates the page container with a button container. The button container contains 7 buttons.
   */
  private void createPageContainer(ChaosCanvas chaosCanvas) {
    ImageView imageView = new ImageView(ChaosImage.createImageFromCanvas(chaosCanvas));

    HBox mainContent = new HBox();
    mainContent.setSpacing(20);
    mainContent.setAlignment(Pos.CENTER);
    mainContent.getChildren().addAll(imageView, createAddTransformationPanel());

    pageContainer.getChildren().add(mainContent);
    pageContainer.getChildren().add(createButtonContainer());
    pageContainer.getStyleClass().add("page-container");
    pageContainer.setMaxHeight(Sizes.SCREEN_HEIGHT - 50);
    pageContainer.setMaxWidth(Sizes.SCREEN_WIDTH - 50);
    root.setCenter(pageContainer);
  }

  /**
   * Creates a button container with a ComboBox to change the type of transformation, buttons for
   * running steps/resetting, the transformation and input field to type custom amount of steps.
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
   * Creates a button with a specified number of steps. If -1 it's a reset button
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
   * Shows an alert with a specified message to give feedback to the user.
   *
   * @param message The message to be shown in the alert.
   */
  private void showAlert(String message) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }



  /**
   * Creates the add panel for adding new transformations. It uses combobox to give the
   * alternative transformation type, buttons to save, cancel and add to file button. And text
   * fields to give start vector, input vector, and the necessary matrix.
   *
   * @return The add panel.
   */
  public VBox createAddTransformationPanel() {
    VBox addPanel = createMainPanel();
    ComboBox<TransformationType> transformationComboBox = createTransformationComboBox();
    HBox transformationInputField = createTransformationInputField(transformationComboBox);

    TextField startVectorField = createTextField("Enter start vector");
    TextField endVectorField = createTextField("Enter end vector");

    Button saveButton = createSaveButton(transformationComboBox, transformationInputField,
        startVectorField, endVectorField);
    Button cancelButton = createCancelButton(transformationInputField, startVectorField,
        endVectorField);
    Button addFileButton = createAddFileButton();

    addPanel.getChildren()
        .addAll(transformationComboBox, transformationInputField, startVectorField, endVectorField,
            saveButton, cancelButton, addFileButton);
    StackPane.setAlignment(addPanel, Pos.BOTTOM_LEFT);

    return addPanel;
  }

  /**
   * Creates the main panel.
   *
   * @return a VBox configured as the main panel.
   */

  private VBox createMainPanel() {
    VBox addPanel = new VBox(10);
    addPanel.getStyleClass().add("add-panel");
    addPanel.setAlignment(Pos.CENTER);
    return addPanel;
  }

  /**
   * Creates the transformation ComboBox.
   *
   * @return a ComboBox populated with transformation types.
   */

  private ComboBox<TransformationType> createTransformationComboBox() {
    ComboBox<TransformationType> transformationComboBox = new ComboBox<>();
    transformationComboBox.getItems().addAll(TransformationType.values());
    transformationComboBox.setPromptText("Select Transformation");
    return transformationComboBox;
  }
  /**
   * Enumeration of transformation types.
   */

  public enum TransformationType {
    JULIA,
    AFFINE,
  }

  /**
   * Creates the transformation input field container.
   *
   * @param transformationComboBox the ComboBox for selecting transformation types.
   * @return an HBox containing the input fields for transformations.
   */

  private HBox createTransformationInputField(ComboBox<TransformationType> transformationComboBox) {
    HBox transformationInputField = new HBox();
    transformationInputField.setSpacing(10);

    transformationComboBox.setOnAction(
        e -> updateTransformationFields(transformationComboBox, transformationInputField));
    updateTransformationFields(transformationComboBox, transformationInputField);

    return transformationInputField;
  }

  /**
   * Updates the transformation input fields based on the selected transformation type.
   *
   * @param comboBox  the ComboBox for selecting transformation types.
   * @param inputField the HBox containing the input fields for transformations.
   */

  private void updateTransformationFields(ComboBox<TransformationType> comboBox, HBox inputField) {
    inputField.getChildren().clear();
    if (comboBox.getValue() != null) {
      switch (comboBox.getValue()) {
        case JULIA:
          inputField.getChildren().add(juliaTransformationTextField());
          break;
        case AFFINE:
          inputField.getChildren().add(affineTransformationTextFields());
          break;
      }
    }
  }

  /**
   * Creates a TextField with the specified prompt text.
   *
   * @param promptText the prompt text for the TextField.
   * @return a configured TextField.
   */

  private TextField createTextField(String promptText) {
    TextField textField = new TextField();
    textField.setPromptText(promptText);
    return textField;
  }

  /**
   * Creates the save button.
   *
   * @param transformationComboBox   the ComboBox for selecting transformation types.
   * @param transformationInputField the HBox containing the input fields for transformations.
   * @param startVectorField         the TextField for the start vector.
   * @param endVectorField           the TextField for the end vector.
   * @return a configured save Button.
   */

  private Button createSaveButton(ComboBox<TransformationType> transformationComboBox,
      HBox transformationInputField, TextField startVectorField, TextField endVectorField) {
    Button saveButton = new Button("Save");
    saveButton.getStyleClass().add("button");
    saveButton.setOnAction(e -> {
      // Implement the save logic here
      String selectedTransformation = transformationComboBox.getValue().toString();
      //Vector2d startVector = new Vector2d()
      // Get start and end vectors
      // Save the transformation using controller
    });
    return saveButton;
  }

  /**
   * Creates the cancel button.
   *
   * @param transformationInputField the HBox containing the input fields for transformations.
   * @param startVectorField         the TextField for the start vector.
   * @param endVectorField           the TextField for the end vector.
   * @return a configured cancel Button.
   */

  private Button createCancelButton(HBox transformationInputField, TextField startVectorField,
      TextField endVectorField) {
    Button cancelButton = new Button("Cancel");
    cancelButton.getStyleClass().add("button");
    cancelButton.setOnAction(e -> {
      transformationInputField.getChildren().clear();
      startVectorField.clear();
      endVectorField.clear();
    });
    return cancelButton;
  }

  /**
   * Creates the add file button.
   *
   * @return a configured add file Button.
   */

  private Button createAddFileButton() {
    Button addFileButton = new Button("Add File");
    addFileButton.getStyleClass().add("button");
    addFileButton.setOnAction(e -> {
      // Code to add file
    });
    return addFileButton;
  }

  /**
   * Creates a VBox containing the input field for Julia transformation.
   *
   * @return a VBox configured for Julia transformation input.
   */

  private VBox juliaTransformationTextField() {
    VBox transformationVBox = new VBox(10);

    HBox vectorHbox = vectorHBox("Real part", "Imaginary part");

    transformationVBox.getChildren().add(vectorHbox);

    return transformationVBox;
  }

  /**
   * Creates a VBox containing the input fields for affine transformations.
   *
   * @return a VBox configured for affine transformation inputs.
   */

  private VBox affineTransformationTextFields() {
    VBox transformationsBox = new VBox(5);
    Button addTransformationButton = new Button("Add Transformation");

    addTransformationButton.setOnAction(e -> {
      HBox matrixHBox = matrixHBox("X0", "Y0", "X1", "Y1");
      transformationsBox.getChildren().add(matrixHBox);
    });

    transformationsBox.getChildren().add(addTransformationButton);
    return transformationsBox;
  }

  private HBox vectorHBox (String namex0, String namey0) {
    HBox transformationHbox = new HBox(10);
    TextField x0 = new TextField();
    x0.setPromptText(namex0);

    TextField y0 = new TextField();
    y0.setPromptText(namey0);

    transformationHbox.getChildren().addAll(x0, y0);
    return transformationHbox;
  }

  private HBox matrixHBox (String namex0, String namey0, String namex1, String namey1) {
    HBox transformationHbox = new HBox(10);

    TextField x0 = new TextField();
    x0.setPromptText(namex0);

    TextField y0 = new TextField();
    y0.setPromptText(namey0);

    TextField x1 = new TextField();
    x1.setPromptText(namex1);

    TextField y1 = new TextField();
    y1.setPromptText(namey1);

    transformationHbox.getChildren().addAll(x0, y0, x1, y1);

    return transformationHbox;
  }
}

package edu.ntnu.idatt2003.view;

import edu.ntnu.idatt2003.controller.MainPageController;
import edu.ntnu.idatt2003.model.AffineTransform2D;
import edu.ntnu.idatt2003.model.ChaosGameDescriptionFactory;
import edu.ntnu.idatt2003.model.ChaosGameObserver;
import edu.ntnu.idatt2003.model.Complex;
import edu.ntnu.idatt2003.model.JuliaTransform;
import edu.ntnu.idatt2003.model.Matrix2x2;
import edu.ntnu.idatt2003.model.Transform2D;
import edu.ntnu.idatt2003.model.Vector2d;
import edu.ntnu.idatt2003.utils.Sizes;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
  private static TextField xField;
  private static TextField yField;



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
    pageContainer.getChildren().clear();
    createPageContainer();
  }

  /**
   * Updates the page container by clearing the children and rendering the page.
   */
  public void update() {
    render();
  }

  /**
   * Creates the page container with a button container.
   * The button container contains 7 buttons.
   */
  private void createPageContainer() {
    HBox contentContainer = new HBox(
        createAddTransformationPanel(),
        ChaosImage.createImageFromCanvas(controller.getGame().getCanvas()), 
        mouseBox()
        );
    pageContainer.getChildren().addAll(contentContainer, createButtonContainer());
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
            createCustomComboBox(),
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

  private ComboBox<String> createCustomComboBox() {
    ComboBox<String> customMenu = new ComboBox<>();
    customMenu.getStyleClass().add("combo-box");
    customMenu.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
    customMenu.setPromptText("Custom Transformations");

    List<String> customTransformations = controller.getCustomTransformation();

    if (customTransformations != null && !customTransformations.isEmpty()) {
      customMenu.getItems().addAll(customTransformations);
    }

    customMenu.setOnAction(e -> controller.changeCustomTransformation(customMenu.getValue()));

    return customMenu;
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


  /**
   * Creates the add panel for adding new transformations. It uses combobox to give the alternative
   * transformation type, buttons to save, cancel, and add to file button, and text fields to give
   * start vector, input vector, and the necessary matrix.
   *
   * @return The add panel.
   */
  public VBox createAddTransformationPanel() {
    VBox addPanel = createMainPanel();
    TextField transformationName = createTextField("transformation name");
    ComboBox<TransformationType> transformationComboBox = createTransformationComboBox();
    HBox transformationInputField = createTransformationInputField(transformationComboBox);
    HBox startVectorField = createVectorHbox(Arrays.asList("x0", "y0"));
    HBox endVectorField = createVectorHbox(Arrays.asList("x0", "y0"));

    addPanel.getChildren().addAll(
        transformationName,
        transformationComboBox,
        transformationInputField,
        startVectorField,
        endVectorField,
        createButton("Save", e -> saveTransformation(
            transformationName, transformationComboBox, transformationInputField, startVectorField,
            endVectorField)),
        createButton("Cancel", e -> render()),
        createButton("Add File", e -> uploadFile()));
    StackPane.setAlignment(addPanel, Pos.BOTTOM_LEFT);

    return addPanel;
  }

  /**
   * Creates a generic VBox panel with spacing and alignment.
   *
   * @return a VBox configured as the main panel.
   */
  private VBox createMainPanel() {
    VBox panel = new VBox(10);
    panel.getStyleClass().add("add-panel");
    panel.setAlignment(Pos.CENTER);
    return panel;
  }

  /**
   * Creates a ComboBox populated with transformation types.
   *
   * @return a ComboBox populated with transformation types.
   */
  private ComboBox<TransformationType> createTransformationComboBox() {
    ComboBox<TransformationType> comboBox = new ComboBox<>();
    comboBox.getItems().addAll(TransformationType.values());
    comboBox.setPromptText("Select Transformation");
    return comboBox;
  }

  /**
   * Enum representing the types of transformations.
   */
  public enum TransformationType {
    JULIA, AFFINE
  }

  /**
   * Creates the transformation input field container and sets up its update behavior.
   *
   * @param transformationComboBox the ComboBox for selecting transformation types.
   * @return an HBox containing the input fields for transformations.
   */
  private HBox createTransformationInputField(ComboBox<TransformationType> transformationComboBox) {
    HBox inputField = new HBox(10);
    transformationComboBox.setOnAction(
        e -> updateTransformationFields(transformationComboBox, inputField));
    updateTransformationFields(transformationComboBox, inputField);
    return inputField;
  }

  /**
   * Updates the transformation input fields based on the selected transformation type.
   *
   * @param comboBox the ComboBox for selecting transformation types.
   * @param inputField the HBox containing the input fields for transformations.
   */
  private void updateTransformationFields(ComboBox<TransformationType> comboBox, HBox inputField) {
    inputField.getChildren().clear();
    if (comboBox.getValue() != null) {
      inputField.getChildren().add(createTransformationVbox(
          comboBox.getValue() == TransformationType.AFFINE,
          comboBox.getValue() == TransformationType.JULIA
              ? Arrays.asList("Real part", "Imaginary part") : null));
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
   * Creates a generic button with the specified label and action.
   *
   * @param label the label for the button.
   * @param eventHandler the event handler for the button action.
   * @return a configured Button.
   */
  private Button createButton(String label, EventHandler<ActionEvent> eventHandler) {
    Button button = new Button(label);
    button.getStyleClass().add("button");
    button.setOnAction(eventHandler);
    return button;
  }

  /**
   * Creates a VBox containing input fields for transformations.
   *
   * @param isAffine indicates if the VBox is for affine transformations.
   * @param promptTexts list of prompt texts for the TextFields.
   * @return a VBox configured with input TextFields.
   */
  private VBox createTransformationVbox(boolean isAffine, List<String> promptTexts) {
    VBox vbox = new VBox(isAffine ? 5 : 10);
    if (isAffine) {
      vbox.getChildren().add(createButton("Add Transformation",
          e -> vbox.getChildren().add(createVectorHbox(
              Arrays.asList("X0", "Y0", "X1", "Y1", "V0", "V1")))));
    } else if (promptTexts != null) {
      vbox.getChildren().add(createVectorHbox(promptTexts));
    }
    return vbox;
  }

  /**
   * Creates an HBox containing input fields for transformations.
   *
   * @param promptTexts list of prompt texts for the TextFields.
   * @return an HBox configured with input TextFields.
   */
  private HBox createVectorHbox(List<String> promptTexts) {
    HBox hbox = new HBox(10);
    for (String promptText : promptTexts) {
      hbox.getChildren().add(createTextField(promptText));
    }
    return hbox;
  }

  /**
   * Saves the transformation with the given parameters.
   *
   * @param transformationName the name of the transformation.
   * @param transformationComboBox the ComboBox for selecting transformation types.
   * @param transformationInputField the HBox containing the input fields for transformations.
   * @param startVectorField the TextField for the start vector.
   * @param endVectorField the TextField for the end vector.
   */
  private void saveTransformation(TextField transformationName,
      ComboBox<TransformationType> transformationComboBox, HBox transformationInputField,
      HBox startVectorField, HBox endVectorField) {
    List<Transform2D> transformations = getInputInformation(transformationComboBox.getValue(),
        transformationInputField);
    Vector2d startVector = getInputVector(startVectorField);
    Vector2d endVector = getInputVector(endVectorField);
    controller.addCustomTransformation(
        startVector, endVector, transformations, transformationName.getText());
  }

  /**
   * Retrieves input information based on the selected transformation type.
   *
   * @param selectedTransformation the selected transformation type.
   * @param transformationInputField the HBox containing the input fields for transformations.
   * @return a list of Transform2D objects.
   */
  private List<Transform2D> getInputInformation(TransformationType selectedTransformation,
      HBox transformationInputField) {
    if (selectedTransformation == TransformationType.JULIA) {
      return getJuliaTransformation(transformationInputField);
    } else if (selectedTransformation == TransformationType.AFFINE) {
      return getAffineTransformation(transformationInputField);
    }
    return new ArrayList<>();
  }

  /**
   * Retrieves input information for the Julia transformation.
   *
   * @param transformationInputField the HBox containing the input fields for the Julia
   *                                 transformation.
   * @return a list of Transform2D objects for the Julia transformation.
   */
  private List<Transform2D> getJuliaTransformation(HBox transformationInputField) {
    List<Transform2D> list = new ArrayList<>();
    if (!transformationInputField.getChildren().isEmpty()) {
      VBox juliaVbox = (VBox) transformationInputField.getChildren().get(0);
      HBox juliaFields = (HBox) juliaVbox.getChildren().get(0);
      list.add(new JuliaTransform(
          new Complex(parseDoubleFromTextField(juliaFields, 0),
              parseDoubleFromTextField(juliaFields, 1)), 1));
    }
    return list;
  }

  /**
   * Retrieves input information for the Affine transformation.
   *
   * @param transformationInputField the HBox containing the input fields for the Affine
   *                                 transformation.
   * @return a list of Transform2D objects for the Affine transformation.
   */
  private List<Transform2D> getAffineTransformation(HBox transformationInputField) {
    List<Transform2D> list = new ArrayList<>();
    if (!transformationInputField.getChildren().isEmpty()) {
      VBox affineVbox = (VBox) transformationInputField.getChildren().get(0);
      for (Node node : affineVbox.getChildren().subList(1, affineVbox.getChildren().size())) {
        HBox matrixFields = (HBox) node;

        double x0 = parseDoubleFromTextField(matrixFields, 0);
        double y0 = parseDoubleFromTextField(matrixFields, 1);
        double x1 = parseDoubleFromTextField(matrixFields, 2);
        double y1 = parseDoubleFromTextField(matrixFields, 3);
        double v0 = parseDoubleFromTextField(matrixFields, 4);
        double v1 = parseDoubleFromTextField(matrixFields, 5);

        Matrix2x2 matrix2x2 = new Matrix2x2(x0, y0, x1, y1);
        Vector2d vector2d = new Vector2d(v0, v1);
        list.add(new AffineTransform2D(matrix2x2, vector2d));
      }
    }
    return list;
  }

  /**
   * Retrieves the input vector from an HBox.
   *
   * @param vectorHbox the HBox containing the input fields for the vector.
   * @return a Vector2d object representing the input vector.
   */
  private Vector2d getInputVector(HBox vectorHbox) {
    return new Vector2d(parseDoubleFromTextField(vectorHbox, 0),
        parseDoubleFromTextField(vectorHbox, 1));
  }

  /**
   * Parses a double value from the TextField at the specified index in the HBox.
   *
   * @param hbox the HBox containing the TextField.
   * @param index the index of the TextField in the HBox.
   * @return the parsed double value.
   */
  private double parseDoubleFromTextField(HBox hbox, int index) {
    return Double.parseDouble(((TextField) hbox.getChildren().get(index)).getText());
  }

  /**
   * Creates a VBox containing a Pane that tracks mouse movement and two TextFields
   * that display the normalized mouse coordinates within the Pane.
   * The Pane is 400x400 in size. When the mouse is moved within the Pane, the
   * coordinates are normalized to the range [-1, 1] and the values are updated
   * in the TextFields.
   *
   * @return a VBox containing the Pane and the TextFields
   */
  private VBox mouseBox() {
    Pane box = new Pane();
    box.setPrefSize(400, 400);

    xField = new TextField();
    yField = new TextField();

    VBox vbox = new VBox(xField, yField);
    vbox.setSpacing(10);

    box.setOnMouseMoved(e -> {
      double mouseX = e.getX();
      double mouseY = e.getY();
      double normalizedX = (mouseX / box.getWidth()) * 2 - 1;
      double normalizedY = (mouseY / box.getHeight()) * 2 - 1;
      updateValues(normalizedX, normalizedY);
    });

    VBox root = new VBox(box, vbox);
    root.setSpacing(10);

    return root;
  }

  /**
   * Updates the displayed values in the TextFields and triggers a change in the
   * Julia transformation dynamically.
   *
   * @param x the normalized x-coordinate in the range [-1, 1]
   * @param y the normalized y-coordinate in the range [-1, 1]
   */
  private void updateValues(double x, double y) {
    controller.changeJuliaTransformationDynamic(x, y);
    xField.setText(String.format("X: %.1f", x));
    yField.setText(String.format("Y: %.1f", y));
  }

}

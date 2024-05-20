package edu.ntnu.idatt2003.view;

import edu.ntnu.idatt2003.controller.MainPageController;
import edu.ntnu.idatt2003.model.ChaosGameDescriptionFactory;
import edu.ntnu.idatt2003.model.ChaosGameObserver;
import edu.ntnu.idatt2003.utils.Sizes;
import edu.ntnu.idatt2003.view.components.ChaosImage;
import edu.ntnu.idatt2003.view.components.StyledButton;
import edu.ntnu.idatt2003.view.components.StyledComboBox;
import edu.ntnu.idatt2003.view.components.StyledTextField;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
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
  private final MainPageController controller;
  private static final int BUTTON_COUNT = 7;
  private static final int COMPONENT_HEIGHT = 40;
  private static final int BUTTON_WIDTH = (int) (Sizes.SCREEN_WIDTH) / BUTTON_COUNT;
  private static final int DEFAULT_SPACING = 10;
  private TextField x0Field;
  private TextField x1Field;


  /**
   * Constructs a MainPageView object with a BorderPane as the root node
   * and a specified width and height.
   * The view is styled with a style sheet.
   */
  public MainPageView(MainPageController mainPageController) {
    super(new BorderPane(), Sizes.SCREEN_WIDTH, Sizes.SCREEN_HEIGHT);
    this.getStylesheets().add(Objects.requireNonNull(getClass()
            .getResource("/styles/mainPage.css")).toExternalForm());
    this.getStylesheets().add(Objects.requireNonNull(getClass()
            .getResource("/styles/components.css")).toExternalForm());

    root = (BorderPane) this.getRoot();
    this.controller = mainPageController;
  }

  /**
   * Renders the main page of the application.
   * The main page contains a button container with 7 buttons.
   */
  public void render() {
    root.getStyleClass().add("main-page");
    root.getChildren().clear();
    createPage();
  }

  /**
   * Updates the page container by clearing the children and rendering the page.
   */
  public void update() {
    render();
  }

  /**
   * Creates the page with a button container at the top and an edit panel,
   * dynamic julia container and the canvas at the bottom.
   */
  private void createPage() {
    root.setTop(createButtonContainer());
    root.setCenter(createCenterContainer());
  }

  /**
   * Creates the center container with the edit panel and the dynamic julia container.
   *
   * @return The center container.
   */
  private HBox createCenterContainer() {
    HBox centerContainer = new HBox(DEFAULT_SPACING);
    centerContainer.getStyleClass().add("center-container");
    centerContainer.getChildren().addAll(
            createEditPanelAndDynamicJuliaContainer(),
            ChaosImage.createImageFromCanvas(controller.getGame().getCanvas())
    );
    return centerContainer;
  }

  /**
   * Creates the edit panel and the dynamic julia container.
   *
   * @return The edit panel and the dynamic julia container.
   */
  private VBox createEditPanelAndDynamicJuliaContainer() {
    VBox editPanelAndDynamicJuliaContainer = new VBox(DEFAULT_SPACING);
    HBox.setHgrow(editPanelAndDynamicJuliaContainer, Priority.ALWAYS);

    editPanelAndDynamicJuliaContainer.getStyleClass().add("canvas-and-dynamic-julia-container");
    editPanelAndDynamicJuliaContainer.getChildren().addAll(
            createAddTransformationPanel(),
            createVboxSpacing(),
            createDynamicJuliaContainer()
    );
    return editPanelAndDynamicJuliaContainer;
  }

  /**
   * Creates the dynamic julia container with the julia information and the mouse box.
   *
   * @return The dynamic julia container.
   */
  private HBox createDynamicJuliaContainer() {
    HBox dynamicJuliaContainer = new HBox(DEFAULT_SPACING);
    dynamicJuliaContainer.setAlignment(Pos.BOTTOM_CENTER);
    x0Field = new StyledTextField("x: ", 100, 20);
    x1Field = new StyledTextField("y: ", 100, 20);
    if (controller.transformationIsJulia()) {
      VBox juliaInformationContainer = new VBox(DEFAULT_SPACING);
      HBox.setHgrow(juliaInformationContainer, Priority.ALWAYS);
      juliaInformationContainer.getChildren().addAll(
              createTextBox("Total steps: " + controller.getGame().getTotalSteps()),
              new HBox(10, createTextBox("X-value: "), x0Field),
              new HBox(10, createTextBox("Y-value: "), x1Field)
      );
      dynamicJuliaContainer.getChildren().addAll(
              juliaInformationContainer,
              mouseBox()
      );
    }
    return dynamicJuliaContainer;
  }

  /**
   * Creates a Region that fills the remaining space in a VBoc.
   *
   * @return The Region that fills the VBox.
   */
  private Region createVboxSpacing() {
    Region spacing = new Region();
    VBox.setVgrow(spacing, Priority.ALWAYS);
    return spacing;
  }

  /**
   * Creates a text box with the specified text. The text box is styled with the
   * "text-box" style class. The text box is a StackPane with a Label as a child.
   * The text box is configured to grow horizontally.
   *
   * @param text The text to display in the text box.
   * @return The text box.
   */
  private StackPane createTextBox(String text) {
    StackPane textBox = new StackPane();
    textBox.getStyleClass().add("text-box");
    HBox.setHgrow(textBox, Priority.ALWAYS);
    textBox.getChildren().add(new Label(text));
    return textBox;
  }

  /**
   * Creates a HBox containing a text box with the specified text and as many text fields
   * as specified is given prompt text to, which gets the size given as parameters.
   *
   * @param text The text to display in the text box.
   * @param width The width of the text field.
   * @param height The height of the text field.
   * @param promptTexts The prompt texts for the text fields.
   * @return The HBox containing the text box and text fields.
   */
  private HBox createTextBoxWithTextField(String text, int width,
                                          int height, String... promptTexts) {
    HBox container = new HBox(DEFAULT_SPACING);
    container.getChildren().add(createTextBox(text));
    for (String promptText : promptTexts) {
      container.getChildren().add(new StyledTextField(promptText, width, height));
    }
    return container;
  }

  /**
   * Creates a HBox containing a text box with the specified text and as many text fields
   * as the length of the doubles array, which gets the height and with given as parameters.
   *
   * @param text The text to display in the text box.
   * @param width The width of the text field.
   * @param height The height of the text field.
   * @param coords The text for the StyledTextField.
   * @return The HBox containing the text box and text fields.
   */
  private HBox createTextBoxWithTextField(String text, int width, int height, double[] coords) {
    HBox container = new HBox(DEFAULT_SPACING);
    container.getChildren().add(createTextBox(text));
    for (double coordinate : coords) {
      container.getChildren().add(new StyledTextField(coordinate, width, height));
    }
    return container;
  }


  /**
   * Creates a button container with a ComboBox to change the type
   * of transformation, buttons for running steps/resetting, the
   * transformation and input field to type custom amount of steps.
   *
   * @return The button container.
   */

  private HBox createButtonContainer() {
    HBox buttonContainer = new HBox(DEFAULT_SPACING);
    buttonContainer.getStyleClass().add("button-container");
    buttonContainer.setMaxHeight(Region.USE_PREF_SIZE);
    buttonContainer.setAlignment(Pos.CENTER);
    StackPane.setAlignment(buttonContainer, Pos.TOP_CENTER);

    buttonContainer.getChildren().addAll(
            createComboBox(),
            createCustomComboBox(),
            new StyledButton("10 Steps", BUTTON_WIDTH, COMPONENT_HEIGHT,
                    e -> controller.runSteps(10)),
            new StyledButton("100 Steps", BUTTON_WIDTH, COMPONENT_HEIGHT,
                    e -> controller.runSteps(100)),
            new StyledButton("1000 Steps", BUTTON_WIDTH, COMPONENT_HEIGHT,
                    e -> controller.runSteps(1000)),
            createStepsTextField(),
            new StyledButton("Reset", BUTTON_WIDTH, COMPONENT_HEIGHT,
                    e -> controller.runSteps(-1))
    );

    return buttonContainer;
  }

  private ComboBox<ChaosGameDescriptionFactory.descriptionTypeEnum> createComboBox() {
    StyledComboBox<ChaosGameDescriptionFactory.descriptionTypeEnum> transformMenu =
            new StyledComboBox<>("Transformation", BUTTON_WIDTH, COMPONENT_HEIGHT,
                    Arrays.asList(ChaosGameDescriptionFactory.descriptionTypeEnum.values())
            );
    transformMenu.setOnAction(e -> controller.changeTransformation(transformMenu.getValue()));
    return transformMenu;
  }

  private StyledTextField createStepsTextField() {
    StyledTextField stepsField = new StyledTextField("Steps", BUTTON_WIDTH, COMPONENT_HEIGHT);
    stepsField.getStyleClass().set(0, "button");
    stepsField.setOnAction(e -> {
      controller.runCustomSteps(stepsField.getText());
      stepsField.clear();
    });
    return stepsField;
  }

  /**
   * Creates a StyledComboBox with setOnAction to change the transformation based on
   * the selected value.
   *
   * @return The ComboBox with custom transformations.
   */
  private ComboBox<String> createCustomComboBox() {
    StyledComboBox<String> customMenu = new StyledComboBox<>("Custom Transformations",
            BUTTON_WIDTH, COMPONENT_HEIGHT, controller.getCustomTransformation());
    customMenu.setOnAction(e -> controller.changeTransformation(customMenu.getValue()));
    return customMenu;
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
    TextField transformationName = new StyledTextField("Transformation name", 210, 20);
    StyledComboBox<TransformationType> transformationComboBox = new StyledComboBox<>(
            "Transformation Type", 210, 40, Arrays.asList(TransformationType.values())
    );
    transformationComboBox.setValue(controller.transformationIsJulia()
            ? TransformationType.JULIA : TransformationType.AFFINE);

    HBox startVectorField = createTextBoxWithTextField("Min vector:", 100, 20,
            controller.getMinCoordsX());
    HBox endVectorField = createTextBoxWithTextField("Max vector:", 100, 20,
            controller.getMaxCoordsX());
    VBox transformationVbox = createTransformationVbox();

    addPanel.getChildren().addAll(
            new HBox(DEFAULT_SPACING,
                    createTextBox("Transformation name:"),
                    transformationName
            ),
            new HBox(DEFAULT_SPACING,
                    createTextBox("Transformation type:"),
                    transformationComboBox
            ),
            startVectorField,
            endVectorField,
            transformationVbox,
            new HBox(
                    DEFAULT_SPACING,
                    new StyledButton("Save", 20,
                            e -> saveTransformation(transformationName, transformationComboBox,
                                    transformationVbox, startVectorField, endVectorField)),
                    new StyledButton("Cancel", 20, e -> render()),
                    new StyledButton("Add File", 100, 20, e -> uploadFile())
            )

    );

    StackPane.setAlignment(addPanel, Pos.BOTTOM_LEFT);

    return addPanel;
  }

  /**
   * Creates a VBox containing input fields for transformations.
   *
   * @return a VBox configured with input TextFields.
   */
  private VBox createTransformationVbox() {
    VBox vbox = new VBox(10);
    vbox.setFillWidth(true);
    vbox.setAlignment(Pos.CENTER);
    if (!controller.transformationIsJulia()) {
      vbox.getChildren().add(
              new StyledButton("Add Transformation", 250, 20,
                      e -> vbox.getChildren().add(createTextBoxWithTextField("Transformation:",
                              55, 20, "a00", "a01", "a10", "a11", "v0", "v1"))
              )
      );
    }
    for (double[] coords : controller.getTransformList()) {
      vbox.getChildren().add(createTextBoxWithTextField("Transformation:",
              55, 20, coords));
    }
    return vbox;
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
   * Enum representing the types of transformations.
   */
  public enum TransformationType {
    JULIA, AFFINE
  }

  /**
   * Saves the transformation with the given parameters.
   *
   * @param transformationName     the name of the transformation.
   * @param transformationComboBox the ComboBox for selecting transformation types.
   * @param transformationVbox     the HBox containing the input fields for transformations.
   * @param startVectorField       the TextField for the start vector.
   * @param endVectorField         the TextField for the end vector.
   */
  private void saveTransformation(TextField transformationName,
                                  ComboBox<TransformationType> transformationComboBox,
                                  VBox transformationVbox, HBox startVectorField,
                                  HBox endVectorField) {
    List<String[]> transformations = getInputInformation(transformationComboBox.getValue(),
            transformationVbox);
    String[] startVector = getInputVector(startVectorField);
    String[] endVector = getInputVector(endVectorField);
    controller.addCustomTransformation(
            startVector, endVector, transformations, transformationName.getText());
  }

  /**
   * Retrieves input information based on the selected transformation type.
   *
   * @param selectedTransformation the selected transformation type.
   * @param transformationVbox     the HBox containing the input fields for transformations.
   * @return a list of Transform2D objects.
   */
  private List<String[]> getInputInformation(TransformationType selectedTransformation,
                                             VBox transformationVbox) {
    if (selectedTransformation == TransformationType.JULIA) {
      return getJuliaTransformation(transformationVbox);
    } else if (selectedTransformation == TransformationType.AFFINE) {
      return getAffineTransformation(transformationVbox);
    }
    return new ArrayList<>();
  }

  /**
   * Retrieves input information for the Julia transformation.
   *
   * @param transformationVbox the HBox containing the input fields for the Julia
   *                           transformation.
   * @return a list of Transform2D objects for the Julia transformation.
   */
  private List<String[]> getJuliaTransformation(VBox transformationVbox) {
    List<String[]> list = new ArrayList<>();
    if (!transformationVbox.getChildren().isEmpty()) {
      HBox juliaFields = (HBox) transformationVbox.getChildren().get(0);
      list.add(new String[]{((TextField) juliaFields.getChildren().get(0)).getText(),
              ((TextField) juliaFields.getChildren().get(0)).getText()});
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
  private List<String[]> getAffineTransformation(VBox transformationInputField) {
    List<String[]> list = new ArrayList<>();
    if (!transformationInputField.getChildren().isEmpty()) {
      for (Node node : transformationInputField.getChildren()
              .subList(1, transformationInputField.getChildren().size())) {
        HBox matrixFields = (HBox) node;
        String[] coordinateList = new String[matrixFields.getChildren().size() - 1];
        for (int i = 1; i < matrixFields.getChildren().size(); i++) {
          coordinateList[i - 1] = ((TextField) matrixFields.getChildren().get(i)).getText();
        }
        list.add(coordinateList);
      }
    }
    return list;
  }

  /**
   * Retrieves the input vector from an HBox.
   *
   * @param vectorHBox the HBox containing the input fields for the vector.
   * @return a Vector2d object representing the input vector.
   */
  private String[] getInputVector(HBox vectorHBox) {
    return new String[]{((TextField) vectorHBox.getChildren().get(1)).getText(),
            ((TextField) vectorHBox.getChildren().get(2)).getText()};
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
  private Pane mouseBox() {
    StackPane box = new StackPane();
    box.setPrefWidth(125);
    box.getStyleClass().add("mouse-box");

    box.getChildren().add(new Label("Hover over me!"));

    box.setOnMouseMoved(e -> {
      double mouseX = e.getX();
      double mouseY = e.getY();
      double normalizedX = (mouseX / box.getWidth()) * 2 - 1;
      double normalizedY = (mouseY / box.getHeight()) * 2 - 1;
      updateValues(normalizedX, normalizedY);
    });

    return box;
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
    x0Field.setText(String.format(Locale.ENGLISH, "%.5f", x));
    x1Field.setText(String.format(Locale.ENGLISH, "%.5f", y));
  }

}

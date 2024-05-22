package edu.ntnu.idatt2003.view;

import static edu.ntnu.idatt2003.view.components.ComboBoxFactory.createComboBox;
import static edu.ntnu.idatt2003.view.components.MouseHoverBoxFactory.createMouseHoverBox;
import static edu.ntnu.idatt2003.view.components.TextBoxAndTextFieldContainerFactory.createTextBoxWithTextFieldsContainer;
import static edu.ntnu.idatt2003.view.components.TextBoxFactory.createTextBox;
import static edu.ntnu.idatt2003.view.components.TextFieldFactory.createTextField;

import edu.ntnu.idatt2003.controller.MainPageController;
import edu.ntnu.idatt2003.model.ChaosGameDescriptionFactory;
import edu.ntnu.idatt2003.model.ChaosGameObserver;
import edu.ntnu.idatt2003.utils.Sizes;
import edu.ntnu.idatt2003.view.components.ChaosImage;
import edu.ntnu.idatt2003.view.components.StyledButton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

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
  private final EventHandler eventHandler;
  private TransformationType selectedTransformation;

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
    this.eventHandler = new EventHandler(mainPageController, this);
    root = (BorderPane) this.getRoot();
    this.selectedTransformation = TransformationType.AFFINE;
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
            createAddFractalPanel(),
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
    TextField x0Field = createTextField("x: ", 100, 20);
    TextField x1Field = createTextField("y: ", 100, 20);
    if (controller.fractalIsJulia()) {
      VBox juliaInformationContainer = new VBox(DEFAULT_SPACING);
      HBox.setHgrow(juliaInformationContainer, Priority.ALWAYS);
      juliaInformationContainer.getChildren().addAll(
              createTextBox("Total steps: " + controller.getGame().getTotalSteps()),
              new HBox(10, createTextBox("X-value: "), x0Field),
              new HBox(10, createTextBox("Y-value: "), x1Field)
      );
      dynamicJuliaContainer.getChildren().addAll(
              juliaInformationContainer,
              createMouseHoverBox("Hover over me!", 125,
                      (box, e) -> eventHandler.handleMouseHoverOnMouseBox(x0Field, x1Field, box, e))
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
            createComboBox("Fractals", BUTTON_WIDTH, COMPONENT_HEIGHT,
                    Arrays.asList(ChaosGameDescriptionFactory.DescriptionTypeEnum.values()),
                    (box, e) -> eventHandler.handleChangeFractal(box)),
            createComboBox("Custom fractals", BUTTON_WIDTH, COMPONENT_HEIGHT,
                    controller.getCustomFractalNames(),
                    (box, e) -> eventHandler.handleChangeFractal(box)),
            new StyledButton("10 Steps", BUTTON_WIDTH, COMPONENT_HEIGHT,
                    e -> eventHandler.handleRunSteps(10)),
            new StyledButton("100 Steps", BUTTON_WIDTH, COMPONENT_HEIGHT,
                    e -> eventHandler.handleRunSteps(100)),
            new StyledButton("1000 Steps", BUTTON_WIDTH, COMPONENT_HEIGHT,
                    e -> eventHandler.handleRunSteps(1000)),
            createTextField("Steps", BUTTON_WIDTH, COMPONENT_HEIGHT,
                    (box, e) -> eventHandler.handleRunCustomSteps(box)),
            new StyledButton("Reset", BUTTON_WIDTH, COMPONENT_HEIGHT,
                    e -> eventHandler.handleReset())
    );
    return buttonContainer;
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
   * Creates the add panel for adding new fractals. It uses combobox to give the alternative
   * transformation type, buttons to save, cancel, and add to file button, and text fields to give
   * start vector, input vector, and the necessary matrix.
   *
   * @return The add panel.
   */
  public VBox createAddFractalPanel() {
    VBox addPanel = createMainPanel();
    TextField fractalName = createTextField("Fractal name", 210, 20);
    fractalName.setText(controller.getCurrentFractalName());
    ComboBox<TransformationType> transformationComboBox = createComboBox("Transformation",
            210, COMPONENT_HEIGHT,
            Arrays.asList(TransformationType.values()),
            (box, e) -> eventHandler.handleEditTransformationChoice(box),
            getTransformationComboBoxValue());
    HBox startVectorField;
    HBox endVectorField;
    if (controller.isAddingCustomFractal()) {
      startVectorField = createTextBoxWithTextFieldsContainer(
              DEFAULT_SPACING, "Start vector:", 100, 20,
              "x0", "x1");
      endVectorField = createTextBoxWithTextFieldsContainer(
              DEFAULT_SPACING, "End vector:", 100,
              20, "x0", "x1");
    } else {
      startVectorField = createTextBoxWithTextFieldsContainer(
              DEFAULT_SPACING, "Min vector:", 100,
              20, controller.getMinCoordsX());
      endVectorField = createTextBoxWithTextFieldsContainer(
              DEFAULT_SPACING, "Max vector:", 100,
              20, controller.getMaxCoordsX());
    }

    VBox transformationVbox = createTransformationVbox(transformationComboBox);

    addPanel.getChildren().addAll(
            new HBox(DEFAULT_SPACING,
                    createTextBox("Fractal name:"),
                    fractalName
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
                            e -> eventHandler.handleSave(
                                    fractalName.getText(),
                                    getTransformationList(
                                            transformationComboBox.getValue(),
                                            transformationVbox),
                                    getInputVector(startVectorField),
                                    getInputVector(endVectorField)
                            )
                    ),
                    new StyledButton("Cancel", 20, e -> eventHandler.handleCancel()),
                    new StyledButton("Save current locally", 20,
                            e -> eventHandler.handleSaveLocally()),
                    new StyledButton("Add File", 100, 20, e -> eventHandler.handleUploadFile())
            )

    );
    StackPane.setAlignment(addPanel, Pos.BOTTOM_LEFT);
    return addPanel;
  }

  private TransformationType getTransformationComboBoxValue() {
    if (controller.isAddingCustomFractal()) {
      return selectedTransformation;
    } else if (controller.fractalIsJulia()) {
      return TransformationType.JULIA;
    } else {
      return TransformationType.AFFINE;
    }
  }

  /**
   * Retrieves input information based on the selected transformation type.
   *
   * @param selectedTransformation the selected transformation type.
   * @param transformationVbox     the HBox containing the input fields for transformations.
   * @return a list of Transform2D objects.
   */
  private List<String[]> getTransformationList(MainPageView.TransformationType
                                                       selectedTransformation,
                                               VBox transformationVbox) {
    if (selectedTransformation == MainPageView.TransformationType.JULIA) {
      return getJuliaTransformation(transformationVbox);
    } else if (selectedTransformation == MainPageView.TransformationType.AFFINE) {
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
      list.add(new String[]{((TextField) juliaFields.getChildren().get(1)).getText(),
              ((TextField) juliaFields.getChildren().get(2)).getText()});
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
   * @param vectorBox the HBox containing the input fields for the vector.
   * @return a Vector2d object representing the input vector.
   */
  private String[] getInputVector(HBox vectorBox) {
    return new String[]{((TextField) vectorBox.getChildren().get(1)).getText(),
            ((TextField) vectorBox.getChildren().get(2)).getText()};
  }

  /**
   * Creates a VBox containing input fields for transformations.
   *
   * @return a VBox configured with input TextFields.
   */
  private VBox createTransformationVbox(ComboBox<TransformationType> transformationComboBox) {
    VBox vbox = new VBox(10);
    vbox.setFillWidth(true);
    vbox.setAlignment(Pos.CENTER);
    String textBoxText = "Transformation:";
    if (transformationComboBox.getValue() == TransformationType.AFFINE) {
      vbox.getChildren().add(
              new StyledButton("Add Transformation", 250, 20,
                      e -> eventHandler.handleAddTransformation(vbox,
                              "Transformation:", DEFAULT_SPACING)
              )
      );
    }
    if (!controller.isAddingCustomFractal()) {
      for (double[] coords : controller.getTransformList()) {
        vbox.getChildren().add(createTextBoxWithTextFieldsContainer(DEFAULT_SPACING,
                textBoxText, 55, 20, coords));
      }
    } else if (controller.isAddingCustomFractal()
            && transformationComboBox.getValue() == TransformationType.JULIA) {
      vbox.getChildren().add(createTextBoxWithTextFieldsContainer(DEFAULT_SPACING,
              textBoxText, 55, 20, "c0", "c1"));
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

  public void setSelectedTransformation(TransformationType transformationType) {
    selectedTransformation = transformationType;
  }
}

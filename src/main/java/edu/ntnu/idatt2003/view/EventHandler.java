package edu.ntnu.idatt2003.view;

import static edu.ntnu.idatt2003.view.components.TextBoxAndTextFieldContainerFactory.createTextBoxWithTextFieldsContainer;

import edu.ntnu.idatt2003.controller.MainPageController;
import edu.ntnu.idatt2003.model.ChaosGameDescriptionFactory;
import java.io.File;
import java.util.List;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


/**
 * An event handler for the main page view. The event handler is responsible for
 * handling events from the main page view and calling the appropriate methods
 * in the main page controller.
 */
public class EventHandler {
  private final MainPageController controller;
  private final MainPageView view;

  /**
   * Creates a new event handler with the specified controller and view.
   * The event handler is responsible for handling events from the main page
   * view and calling the appropriate methods in the main page controller.
   *
   * @param controller The controller to call methods on.
   * @param view       The view to handle events from.
   */
  protected EventHandler(MainPageController controller, MainPageView view) {
    this.controller = controller;
    this.view = view;
  }

  /**
   * Handles the event of the user pressing a run steps button. The event handler
   * calls the controller's runSteps method with the number of steps to run.
   *
   * @param steps The number of steps to run.
   */
  protected void handleRunSteps(int steps) {
    controller.runSteps(steps);
  }

  /**
   * Handles the event of the user pressing a reset button. The event handler
   * calls the controller's runSteps method with -1 as the number of steps to run.
   */
  protected void handleReset() {
    controller.runSteps(-1);
  }

  /**
   * Handles the event of the user running steps from a TextField. The event handler
   * calls the controller's runCustomSteps method with the steps to run.
   *
   * @param stepsField The text field containing the steps to run.
   */
  protected void handleRunCustomSteps(TextField stepsField) {
    controller.runCustomSteps(stepsField.getText());
    stepsField.clear();
  }

  /**
   * Handles the event of the user pressing a save button. The event handler
   * calls the controller's save-method with the fractal name, transformations,
   *
   * @param fractalName     The name of the fractal.
   * @param transformations The transformations of the fractal.
   * @param startVector     The start vector of the fractal.
   * @param endVector       The end vector of the fractal.
   */
  protected void handleSave(String fractalName, List<String[]> transformations,
                            String[] startVector, String[] endVector) {
    controller.addCustomFractal(startVector, endVector, transformations, fractalName);
  }

  /**
   * Handles the event of the user pressing a cancel button. The event handler
   * calls the view's render method to render the main page view.
   */
  protected void handleCancel() {
    view.render();
  }

  /**
   * Handles the event of the user pressing a save locally button. The event handler
   * asks for a location to save from user and calls the controller's saveToLocalDirectory
   * method with the location to save to.
   */
  protected void handleSaveLocally() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Text Files", "*.txt"));
    controller.saveToLocalDirectory(fileChooser.showSaveDialog(null));
  }

  /**
   * Opens a file chooser dialog that enables the user to upload a custom
   * text file with a chaos game description. The file is then uploaded
   * by the controller. If an exception occurs, an alert is shown.
   */
  protected void handleUploadFile() {
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
   * Handles the event of the user pressing addTransformation button. It adds a new
   * text box with text fields for the user to input the transformation matrix and vector
   * to the vbox.
   *
   * @param vbox        the vbox to add the text box with fields to.
   * @param textBoxText the text to display in the text box.
   * @param spacing     the spacing between the elements.
   */
  public void handleAddTransformation(VBox vbox, String textBoxText, int spacing) {
    vbox.getChildren().add(createTextBoxWithTextFieldsContainer(spacing,
            textBoxText, 55, 20, "a00", "a01",
            "a10", "a11", "v0", "v1"));
  }

  /**
   * Handles the event of the user using a ComboBox to change fractal. It calls the controller's
   * changeFractal method with the selected fractal.
   *
   * @param box The ComboBox containing the fractal to change to.
   */
  protected <T> void handleChangeFractal(ComboBox<T> box) {
    if (box.getValue()
            instanceof ChaosGameDescriptionFactory.DescriptionTypeEnum descriptionTypeEnum) {
      controller.changeFractal(descriptionTypeEnum);
    } else if (box.getValue() instanceof String string) {
      controller.changeFractal(string);
    }
  }

  /**
   * Handles the event of the user using the edit ComboBox. The event handler
   * sets the selected transformation in the view. The controller then changes
   * the fractal to "add new".
   *
   * @param box The ComboBox containing the transformationType.
   */
  protected <T> void handleEditTransformationChoice(ComboBox<T> box) {
    view.setSelectedTransformation((MainPageView.TransformationType) box.getValue());
    controller.changeFractal("add new");
  }
}

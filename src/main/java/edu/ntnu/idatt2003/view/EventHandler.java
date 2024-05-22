package edu.ntnu.idatt2003.view;

import edu.ntnu.idatt2003.controller.MainPageController;
import edu.ntnu.idatt2003.model.ChaosGameDescriptionFactory;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

import static edu.ntnu.idatt2003.view.components.TextBoxAndTextFieldContainerFactory.createTextBoxWithTextFieldsContainer;

public class EventHandler {
  private final MainPageController controller;
  private final MainPageView view;

  protected EventHandler(MainPageController controller, MainPageView view) {
    this.controller = controller;
    this.view = view;
  }

  protected void handleRunSteps(int steps) {
    controller.runSteps(steps);
  }

  protected void handleReset() {
    controller.runSteps(-1);
  }

  protected void handleSave(String fractalName, List<String[]> transformations,
                            String[] startVector, String[] endVector) {
    controller.addCustomFractal(startVector, endVector, transformations, fractalName);
  }

  protected void handleCancel() {
    view.render();
  }

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

  public void handleAddTransformation(VBox vbox, String textBoxText, int spacing) {
    vbox.getChildren().add(createTextBoxWithTextFieldsContainer(spacing,
            textBoxText, 55, 20, "a00", "a01",
            "a10", "a11", "v0", "v1"));
  }

  protected <T> void handleChangeFractal(ComboBox<T> box) {
    if (box.getValue() instanceof ChaosGameDescriptionFactory.DescriptionTypeEnum descriptionTypeEnum) {
      controller.changeFractal(descriptionTypeEnum);
    } else if (box.getValue() instanceof String string) {
      controller.changeFractal(string);
    }
  }

  protected <T> void handleEditTransformationChoice(ComboBox<T> box) {
    view.setSelectedTransformation((MainPageView.TransformationType) box.getValue());
    controller.changeFractal("add new");
  }
}
